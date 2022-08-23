package com.rew.portal.model.transaction.payment;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Entity(name = "payment_d")
@Table(name = "payment_d")
public class PaymentDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="paymentDetailId", length=20)
	private Integer paymentDetailId;
	
	@Setter
	@Column(name="deliveryId", length=20, nullable=true)
	private String deliveryId;
	
	@Setter
	@Column(name="invoiceId", length=20, nullable=true)
	private String invoiceId;
	
	@Column(name="amount", length=20, nullable=true)
	private Double amount;

	@JsonIgnore
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paymentId", referencedColumnName="paymentId", nullable=false)
	private Payment payment;
	
	@Setter
	@Transient
	private String itemId;
}
