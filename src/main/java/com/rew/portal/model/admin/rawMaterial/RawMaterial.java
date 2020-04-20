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

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "unitId")
	private String unitId;

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
	
	/*public String getUnitId() {
		if (Objects.nonNull(this.unit)) {
			return this.unit.getUnitId();
		}
		return null;
	}*/

	@Override
	public String toString() {
		return "RawMaterial [code=" + code + ", name=" + name
				+ ", description=" + description + ", isActive=" + isActive
				+ ", unitName=" + unitName + "]";
	}
}
