package com.rew.portal.model.admin.unit;

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
@Entity(name="unit")
public class Unit {

	@Id
	@Column(name="unitId")
	private String unitId;
	
	@Column(name="unitName")
	private String unitName;
}
