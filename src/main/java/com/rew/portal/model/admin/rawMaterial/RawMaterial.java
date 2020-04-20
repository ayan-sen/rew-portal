package com.rew.portal.model.admin.rawMaterial;

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
@Entity(name="raw_material")
public class RawMaterial {

	@Id
	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description", nullable=true)
	private String description;
	
	@Column(name="unitId")
	private String unitId;
	
	@Column(name="isActive")
	private Boolean isActive;
}
