package com.rew.portal.model.transaction.invoice;

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
@Entity(name="invoice_d")
@Table(name="invoice_d")
public class InvoiceDetails implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="invoiceDetailsId", length=20)
	private Integer invoiceDetailsId;
	
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
	
	@JsonIgnore
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoiceId", referencedColumnName="invoiceId", nullable=false)
	private Invoice invoice;
	
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
	
	public String getRmName() {
		if(this.rawMaterial != null) {
			return this.rawMaterial.getName();
		}
		return null;
	}
	
	public String getUnitName() {
		if(this.unit != null) {
			return this.unit.getUnitName();
		}
		return null;
	}
	
	public String getInvoiceId() {
		if(this.invoice != null) {
			return this.invoice.getInvoiceId();
		}
		return null;
	}

}
