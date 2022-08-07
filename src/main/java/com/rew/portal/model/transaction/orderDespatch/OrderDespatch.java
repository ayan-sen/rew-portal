package com.rew.portal.model.transaction.orderDespatch;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.common.PkGenerationSignature;

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
@Entity(name="orderdespatch_h")
@Table(name="orderdespatch_h")
public class OrderDespatch implements PkGenerationSignature, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -677978641092481351L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="despatchId", length=20)
	private String despatchId;
	
	@Column(name = "projectId", length = 20, nullable = false)
	private String projectId;
	
	@JsonIgnore
	@Column(name = "despatchDate", nullable = false)
	private LocalDate despatchDate;

	@Transient
	private String despatchDateString;
	
	@Column(name = "siteId", length = 10, nullable = false)
	private String siteId;
	
	@Column(name = "notes", length = 100)
	private String notes;
	
	@Column(name="vehicleNo", length=25)
	private String vehicleNo;
	
	/*
	 * @Column(name="amount", length=20, nullable=false) private Double amount;
	 * 
	 * @Column(name="freightCharges", length=20) private Double freightCharges;
	 * 
	 * @Column(name="cgstAmount", length=20, nullable=false) private Double
	 * cgstAmount;
	 * 
	 * @Column(name="sgstAmount", length=20, nullable=false) private Double
	 * sgstAmount;
	 * 
	 * @Column(name="totalAmount", length=20, nullable=false) private Double
	 * totalAmount;
	 */
	
	@OneToMany(mappedBy = "orderDespatch", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<OrderDespatchDetails> details = new ArrayList<>();
	
	public void removeDetail(int detailId) {
		details.removeIf(d -> d.getDespatchDetailsId() == detailId);
	}
	
	public String getDespatchDateString() {
		if (this.despatchDate != null) {
			return this.despatchDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}

	public void setDespatchDateString(String despatchDateString) {
		this.despatchDateString = despatchDateString;
		if (StringUtils.isNotEmpty(despatchDateString)) {
			this.despatchDate = LocalDate.parse(this.despatchDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}
	
	@JsonIgnore
	@Override
	public String getPrefix() {
		return "REW/S/";
	}

	@JsonIgnore
	@Override
	public String getTableName() {
		return "orderdespatch_h";
	}

	@JsonIgnore
	@Override
	public String getIdColName() {
		return "despatchId";
	}
	
	@Override
	public boolean enableSuffix() {
		return true;
	}

}
