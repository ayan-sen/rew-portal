package com.rew.portal.model.transaction.orderPlacement;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.rawMaterial.RawMaterial;
import com.rew.portal.model.admin.unit.Unit;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="orderplacement_d")
@Table(name="orderplacement_d")
public class OrderPlacementDetails implements Serializable {

	private static final long serialVersionUID = -3708389199754258025L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="orderDetailsId", length=20)
	private Integer orderDetailsId;
	
	@Column(name="rmId", length=20, nullable=false)
	private String rmId;
	
	@Column(name="unitId", length=20, nullable=false)
	private String unitId;
	
	@Column(name="quantity", length=20, nullable=false)
	private Double quantity;
	
	@JsonIgnore
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId", referencedColumnName="orderId", nullable=false)
	private OrderPlacement orderPlacement;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="rmId", referencedColumnName="code", insertable=false,updatable=false)
	private RawMaterial rawMaterial;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unitId", referencedColumnName="unitId", insertable=false,updatable=false)
	private Unit unit;
}
