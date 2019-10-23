package cn.hp.core.service;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.hp.core.pojo.seller.Seller;

public class UserDetailServiceImpl implements UserDetailsService{
	private SellerService sellerService;

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	//权限认证
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//1.判断用户名是否为空
		//2.根据用户名去数据库中查询对应的用户
		//3.如果用户名不正确，返回null
		//4.如果用户名正确判断是否已经审核通过，如果审核未通过则返回null
		//5.返回SpringSecurity的User对象，包含1.用户名，2.密码，3.用户角色列表
		//创建角色集合
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		//1.
		if(username == null) {
			return null;
		}
		//2.
		Seller seller = sellerService.findOne(username);
		if(seller!=null) {
			if("1".equals(seller.getStatus())) {
				return new User(seller.getSellerId(), seller.getPassword(), authorities);
			}
		}
		return null;
	}
	
}
