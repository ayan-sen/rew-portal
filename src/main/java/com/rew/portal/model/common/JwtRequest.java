package com.rew.portal.model.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = -2208848963907786431L;
	private String username;
	private String password;
}
