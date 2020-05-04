package com.rew.portal.model.transaction.project;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

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
	
	@Column(name="purchaseOrderDate")
	private LocalDate purchaseOrderDate;
	
	@Column(name="workOrderReference", length=50)
	private String workOrderReference;
	
	@Column(name="expectedDeliveryDate", nullable=false)
	private LocalDate expectedDeliveryDate;
	
	@Column(name="actualDeliveryDate")
	private Date actualDeliveryDate;
	
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
	
	@OneToMany(mappedBy="project", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<ProjectDetails> details = new ArrayList<>();

	@Override
	public String getPrefix() {
		return "REW/P/";
	}

	@Override
	public String getTableName() {
		return "project_h";
	}

	@Override
	public String getIdColName() {
		return "projectId";
	}
	
	@Override
	public boolean enableSuffix() {
		return true;
	}
}
