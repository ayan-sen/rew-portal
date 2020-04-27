package com.rew.portal.model.admin.companyProfile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;

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
@Entity(name="work_unit")
@Table(name="work_unit")
public class WorkUnitDetails implements Serializable {

	private static final long serialVersionUID = -3969707934460181232L;

	@Id
	@Column(name="siteId", length=10)
	private String siteId;
	
	@Column(name="siteName", length=50, nullable=false)
	private String siteName;
	
	@Column(name="address", length=100, nullable=false)
	private String address;
	
	@JsonIgnore
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", referencedColumnName="id", nullable=false)
	private CompanyProfile companyProfile;
	
}
