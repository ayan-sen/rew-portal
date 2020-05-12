package com.rew.portal.model.admin.companyProfile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rew.portal.model.transaction.orderDelivery.OrderDeliveryDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="company_profile")
@Table(name="company_profile")
public class CompanyProfile implements Serializable {

	private static final long serialVersionUID = -8966176385931928801L;

	@Id
	@Column(name="id", length=5)
	private String id;
	
	@Column(name="companyName", length=50, nullable=false)
	private String companyName;
	
	@Column(name="gstin", length=15, nullable=false)
	private String gstinNo;
	
	@Column(name="pan", length=10, nullable=false)
	private String pan;
	
	@Column(name="regAddress", length=200, nullable=false)
	private String regAddress;
	
	@Column(name="primaryEmailId", length=50, nullable=false)
	private String primaryEmailId;
	
	@Column(name="secondaryEmailId", length=50, nullable=false)
	private String secondaryEmailId;
	
	@Column(name="primaryContactNo", length=15, nullable=false)
	private String primaryContactNo;
	
	@Column(name="secondContactNo", length=15, nullable=false)
	private String secondContactNo;
	
	@Column(name="website", length=30, nullable=false)
	private String website;
	
	@Column(name="cin", length=40, nullable=false)
	private String cin;
	
	@OneToMany(mappedBy="companyProfile", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<WorkUnitDetails> details = new ArrayList<>();
}
