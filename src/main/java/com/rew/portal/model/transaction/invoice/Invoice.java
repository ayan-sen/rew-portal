package com.rew.portal.model.transaction.invoice;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rew.portal.model.admin.client.Client;
import com.rew.portal.model.admin.client.ClientDetails;
import com.rew.portal.model.common.PkGenerationSignature;
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
@Entity(name="invoice_h")
@Table(name="invoice_h")
public class Invoice implements PkGenerationSignature, Serializable {
	
	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="invoiceId", length=20)
	private String invoiceId;
	
	@Column(name="invoiceDate", nullable=false)
	private LocalDate invoiceDate;
	
	@Transient
	private String invoiceDateString;
	
	@Column(name="projectId", length=20, nullable = false)
	private String projectId;
	
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
	
	@Column(name="notes", length=100)
	private String notes;
	
	@Column(name="vehicleNo", length=100)
	private String vehicleNo;
	
	@Column(name="clientId", length=20, nullable=true)
	private String clientId;
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="clientId", referencedColumnName="clientId", insertable=false,updatable=false)
	private Client client;
	
	@OneToMany(mappedBy="invoice", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<InvoiceDetails> details = new ArrayList<>();
	
	
	public String getInvoiceDateString() {
		if(this.invoiceDate != null) {
			return this.invoiceDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}
	
	public void setInvoiceDateString(String invoiceDateString) {
		this.invoiceDateString = invoiceDateString;
		if(StringUtils.isNotEmpty(invoiceDateString)) {
			this.invoiceDate = LocalDate.parse(this.invoiceDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}
	
	@JsonIgnore
	@Override
	public String getPrefix() {
		return "REW/I/";
	}

	@JsonIgnore
	@Override
	public String getTableName() {
		return "invoice_h";
	}

	@JsonIgnore
	@Override
	public String getIdColName() {
		return "invoiceId";
	}
	
	@Override
	public boolean enableSuffix() {
		return true;
	}

	public void removeDetail(int invoiceDetailId) {
		details.removeIf(d -> d.getInvoiceDetailsId() == invoiceDetailId);
	}
	
	@JsonIgnore
	public String getClientName() {
		if (this.client != null) {
			return this.client.getClientName();
		}
		return null;
	}
}
