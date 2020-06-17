package com.rew.portal.model.transaction.inventory;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.rawMaterial.RawMaterial;
import com.rew.portal.model.admin.unit.Unit;
import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;
import com.rew.portal.model.transaction.orderDelivery.OrderDeliveryDetails;
import com.rew.portal.model.transaction.orderProcessing.OrderProcessing;
import com.rew.portal.model.transaction.orderProcessing.OrderProcessingDetails;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="inventory_record")
@Table(name="inventory_record")
public class InventoryRecord implements Serializable {

	private static final String ORDER_PROCESSING = "OP";

	private static final String OUT = "OUT";

	private static final String IN = "IN";

	private static final String ORDER_DELIVERY = "OD";

	private static final String PRODUCT = "P";

	private static final String RAW_MATERIAL = "R";
	
	private static final String SEMI_FINISHED_PRODUCT = "S";

	private static final long serialVersionUID = 3226091850722591474L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="inventoryId", length=20)
	private Integer inventoryId;
	
	@Column(name="referenceType", length=20, nullable=false)
	private String referenceType;
	
	@Column(name="referenceId", length=20, nullable=false)
	private String referenceId;
	
	@JsonIgnore
	@Column(name="referenceDate", length=20, nullable=false)
	private LocalDate referenceDate;
	
	@Transient
	private String referenceDateString;
	
	@Column(name="referenceDetailId", length=20, nullable=false)
	private Integer referenceDetailId;
	
	@Column(name="inOutFlag", length=10, nullable=false)
	private String inOutFlag;
	
	@Column(name="materialCode", length=20, nullable=false)
	private String materialCode;
	
	@Column(name="unitCode", length=20, nullable=false)
	private String unitCode;
	
	@Column(name="quantity", length=20, nullable=false)
	private Double quantity;
	
	@Column(name="siteId", length=10, nullable=false)
	private String siteId;
	
	@Column(name="itemType", length=10, nullable=false)
	private String itemType;
	
	@Column(name="projectId", length=20, nullable=false)
	private String projectId;

	@Transient
	private String materialName;
	
	@Transient
	private String unitName;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="materialCode", referencedColumnName="code", insertable=false,updatable=false)
	private RawMaterial rawMaterial;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="unitCode", referencedColumnName="unitId", insertable=false,updatable=false)
	private Unit unit;

	public static List<InventoryRecord> createFromOrderDelivery(OrderDelivery delivery) {
		List<OrderDeliveryDetails> deliveryDetails = delivery.getDetails();
		return deliveryDetails.stream().map(d -> {
			InventoryRecord record = InventoryRecord.builder()
					.referenceType(ORDER_DELIVERY)
					.referenceId(delivery.getDeliveryId())
					.referenceDate(delivery.getBillDate())
					.referenceDetailId(d.getDetailId())
					.inOutFlag(IN)
					.materialCode(d.getRmId())
					.unitCode(d.getUnitId())
					.quantity(d.getQuantity())
					.siteId(delivery.getSiteId())
					.itemType(RAW_MATERIAL)
					.projectId(delivery.getProjectId())
					.build();
			return record;
		}).collect(Collectors.toList());
	}
	
	public static List<InventoryRecord> createFromOrderProcessing(OrderProcessing orderProcessing) {
		List<OrderProcessingDetails> details = orderProcessing.getDetails();
		return details.stream().map(d -> {
				InventoryRecord record = InventoryRecord.builder()
						.referenceType(ORDER_PROCESSING)
						.referenceId(orderProcessing.getProcessId().toString())
						.referenceDate(orderProcessing.getProcessDate())
						.referenceDetailId(d.getProcessDetailsId())
						.inOutFlag(d.getProcessType())
						.materialCode(d.getMaterialId())
						.unitCode(d.getMaterialUnit())
						.quantity(d.getQuantity())
						.siteId(orderProcessing.getSiteId())
						.itemType(d.getMaterialType())
						.projectId(orderProcessing.getProjectId())
						.build();
				return record;
		}).collect(Collectors.toList());
	}

	public String getMaterialName() {
		if(this.rawMaterial != null) {
			return this.rawMaterial.getName();
		}
		return this.materialName;
	}
	
	public String getUnitName() {
		if(this.unit != null) {
			return this.unit.getUnitName();
		}
		return this.unitName;
	}
	
}
