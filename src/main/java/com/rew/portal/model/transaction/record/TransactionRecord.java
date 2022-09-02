package com.rew.portal.model.transaction.record;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rew.portal.model.transaction.invoice.Invoice;
import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;
import com.rew.portal.model.transaction.payment.Payment;
import com.rew.portal.model.transaction.payment.PaymentDetails;
import com.rew.portal.model.transaction.payment.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="trancaction_record")
@Table(name="trancaction_record")
public class TransactionRecord implements Serializable {

	private static final long serialVersionUID = 7689644289435490651L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="transactionId", length=20, nullable=false)
	private Integer transactionId;
	
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
	
	@Column(name="amount", length=20, nullable=true)
	private Double amount;
	
	@Column(name="freightCharges", length=20)
	private Double freightCharges;
	
	@Column(name="cgstAmount", length=20, nullable=true)
	private Double cgstAmount;
	
	@Column(name="sgstAmount", length=20, nullable=true)
	private Double sgstAmount;
	
	@Column(name="totalAmount", length=20, nullable=true)
	private Double totalAmount;
	
	@Column(name="clientId", length=20, nullable=true)
	private String clientId;
	
	@Column(name="paidAmount", length=20, nullable=true)
	private Double paidAmount;
	
	@Column(name="paymentReferenceId", length=20, nullable=true)
	private String paymentReferenceId;
	
	@JsonProperty("isPaymentDone")
	@Column(name="isPaymentDone", length=1, nullable=false)
	private Boolean isPaymentDone;

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
								.freightCharges(delivery.getFreightCharges())
								.clientId(delivery.getSupplierId())
								.isPaymentDone(false)
								.build();
	}
	
	public static TransactionRecord createFromInvoice(Invoice invoice) {
			
			return TransactionRecord.builder()
									.referenceId(invoice.getInvoiceId())
									.referenceDate(invoice.getInvoiceDate())
									.referenceType("OS")
									.buySellFlag("S")
									.amount(invoice.getAmount())
									.cgstAmount(invoice.getCgstAmount())
									.sgstAmount(invoice.getSgstAmount())
									.totalAmount(invoice.getTotalAmount())
									.freightCharges(invoice.getFreightCharges())
									.clientId(invoice.getClientId())
									.isPaymentDone(false)
									.build();
		}
		
	public TransactionRecord addPaymentDetails(PaymentDetails paymentDetails) {
		this.paidAmount = Optional.ofNullable(this.paidAmount).orElse(0.0) + paymentDetails.getAmount();
		this.paymentReferenceId = paymentDetails.getPayment().getPaymentId();
		if(Double.compare(this.paidAmount, this.totalAmount) == 0) {
			this.isPaymentDone = true;
		} else {
			this.isPaymentDone =false;
		}
		return this;
	}
	
	public TransactionRecord removePaymentDetails(PaymentDetails paymentDetails) {
		this.paidAmount = Optional.ofNullable(this.paidAmount).orElse(0.0) - paymentDetails.getAmount();
		this.paymentReferenceId = paymentDetails.getPayment().getPaymentId();
		if(Double.compare(this.paidAmount, this.totalAmount) == 0) {
			this.isPaymentDone = true;
		} else {
			this.isPaymentDone = false;
		}
		return this;
	}
	
	public static TransactionRecord createForOtherPayment(Payment payment) {
		String buySellInd = null;
		if(payment.getPaymentType().equals(PaymentType.SEND)) {
			buySellInd = "B";
		} else {
			buySellInd = "S";
		}
		
		return TransactionRecord.builder()
								.referenceId(payment.getPaymentId())
								.referenceDate(payment.getPaymentDate())
								.referenceType("PP")
								.buySellFlag(buySellInd)
								.clientId(payment.getClientId())
								.paidAmount(payment.getOtherPayment())
								.isPaymentDone(true)
								.build();
	}

}
