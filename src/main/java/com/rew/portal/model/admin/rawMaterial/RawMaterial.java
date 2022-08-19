package com.rew.portal.model.admin.rawMaterial;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.unit.Unit;
import com.rew.portal.model.common.PkGenerationSignature;
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "raw_material")
@Table(name="raw_material")
public class RawMaterial implements PkGenerationSignature, Serializable {

	private static final long serialVersionUID = 6740144183656616194L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name = "code", length=10)
	private String code;

	@Column(name = "name", length=50, unique=true)
	private String name;
	
	@Column(name = "description", length=100, nullable = true)
	private String description;

	@Column(name = "hsnSacCode", nullable = true)
	private String hsnSacCode;

	@Column(name = "unitId")
	private String unitId;
	
	@Column(name = "type", length=1)
	private String type;

	@Setter
	@Column(name = "isActive")
	private Boolean isActive;

	@Transient
	private String unitName;
	
	@JsonIgnore
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

	@JsonIgnore
	@Override
	public String getPrefix() {
		return "R";
	}

	@JsonIgnore
	@Override
	public String getTableName() {
		return "raw_material";
	}

	@JsonIgnore
	@Override
	public String getIdColName() {
		return "code";
	}
	
}
