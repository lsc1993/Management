package com.fuyao.service.login;

import java.util.HashMap;

import com.fuyao.model.login.ManagerLogin;

public interface IManagerLoginService {
	HashMap<String,String> login(ManagerLogin manager);
}
