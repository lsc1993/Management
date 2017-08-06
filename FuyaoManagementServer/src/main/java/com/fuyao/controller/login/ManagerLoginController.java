package com.fuyao.controller.login;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fuyao.model.login.ManagerLogin;
import com.fuyao.service.login.IManagerLoginService;

@Controller
@RequestMapping("/manager")
public class ManagerLoginController {
	
	@Resource
	private IManagerLoginService loginService;

	public void setLoginService(IManagerLoginService loginService) {
		this.loginService = loginService;
	}
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	private HashMap<String,String> login(@RequestBody ManagerLogin manager) {
		return loginService.login(manager);
	}
}
