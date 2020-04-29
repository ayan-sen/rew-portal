package com.rew.portal.model.transaction.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.rawMaterial.RawMaterial;
import com.rew.portal.model.admin.unit.Unit;
import com.rew.portal.model.common.PkGenerationSignature;
import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="inventory_record")
@Table(name="inventory_record")
public class InventoryRecord implements PkGenerationSignature, Serializable {

	private static final long serialVersionUID = 3226091850722591474L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="inventoryId", length=20)
	private String inventoryId;
	
	@Column(name="referenceType", length=20, nullable=false)
	private String referenceType;
	
	@Column(name="inOutFlag", length=10, nullable=false)
	private String inOutFlag;
	
	@Column(name="rawMaterialCode", length=20, nullable=false)
	private String rawMaterialCode;
	
	@Column(name="unitCode", length=20, nullable=false)
	private String unitCode;
	
	@Column(name="quantity", length=20, nullable=false)
	private Double quantity;
	
	@Column(name="siteId", length=10, nullable=false)
	private String siteId;
	
	/*@JsonIgnore
	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referenceId", referencedColumnName="deliveryId", nullable=false)
	private OrderDelivery invOrderDelivery;*/
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="rawMaterialCode", referencedColumnName="code", insertable=false,updatable=false)
	private RawMaterial rawMaterial;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unitCode", referencedColumnName="unitId", insertable=false,updatable=false)
	private Unit unit;

	@Override
	public String getPrefix() {
		return "REW/I/";
	}

	@Override
	public String getTableName() {
		return "inventory";
	}

	@Override
	public String getIdColName() {
		return "inventoryId";
	}
	
	@Override
	public boolean enableSuffix() {
		return true;
	}
}
