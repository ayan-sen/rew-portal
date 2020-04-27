package com.rew.portal.model.transaction.orderPlacement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.rew.portal.model.admin.client.Client;
import com.rew.portal.model.admin.client.ClientDetails;
import com.rew.portal.model.admin.companyProfile.WorkUnitDetails;
import com.rew.portal.model.common.PkGenerationSignature;
import com.rew.portal.model.transaction.orderDelivery.OrderDeliveryDetails;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="orderplacement_h")
@Table(name="orderplacement_h")
public class OrderPlacement implements PkGenerationSignature, Serializable {

	private static final long serialVersionUID = -6600440158781259473L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="orderId", length=20)
	private String orderId;
	
	@Column(name="supplierId", length=20)
	private String supplierId;
	
	@Column(name="supplierDetailsId")
	private Integer supplierDetailsId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="expectedDeliveryDate", nullable=false)
	private Date expectedDeliveryDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="actualDeliveryDate")
	private Date actualDeliveryDate;
	
	@Column(name="status", nullable=false)
	private String status;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="siteId", length=20, nullable=false)
	private String siteId;
	
	@OneToMany(mappedBy="orderPlacement", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    private List<OrderPlacementDetails> details = new ArrayList<>();
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierId", referencedColumnName="clientId", insertable=false,updatable=false)
	private Client supplier;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierDetailsId", referencedColumnName="detailId", insertable=false,updatable=false)
	private ClientDetails detailId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="siteId", referencedColumnName="siteId", insertable=false,updatable=false)
	private WorkUnitDetails workUnitDetail;

	@Override
	public String getPrefix() {
		return "REW/O/";
	}

	@Override
	public String getTableName() {
		return "orderplacement_h";
	}

	@Override
	public String getIdColName() {
		return "orderId";
	}
}
