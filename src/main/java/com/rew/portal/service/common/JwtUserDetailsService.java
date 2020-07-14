package com.rew.portal.service.common;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	/*@Autowired
	private UserDao userDao;*/

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("admin".equals(username)) {
			return new User("admin", "$2a$10$.jrBb0x40fwLLa0cuRy3xeKPtVjoK33iS/DNEblhLHJahPhRp0I5e", new ArrayList<>());
		} else if ("ayan".equals(username)) {
			return new User("ayan", "$2a$10$FPEQAZ6epb6.ZLJwfGBCjewhRXO.GFqbMTEbqG5wkgmJXgIMb23xu", new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	/*public UserDao save(UserDTO user) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.save(newUser);
	}*/
}
