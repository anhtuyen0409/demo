package com.nguyenanhtuyen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nguyenanhtuyen.dao.AppRoleDAO;
import com.nguyenanhtuyen.dao.AppUserDAO;
import com.nguyenanhtuyen.entities.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private AppUserDAO appUserDAO;
	
	@Autowired
	private AppRoleDAO appRoleDAO;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		AppUser appUser = this.appUserDAO.findUserAccount(userName);
		
		if(appUser == null) {
			System.out.println("User not found!");
			throw new UsernameNotFoundException("User " + userName + " was not found in database");
		}
		
		System.out.println("Found user: " + appUser);
		
		List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		if(roleNames != null) {
			for(String role : roleNames) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		
		UserDetails userDetails = new User(appUser.getUsername(), appUser.getPassword(), grantList);
		
		return userDetails;
	}

}
