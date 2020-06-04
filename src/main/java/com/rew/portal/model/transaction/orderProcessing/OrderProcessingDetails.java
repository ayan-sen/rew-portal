package com.rew.portal.model.transaction.orderProcessing;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="orderprocessing_d")
@Table(name="orderprocessing_d")
public class OrderProcessingDetails implements Serializable {

	private static final long serialVersionUID = -8016661551833076006L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="processDetailsId", length=20)
	private Integer processDetailsId;
	
	@Column(name="processType", length=20, nullable=false)
	private String processType;
	
	@Column(name="materialId", length=20)
	private String materialId;
	
	@Column(name="materialUnit", length=20)
	private String materialUnit;
	
	@Column(name="quantity", length=20)
	private Double quantity;
	
	@Column(name="materialType", length=20)
	private String materialType;
	
	@Setter
	@Transient
	private Double availableQuantity;
	
	@Setter
	@Transient
	private Double remainingQuantity;
	
	@Column(name="notes", length=100)
	private String notes;
	
	@JsonIgnore
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processId", referencedColumnName="processId", nullable=false)
	private OrderProcessing orderProcessing;
}
