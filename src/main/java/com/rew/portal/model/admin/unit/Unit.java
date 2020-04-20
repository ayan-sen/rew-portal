package com.rew.portal.model.admin.unit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.rew.portal.model.admin.rawMaterial.RawMaterial;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "unit")
public class Unit {

	@Id
	@Column(name = "unitId")
	private String unitId;

	@Column(name = "unitName")
	private String unitName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unitId")
	private RawMaterial rawMaterial;
}
