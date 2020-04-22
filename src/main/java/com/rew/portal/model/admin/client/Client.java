package com.rew.portal.model.admin.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

import com.rew.portal.model.common.PkGenerationSignature;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity(name="client_h")
public class Client implements PkGenerationSignature, Serializable {

	private static final long serialVersionUID = -3599351613656484513L;

	@Id
	@GenericGenerator(name="id-generator", strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	private String clientId;
	
	@Column(name="clientName")
	private String clientName;
	
	@Column(name="clientType")
	private String clientType;
	
	@Column(name="gstin")
	private String gstinNo;
	
	@Column(name="contactNoPrimary")
	private Integer primanyContactNo;
	
	@Column(name="emailPrimary")
	private String primaryEmailId;
	
	@Column(name="comments")
	private String comments;
	
	@Column(name="isActive")
	private boolean isActive;
	
	@OneToMany(mappedBy="client", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
    private List<ClientDetails> details = new ArrayList<>();

	@Override
	public String getPrefix() {
		return "P/";
	}

	@Override
	public String getTableName() {
		return "client_h";
	}

	@Override
	public String getidColName() {
		return "clientId";
	}
}
