package com.rew.portal.model.transaction.payment;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.client.Client;
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
@Entity(name = "payment_h")
@Table(name = "payment_h")
public class Payment implements PkGenerationSignature, Serializable  {
	
	private static final long serialVersionUID = 4187769986420503128L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="paymentId", length=20)
	private String paymentId;
	
	@Column(name="paymentDate", nullable=false)
	private LocalDate paymentDate;
	
	@Transient
	private String paymentDateString;
	
	@Column(name="paymentRefNo", length=20, nullable=true)
	private String paymentRefNo;
	
	@Column(name="clientId", length=20, nullable=false)
	private String clientId;
	
	@Column(name="projectPayment", length=20, nullable=false)
	private Double projectPayment;
	
	@Column(name="otherPayment", length=20, nullable=true)
	private Double otherPayment;
	
	@Column(name="totalPayment", length=20, nullable=false)
	private Double totalPayment;

	@Column(name="notes", length=20, nullable=true)
	private String notes;
	
	@Column(name="paymentType", length=10, nullable=true)
	private PaymentType paymentType;

	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="clientId", referencedColumnName="clientId", insertable=false,updatable=false)
	private Client client;
	
	@OneToMany(mappedBy="payment", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<PaymentDetails> details = new ArrayList<>();
	
	public void removeDetail(int detailId) {
		details.removeIf(d -> d.getPaymentDetailId() == detailId);
	}
	
	@Override
	public String getPrefix() {
		return "REW/P/";
	}

	@Override
	public String getTableName() {
		return "payment_h";
	}

	@Override
	public String getIdColName() {
		return "paymentId";
	}
}
