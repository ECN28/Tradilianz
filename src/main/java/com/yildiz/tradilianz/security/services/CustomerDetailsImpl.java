package com.yildiz.tradilianz.security.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yildiz.tradilianz.customer.Customer;

public class CustomerDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private final static Logger log = LoggerFactory.getLogger(CustomerDetailsImpl.class);
	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public CustomerDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static CustomerDetailsImpl build(Customer user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		try {
			authorities.add(new SimpleGrantedAuthority(user.getRole()));
		} catch (NullPointerException e) {
			log.info(e.getMessage());
		}
		return new CustomerDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
				authorities);

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CustomerDetailsImpl user = (CustomerDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

}
