package com.o2r.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.SellerDao;
import com.o2r.helper.CustomException;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private SellerDao sellerDao;

	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		com.o2r.model.Seller domainSeller = null;
		System.out.println("Inside customer user details..." + login);

		try {
			domainSeller = sellerDao.getSeller(login);
		} catch (CustomException ce) {
			System.out.println(ce.getMessage());
		}

		/*
		 * System.out.println("Inside customer user details...login"+domainSeller
		 * .getEmail());
		 * System.out.println("Inside customer user details...pass"
		 * +domainSeller.getPassword());
		 * System.out.println(" Role "+getAuthorities
		 * (domainSeller.getRole().getRole()));
		 */

		String password = new String(
				hexStringToByteArray(domainSeller.getPassword()),
				StandardCharsets.UTF_8);
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		UserDetails user = new User(domainSeller.getEmail(), password, enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				getAuthorities(domainSeller.getRole().getRole()));
		return user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities(String role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	public List<String> getRoles(String role) {

		List<String> roles = new ArrayList<String>();

		if (role.equalsIgnoreCase("admin")) {
			roles.add("ROLE_MODERATOR");
			roles.add("ROLE_ADMIN");
		} else if (role.equalsIgnoreCase("moderator")) {
			roles.add("ROLE_MODERATOR");
		}
		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	public byte[] hexStringToByteArray(String hex) {
		int l = hex.length();
		byte[] data = new byte[l / 2];
		for (int i = 0; i < l; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character
					.digit(hex.charAt(i + 1), 16));
		}
		return data;
	}

}
