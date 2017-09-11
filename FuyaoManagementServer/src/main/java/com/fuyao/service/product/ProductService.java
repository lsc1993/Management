package com.fuyao.service.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.fuyao.dao.product.IProductDao;
import com.fuyao.model.product.Product;
import com.fuyao.model.product.ProductImages;
import com.fuyao.model.product.ProductStandard;
import com.fuyao.util.FuyaoUtil;

@Transactional
@Service("productService")
public class ProductService {
	
	@Resource
	private IProductDao productDao;
	
	public void setProducrDao(IProductDao producrDao) {
		this.productDao = producrDao;
	}

	public JSON getProductList(HashMap<String, String> data) {
		return productDao.getProductList(data);
	}
	
	public HashMap<String,String> uploadProduct(HttpServletRequest request) {
		HashMap<String,String> result = new HashMap<String,String>();
		boolean isSubmit = true;  //是否提交产品
		
		/*
		 * 产品价格转换异常处理
		 **/
		float price = 0;
		try{
			price = Float.parseFloat(request.getParameter("price"));
		} catch(NumberFormatException e) {
			isSubmit = false;
			e.printStackTrace();
		}
		
		if (!isSubmit) {
			isSubmit = true;
			result.put("message", "价格转换异常");
			return result;
		}
		
		/*
		 * 产品数量转换异常处理
		 **/
		int count = 0;
		try{
			count = Integer.parseInt(request.getParameter("count"));
		} catch(NumberFormatException e) {
			isSubmit = false;
			e.printStackTrace();
		}
		if (!isSubmit) {
			isSubmit = true;
			result.put("message", "数量转换异常");
			return result;
		}
		
		/*
		 * 产品属性值填充 
		 **/
		String pId = request.getParameter("pId");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String describe = request.getParameter("describe");
		
		Product product = new Product();
		product.setPId(pId);
		product.setName(name);
		product.setType(type);
		product.setDescribe(describe);
		product.setPrice(price);
		product.setCount(count);
		product.setDate(new Date());
		
		result = productDao.addProduct(product);
		if (!"success".equals(result.get("result"))) {
			return result;
		}
		
		long id = productDao.getProductId(product.getPId());
		
		/*
		 * 上传产品规格
		 **/
		String standard = request.getParameter("standard");
		String[] sd = standard.split(",");
		for (int i = 0;i < sd.length;++i) {
			ProductStandard ps = new ProductStandard();
			ps.setpId(id);
			ps.setStandard(sd[i]);
			try {
				float sPrice = Float.parseFloat(request.getParameter(sd[i]));
				ps.setPrice(sPrice);
			} catch (NumberFormatException e) {
				result.put("message", "规格价格有误！");
				result.put("result", "fault");
				throw new HibernateException("price error");
			}
			result = productDao.addStandard(ps);
			if (!"success".equals(result.get("result"))) {
				return result;
			}
		}
		
		/*
		 * 上传产品相关图片
		 **/
		int pImgLen,dImgLen;
		try {
			pImgLen = Integer.parseInt(request.getParameter("pImageLen"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			pImgLen = 0;
		}
		
		try {
			dImgLen = Integer.parseInt(request.getParameter("dImageLen"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			dImgLen = 0;
		}
		
		OutputStream out = null;
		InputStream in = null;
		/*
		 * 处理产品首页图片
		 **/
		try {
			Part part = request.getPart("sImage");
			ProductImages image = new ProductImages();
			String imgName ="sImg" + FuyaoUtil.getImageUUID() + FuyaoUtil.getImageType(part);
			image.setImage(imgName);
			image.setpId(id);
			result = productDao.addPImages(image);
			if (!"success".equals(result.get("result"))) {
				return result;
			}
			/*
			 * 存储图片到服务器
			 **/
			in = part.getInputStream();
			out = new FileOutputStream(new File(FuyaoUtil.IMAGE_PATH+imgName));
			int read = 0;
			byte[] bytes;
			if(part.getSize() <= Integer.MAX_VALUE) {
				bytes = new byte[(int)part.getSize()];
			} else {
				bytes = new byte[12400];
			}
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		 * 处理产品图片
		 **/
		for (int i = 0;i < pImgLen;++i) {
			try {
				Part part = request.getPart("pImage"+i);
				ProductImages image = new ProductImages();
				String imgName ="pImg" + FuyaoUtil.getImageUUID() + FuyaoUtil.getImageType(part);
				image.setImage(imgName);
				image.setpId(id);
				result = productDao.addPImages(image);
				if (!"success".equals(result.get("result"))) {
					return result;
				}
				/*
				 * 存储图片到服务器
				 **/
				in = part.getInputStream();
				out = new FileOutputStream(new File(FuyaoUtil.IMAGE_PATH+imgName));
				int read = 0;
				byte[] bytes;
				if(part.getSize() <= Integer.MAX_VALUE) {
					bytes = new byte[(int)part.getSize()];
				} else {
					bytes = new byte[12400];
				}
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (null != in) {
						in.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (null != out) {
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/*
		 * 处理产品详情图片
		 **/
		for (int i = 0;i < dImgLen;++i) {
			try {
				Part part = request.getPart("dImage"+i);
				ProductImages image = new ProductImages();
				String imgName ="dImg" + FuyaoUtil.getImageUUID() + FuyaoUtil.getImageType(part);
				image.setImage(imgName);
				image.setpId(id);
				result = productDao.addPImages(image);
				if (!"success".equals(result.get("result"))) {
					return result;
				}
				/*
				 * 存储图片到服务器
				 **/
				in = part.getInputStream();
				out = new FileOutputStream(new File(FuyaoUtil.IMAGE_PATH+imgName));
				int read = 0;
				byte[] bytes;
				if(part.getSize() <= Integer.MAX_VALUE) {
					bytes = new byte[(int)part.getSize()];
				} else {
					bytes = new byte[12400];
				}
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (null != in) {
						in.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (null != out) {
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.put("message", "产品信息同步数据库成功");
				result.put("result", "success");
			}
		}
		return result;
	}
	
}
