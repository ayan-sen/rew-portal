package com.rew.portal.model.common;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 6236037422395321872L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
