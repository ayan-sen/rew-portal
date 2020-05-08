package com.rew.portal.model.admin.rawMaterial;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.rew.portal.model.admin.unit.Unit;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "raw_material")
public class RawMaterial {

	@Id
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "hsnSacCode", nullable = true)
	private String hsnSacCode;

	@Column(name = "unitId")
	private String unitId;
	
	@Column(name = "type", length=1)
	private String type;

	@Column(name = "isActive")
	private Boolean isActive;

	@Transient
	private String unitName;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unitId",insertable=false,updatable=false)
	private Unit unit;

	public String getUnitName() {
		if (Objects.nonNull(this.unit)) {
			return this.unit.getUnitName();
		}
		return this.unitName;
	}
	
}
