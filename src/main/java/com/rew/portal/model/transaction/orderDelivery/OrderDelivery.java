package com.rew.portal.model.transaction.orderDelivery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
import com.rew.portal.model.admin.client.Client;
import com.rew.portal.model.admin.client.ClientDetails;
import com.rew.portal.model.admin.companyProfile.WorkUnitDetails;
import com.rew.portal.model.common.PkGenerationSignature;
import com.rew.portal.model.transaction.inventory.InventoryRecord;

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
	
	@Temporal(TemporalType.DATE)
	@Column(name="billDate", nullable=false)
	private Date billDate;
	
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
	
	@Column(name="cgstAmount", length=20, nullable=false)
	private Double cgstAmount;
	
	@Column(name="sgstAmount", length=20, nullable=false)
	private Double sgstAmount;
	
	@Column(name="roundOffAmount", length=5, nullable=false)
	private Double roundOffAmount;
	
	@Column(name="totalAmount", length=20, nullable=false)
	private Double totalAmount;
	
	@Column(name="notes", length=100)
	private String notes;
	
	@Column(name="siteId", length=20, nullable=false)
	private String siteId;

	@OneToMany(mappedBy="orderDelivery", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<OrderDeliveryDetails> details = new ArrayList<>();
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierId", referencedColumnName="clientId", insertable=false,updatable=false)
	private Client supplier;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierDetailsId", referencedColumnName="detailId", insertable=false,updatable=false)
	private ClientDetails detailId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="consigneeId", referencedColumnName="clientId", insertable=false,updatable=false)
	private Client consignee;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="consigneeDetailsId", referencedColumnName="detailId", insertable=false,updatable=false)
	private ClientDetails consigneeDetail;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="siteId", referencedColumnName="siteId", insertable=false,updatable=false)
	private WorkUnitDetails workUnitDetail;
	
	@Setter
	@OneToOne(mappedBy="invOrderDelivery", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private InventoryRecord record = new InventoryRecord();
	
	@JsonIgnore
	@Override
	public String getPrefix() {
		return "DH/";
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
}
