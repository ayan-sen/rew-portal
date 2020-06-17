package com.rew.portal.model.transaction.project;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.rawMaterial.RawMaterial;
import com.rew.portal.model.admin.unit.Unit;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="project_d")
@Table(name="project_d")
public class ProjectDetails implements Serializable{

	private static final long serialVersionUID = 5909480244327200167L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="detailId", length=20)
	private Integer detailId;
	
	@Column(name="rmId", length=20, nullable=false)
	private String rmId;
	
	@Column(name="unitId", length=20, nullable=false)
	private String unitId;
	
	@Column(name="quantity", length=20, nullable=false)
	private Double quantity;
	
	@Column(name="rate", length=20, nullable=false)
	private Double rate;
	
	@Column(name="amount", length=20, nullable=false)
	private Double amount;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(
		{
			@JoinColumn(name = "projectId", referencedColumnName="projectId", nullable=false),
			@JoinColumn(name = "amendmentNo", referencedColumnName="amendmentNo", nullable=false)
		}
	)
	@JsonIgnore
	private Project project;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="rmId", referencedColumnName="code", insertable=false,updatable=false)
	private RawMaterial rawMaterial;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unitId", referencedColumnName="unitId", insertable=false,updatable=false)
	private Unit unit;
	
	
	public String getProjectId() {
		if(this.project != null) {
			return this.project.getProjectId();
		}
		return null;
	}
	
	public Integer getAmendmentNo() {
		if(this.project != null) {
			return this.project.getAmendmentNo();
		}
		return null;
	}
	
	public String getRmName() {
		if(this.rawMaterial != null) {
			return this.rawMaterial.getName();
		}
		return null;
	}
	
	public String getUnitName() {
		if(this.rawMaterial != null) {
			return this.unit.getUnitName();
		}
		return null;
	}
}