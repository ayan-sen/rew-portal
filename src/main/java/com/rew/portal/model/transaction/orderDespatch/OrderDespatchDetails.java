package com.rew.portal.model.transaction.orderDespatch;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.rawMaterial.RawMaterial;
import com.rew.portal.model.admin.unit.Unit;

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
@Entity(name="orderdespatch_d")
@Table(name="orderdespatch_d")
public class OrderDespatchDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6958953567531712196L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="processDetailsId", length=20)
	private Integer despatchDetailsId;
	
	@Column(name="despatchType", length=20, nullable=false)
	private String despatchType;
	
	@Column(name="materialId", length=20)
	private String materialId;
	
	@Column(name="materialUnit", length=20)
	private String materialUnit;
	
	@Column(name="quantity", length=20)
	private Double quantity;
	
	@Column(name="materialType", length=20)
	private String materialType;
	
	@Setter
	@Transient
	private Double availableQuantity;
	
	@Setter
	@Transient
	private Double remainingQuantity;
	
	@JsonIgnore
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "despatchId", referencedColumnName="despatchId", nullable=false)
	private OrderDespatch orderDespatch;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="materialId", referencedColumnName="code", insertable=false,updatable=false)
	private RawMaterial material;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="materialUnit", referencedColumnName="unitId", insertable=false,updatable=false)
	private Unit unit;
	
	public String getMaterialName() {
		if(this.material != null) {
			return this.material.getName();
		}
		return null;
	}
	
	public String getUnitName() {
		if(this.unit != null) {
			return this.unit.getUnitName();
		}
		return null;
	}
	

}
