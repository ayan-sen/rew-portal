package com.rew.portal.model.transaction.project;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rew.portal.model.admin.client.Client;
import com.rew.portal.model.common.PkGenerationSignature;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="project_h")
@Table(name="project_h")
@IdClass(PojectId.class)
public class Project implements PkGenerationSignature, Serializable {

	private static final long serialVersionUID = 8902793404469284744L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="projectId", length=20)
	private String projectId;
	
	@Id
	@Column(name="amendmentNo", length=2)
	private Integer amendmentNo;
	
	@Column(name="amendmentDate", length=20)
	private LocalDate amendmentDate;
	
	@Column(name="description", length=100, nullable=false)
	private String description;
	
	@Column(name="customerId", length=20, nullable=false)
	private String customerId;
	
	@Column(name="consigneeId", length=20)
	private String consigneeId;
	
	@Column(name="purchaseOrderNo", length=50)
	private String purchaseOrderNo;
	
	@JsonIgnore
	@Column(name="purchaseOrderDate")
	private LocalDate purchaseOrderDate;
	
	@Transient
	private String purchaseOrderDateString;
	
	@Column(name="workOrderReference", length=50)
	private String workOrderReference;
	
	@JsonIgnore
	@Column(name="expectedDeliveryDate", nullable=false)
	private LocalDate expectedDeliveryDate;
	
	@Transient
	private String expectedDeliveryDateString;
	
	@JsonIgnore
	@Column(name="actualDeliveryDate")
	private LocalDate actualDeliveryDate;
	
	@Transient
	private String actualDeliveryDateString;
	
	@Column(name="status", length=10, nullable=false)
	private String status;
	
	@Column(name="notes", length=100)
	private String notes;
	
	@Column(name="amount", length=20, nullable=false)
	private Double amount;
	
	@Column(name="otherCharges", length=20)
	private Double otherCharges;
	
	@Column(name="cgstAmount", length=20, nullable=false)
	private Double cgstAmount;
	
	@Column(name="sgstAmount", length=20, nullable=false)
	private Double sgstAmount;
	
	@Column(name="totalAmount", length=20, nullable=false)
	private Double totalAmount;
	
	@Setter
	@JsonProperty("isActive")
	@Column(name="isActive", length=1, nullable=false)
	private Boolean isActive = true;
	
	@OneToMany(mappedBy="project", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<ProjectDetails> details = new ArrayList<>();
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customerId", referencedColumnName="clientId", insertable=false,updatable=false)
	private Client customer;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="consigneeId", referencedColumnName="clientId", insertable=false,updatable=false)
	private Client consignee;

	@JsonIgnore
	@Override
	public String getPrefix() {
		return "REW/P/";
	}

	@JsonIgnore
	@Override
	public String getTableName() {
		return "project_h";
	}

	@JsonIgnore
	@Override
	public String getIdColName() {
		return "projectId";
	}
	
	@JsonIgnore
	@Override
	public boolean enableSuffix() {
		return true;
	}
	
	public String getCustomerName() {
		if(this.customer != null) {
			return this.customer.getClientName();
		}
		return null;
	}
	
	public String getConsigneeName() {
		if(this.consignee != null) {
			return this.consignee.getClientName();
		}
		return null;
	}
	
	public String getPurchaseOrderDateString() {
		if(this.purchaseOrderDate != null) {
			return this.purchaseOrderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}
	
	public String getExpectedDeliveryDateString() {
		if(this.expectedDeliveryDate != null) {
			return this.expectedDeliveryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}
	
	public String getActualDeliveryDateString() {
		if(this.actualDeliveryDate != null) {
			return this.actualDeliveryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}
	
	public void removeDetail(int detailId) {
		details.removeIf(d -> d.getDetailId() == detailId);
	}
}
