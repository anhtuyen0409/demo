package com.nguyenanhtuyen.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "app_user", uniqueConstraints = { @UniqueConstraint(name = "app_user_uk", columnNames = "user_name") })
public class AppUser {
	
	@Id
	@GeneratedValue
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	
	@Column(name = "user_name", length = 255, nullable = false)
	private String username;
	
	@Column(name = "password", length = 255, nullable = false)
	private String password;
	
	@Column(name = "enabled", length = 1, nullable = false)
	private boolean enabled;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
