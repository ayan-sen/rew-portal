package com.rew.portal.model.transaction.payment;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.client.Client;
import com.rew.portal.model.transaction.record.TransactionRecord;

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
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="invoiceId", referencedColumnName="referenceId", insertable=false,updatable=false)
	private TransactionRecord senderRecord;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="deliveryId", referencedColumnName="referenceId", insertable=false,updatable=false)
	private TransactionRecord receiverRecord;
	
	public String getItemId() {
		return StringUtils.isNotEmpty(itemId) ? itemId : StringUtils.isNotEmpty(this.invoiceId) ? invoiceId : deliveryId;
	}
	
	public Double getProjectAmount() {
		return Objects.nonNull(senderRecord) ? senderRecord.getTotalAmount() : receiverRecord.getTotalAmount();
	}
	
	public Double getpaidAmount() {
		return Objects.nonNull(senderRecord) ? senderRecord.getPaidAmount() : receiverRecord.getPaidAmount();
	}
	
	public String getPaymentId() {
		if(this.payment != null) {
			return this.payment.getPaymentId();
		}
		return null;
	} 
}
