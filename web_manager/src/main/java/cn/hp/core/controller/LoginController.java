package cn.hp.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆controller获得用户名
 * @author 35814
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {
	@RequestMapping("/showName")
	public Map LoginController() {
		//获得权限框架的上下文对象
		SecurityContext context = SecurityContextHolder.getContext();
		//获得authentication
		Authentication authentication = context.getAuthentication();
		//获取权限的user
		User user = (User) authentication.getPrincipal();
		//获取用户名
		String name = user.getUsername();
		//创建map
		Map map=new HashMap<>();
		map.put("username", name);
		return map;
	}

}
