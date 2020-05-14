package com.rew.portal.model.transaction.record;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.common.PkGenerationSignature;
import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="trancaction_record")
@Table(name="trancaction_record")
public class TransactionRecord implements PkGenerationSignature, Serializable {

	private static final long serialVersionUID = 7689644289435490651L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="transactionId", length=20, nullable=false)
	private String transactionId;
	
	@Column(name="referenceId", length=20, nullable=false)
	private String referenceId;
	
	@JsonIgnore
	@Column(name="referenceDate", length=20, nullable=false)
	private LocalDate referenceDate;
	
	@Transient
	private String referenceDateString;
	
	@Column(name="referenceType", length=20, nullable=false)
	private String referenceType;
	
	@Column(name="buySellFlag", length=4, nullable=false)
	private String buySellFlag;
	
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

	@Override
	public String getPrefix() {
		return "REW/T/";
	}

	@Override
	public String getTableName() {
		return "trancaction_record";
	}

	@Override
	public String getIdColName() {
		return "transactionId";
	}
	
	@Override
	public boolean enableSuffix() {
		return true;
	}
	
	public static TransactionRecord createFromOrderDelivery(OrderDelivery delivery) {
		
		return TransactionRecord.builder()
								.referenceId(delivery.getDeliveryId())
								.referenceDate(delivery.getBillDate())
								.referenceType("OD")
								.buySellFlag("B")
								.amount(delivery.getAmount())
								.cgstAmount(delivery.getCgstAmount())
								.sgstAmount(delivery.getSgstAmount())
								.totalAmount(delivery.getTotalAmount())
								.build();
	}
}
