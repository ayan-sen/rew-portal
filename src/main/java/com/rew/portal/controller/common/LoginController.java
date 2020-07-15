package com.rew.portal.controller.common;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.config.JwtConfig;
import com.rew.portal.controller.admin.client.ClientController;
import com.rew.portal.model.common.JwtRequest;
import com.rew.portal.model.common.JwtResponse;
import com.rew.portal.model.common.UserInfo;
import com.rew.portal.service.common.JwtUserDetailsService;

@Slf4j
@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(),
				authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtConfig.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> save(@RequestBody UserInfo userInfo) throws Exception {
		
		Map<String, String> response = new HashMap<>();
		try {
			UserInfo user = userDetailsService.save(userInfo);
			response.put("status", "success");
			response.put("message", "User saved Successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in user creation", e);
			response.put("status", "failure");
			response.put("message", "Error occurred user creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
