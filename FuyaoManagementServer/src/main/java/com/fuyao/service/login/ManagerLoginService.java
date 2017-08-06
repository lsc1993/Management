package com.fuyao.service.login;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuyao.dao.login.IManagerLoginDao;
import com.fuyao.model.login.ManagerLogin;

@Transactional
@Service("managerLoginService")
public class ManagerLoginService implements IManagerLoginService {
	
	@Resource
	private IManagerLoginDao loginDao;

	public void setLoginDao(IManagerLoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public HashMap<String, String> login(ManagerLogin manager) {
		// TODO Auto-generated method stub
		return loginDao.login(manager);
	}
}
