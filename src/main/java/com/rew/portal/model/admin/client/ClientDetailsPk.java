package com.rew.portal.model.admin.client;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ClientDetailsPk implements Serializable {

	private static final long serialVersionUID = -2403820290321154273L;
	private String clientId;
	private Integer detailId;
}
