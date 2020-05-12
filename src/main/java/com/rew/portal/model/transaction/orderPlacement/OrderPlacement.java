package com.rew.portal.model.transaction.orderPlacement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rew.portal.model.admin.client.Client;
import com.rew.portal.model.admin.client.ClientDetails;
import com.rew.portal.model.admin.companyProfile.WorkUnitDetails;
import com.rew.portal.model.common.PkGenerationSignature;
import com.rew.portal.model.transaction.project.Project;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name = "orderplacement_h")
@Table(name = "orderplacement_h")
public class OrderPlacement implements PkGenerationSignature, Serializable {

	private static final long serialVersionUID = -6600440158781259473L;

	@Id
	@GenericGenerator(name = "id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator = "id-generator")
	@Column(name = "orderId", length = 20)
	private String orderId;

	@JsonIgnore
	@Column(name = "orderDate", nullable = false)
	private LocalDate orderDate;
	
	@Transient
	private String orderDateString;
	
	@Column(name = "supplierId", length = 20)
	private String supplierId;

	@Column(name = "supplierDetailsId")
	private Integer supplierDetailsId;

	@JsonIgnore
	@Column(name = "expectedDeliveryDate", nullable = false)
	private LocalDate expectedDeliveryDate;

	@Transient
	private String expectedDeliveryDateString;

	@JsonIgnore
	@Column(name = "actualDeliveryDate")
	private LocalDate actualDeliveryDate;

	@Transient
	private String actualDeliveryDateString;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "notes")
	private String notes;

	@Column(name = "siteId", length = 20, nullable = false)
	private String siteId;

	@Setter
	@JsonProperty("isActive")
	@Column(name = "isActive", length = 1, nullable = false)
	private Boolean isActive = true;

	@Column(name = "projectId", length = 20, nullable = false)
	private String projectId;

	@Column(name = "freightChargeType", length = 12, nullable = false)
	private String freightChargeType;

	@Column(name = "paymentTerms", length = 100, nullable = false)
	private String paymentTerms;

	@OneToMany(mappedBy = "orderPlacement", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<OrderPlacementDetails> details = new ArrayList<>();

	@JsonIgnore
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierId", referencedColumnName = "clientId", insertable = false, updatable = false)
	private Client supplier;

	@JsonIgnore
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierDetailsId", referencedColumnName = "detailId", insertable = false, updatable = false)
	private ClientDetails supplierDetails;

	@JsonIgnore
	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "siteId", referencedColumnName = "siteId", insertable = false, updatable = false)
	private WorkUnitDetails workUnitDetail;

	@JsonIgnore
	@Setter
	@Transient
	private Project project;

	@JsonIgnore
	@Override
	public String getPrefix() {
		return "REW/O/";
	}

	@JsonIgnore
	@Override
	public String getTableName() {
		return "orderplacement_h";
	}

	@JsonIgnore
	@Override
	public String getIdColName() {
		return "orderId";
	}

	@JsonIgnore
	@Override
	public boolean enableSuffix() {
		return true;
	}

	public void removeDetail(int detailId) {
		details.removeIf(d -> d.getOrderDetailsId() == detailId);
	}

	public String getDescription() {
		if (this.project != null) {
			return this.project.getDescription();
		}
		return null;
	}

	public String getIdentifier() {
		if (this.supplierDetails != null) {
			return this.supplierDetails.getIdentifier();
		}
		return null;
	}

	public String getExpectedDeliveryDateString() {
		if (this.expectedDeliveryDate != null) {
			return this.expectedDeliveryDate.format(DateTimeFormatter
					.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}

	public String getActualDeliveryDateString() {
		if (this.actualDeliveryDate != null) {
			return this.actualDeliveryDate.format(DateTimeFormatter
					.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}

	public void setExpectedDeliveryDateString(String expectedDeliveryDateString) {
		this.expectedDeliveryDateString = expectedDeliveryDateString;
		if (StringUtils.isNotEmpty(expectedDeliveryDateString)) {
			this.expectedDeliveryDate = LocalDate.parse(
					this.expectedDeliveryDateString,
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}

	public void setActualDeliveryDateString(String actualDeliveryDateString) {
		this.actualDeliveryDateString = actualDeliveryDateString;
		if (StringUtils.isNotEmpty(actualDeliveryDateString)) {
			this.actualDeliveryDate = LocalDate.parse(
					this.actualDeliveryDateString,
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}

	public String getSupplierName() {
		if (this.supplier != null) {
			return this.supplier.getClientName();
		}
		return null;
	}
	
	public String getOrderDateString() {
		if (this.orderDate != null) {
			return this.orderDate.format(DateTimeFormatter
					.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}

	public void setOrderDateString(String orderDateString) {
		this.orderDateString = orderDateString;
		if (StringUtils.isNotEmpty(orderDateString)) {
			this.actualDeliveryDate = LocalDate.parse(
					this.orderDateString,
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}
}
