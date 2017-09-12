package com.fuyao.service.login;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuyao.dao.login.IManagerLoginDao;
import com.fuyao.model.login.ManagerLogin;
import com.fuyao.util.Log;

@Transactional
@Service("managerLoginService")
public class ManagerLoginService {
	
	@Resource
	private IManagerLoginDao loginDao;

	public void setLoginDao(IManagerLoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public HashMap<String, String> login(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HashMap<String,String> result = new HashMap<String,String>();
		ManagerLogin manager = new ManagerLogin();
		manager.setAccount(request.getParameter("account"));
		manager.setPassword(request.getParameter("password"));
		if ((manager = loginDao.login(manager)) != null) {
			Log.log("token:" + manager.getToken());
			this.generateCookie(manager.getToken(), response);
			result.put("result", "success");
		} else {
			result.put("result", "fault");
		}
		return result;
	}
	
	private void generateCookie(String token, HttpServletResponse response) {
		Cookie cookie = new Cookie("manager_token", token);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
