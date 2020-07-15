package com.rew.portal.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="user_h")
@Table(name="user_h")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 2856974212179653063L;

	@Column(name="name", length=50, nullable=true)
	private String name;

	@Id	
	@Column(name="userName", length=50, nullable=false)
	private String userName;
	
	@Setter
	@Column(name="password", length=200, nullable=false)
	private String password;
	
}
