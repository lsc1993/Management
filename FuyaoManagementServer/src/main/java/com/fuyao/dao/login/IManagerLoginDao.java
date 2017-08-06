package com.fuyao.dao.login;

import java.util.HashMap;

import com.fuyao.model.login.ManagerLogin;

public interface IManagerLoginDao {
	HashMap<String,String> login(ManagerLogin manager);
}
