package com.fuyao.controller.login;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fuyao.service.login.ManagerLoginService;

@Controller
@RequestMapping("/manager")
public class ManagerLoginController {
	
	@Resource
	private ManagerLoginService loginService;

	public void setLoginService(ManagerLoginService loginService) {
		this.loginService = loginService;
	}
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	private HashMap<String,String> login(HttpServletRequest request, HttpServletResponse response) {
		return loginService.login(request, response);
	}
}
