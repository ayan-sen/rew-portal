package com.rew.portal.model.transaction.orderDelivery;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rew.portal.model.admin.client.Client;
import com.rew.portal.model.admin.client.ClientDetails;
import com.rew.portal.model.admin.companyProfile.WorkUnitDetails;
import com.rew.portal.model.common.PkGenerationSignature;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="orderdelivery_h")
@Table(name="orderdelivery_h")
public class OrderDelivery implements PkGenerationSignature, Serializable {

	private static final long serialVersionUID = -5710599625123135341L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="deliveryId", length=20)
	private String deliveryId;
	
	@Column(name="billNo", nullable=false, length=20)
	private String billNo;
	
	@JsonIgnore
	@Column(name="billDate", nullable=false)
	private LocalDate billDate;
	
	@Transient
	private String billDateString;
	
	@Column(name="orderId", length=20)
	private String orderId;
	
	@Column(name="supplierId", length=20, nullable=false)
	private String supplierId;
	
	@Column(name="supplierDetailsId", length=20, nullable=false)
	private Integer supplierDetailsId;
	
	@Column(name="consigneeId", length=20)
	private String consigneeId;
	
	@Column(name="consigneeDetailsId", length=20)
	private Integer consigneeDetailsId;
	
	@Column(name="vehicleNo", length=25)
	private String vehicleNo;
	
	@Column(name="amount", length=20, nullable=false)
	private Double amount;
	
	@Column(name="freightCharges", length=20)
	private Double freightCharges;
	
	@Column(name="cgstAmount", length=20, nullable=false)
	private Double cgstAmount;
	
	@Column(name="sgstAmount", length=20, nullable=false)
	private Double sgstAmount;
	
	@Column(name="totalAmount", length=20, nullable=false)
	private Double totalAmount;
	
	@Column(name="notes", length=100)
	private String notes;
	
	@Column(name="siteId", length=20, nullable=false)
	private String siteId;
	
	@Column(name="projectId", length=20, nullable=true)
	private String projectId;
	
	@Setter
	@JsonProperty("isActive")
	@Column(name="isActive", length=1, nullable=false)
	private Boolean isActive = true;

	@OneToMany(mappedBy="orderDelivery", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<OrderDeliveryDetails> details = new ArrayList<>();
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierId", referencedColumnName="clientId", insertable=false,updatable=false)
	private Client supplier;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierDetailsId", referencedColumnName="detailId", insertable=false,updatable=false, nullable=true)
	private ClientDetails supplierDetail;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER, optional=true)
	@JoinColumn(name="consigneeId", referencedColumnName="clientId", insertable=false,updatable=false, nullable=true)
	private Client consignee;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER, optional=true)
	@JoinColumn(name="consigneeDetailsId", referencedColumnName="detailId", insertable=false,updatable=false)
	private ClientDetails consigneeDetail;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="siteId", referencedColumnName="siteId", insertable=false,updatable=false)
	private WorkUnitDetails workUnitDetail;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="orderId", referencedColumnName="orderId", insertable=false,updatable=false)
	private OrderPlacement orderPlacement;
	
//	@Setter
//	@OneToOne(mappedBy="invOrderDelivery", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
//    private InventoryRecord record = new InventoryRecord();
	
	@JsonIgnore
	@Override
	public String getPrefix() {
		return "REW/D/";
	}

	@JsonIgnore
	@Override
	public String getTableName() {
		return "orderdelivery_h";
	}

	@JsonIgnore
	@Override
	public String getIdColName() {
		return "deliveryId";
	}
	
	@Override
	public boolean enableSuffix() {
		return true;
	}
	
	public void removeDetail(int detailId) {
		details.removeIf(d -> d.getDetailId() == detailId);
	}
	
	public String getBillDateString() {
		if(this.billDate != null) {
			return this.billDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}
	
	public void setBillDateString(String billDateString) {
		this.billDateString = billDateString;
		if(StringUtils.isNotEmpty(billDateString)) {
			this.billDate = LocalDate.parse(this.billDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}
	
	public String getSupplierName() {
		if (this.supplier != null) {
			return this.supplier.getClientName();
		}
		return null;
	}

	public String getSupplierIdentifier() {
		if (this.supplierDetail != null) {
			return this.supplierDetail.getIdentifier();
		}
		return null;
	}
	
	public String getConsigneeIdentifier() {
		if (this.consigneeDetail != null) {
			return this.consigneeDetail.getIdentifier();
		}
		return null;
	}
	
	public String getConsigneeName() {
		if (this.consignee != null) {
			return this.consignee.getClientName();
		}
		return null;
	}
}
