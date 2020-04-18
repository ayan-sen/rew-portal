package com.rew.portal.model.admin.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name="menu")
public class Menu {
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="displayName")
	private String displayName;
	
	@Column(name="iconName")
	private String iconName;
	
	@Column(name="route")
	private String route;
	
	@Column(name="parent", nullable=true)
	private Integer parent;
	
}
