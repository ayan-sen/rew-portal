package com.rew.portal.model.admin.client;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.rew.portal.model.common.PkGenerationSignature;

@Getter
@AllArgsConstructor
@ToString
@Builder
@Entity(name="client_h")
public class Client implements PkGenerationSignature {

	@Id
	@GenericGenerator(name="id-generator", 
						parameters={@Parameter(name = "table", value = "client_h"),
									@Parameter(name = "id", value = "clientId"),
									@Parameter(name = "prefix", value = "P/")}, 
						strategy = "com.rew.portal.repository.common.SequenceGenerator")
	@GeneratedValue(generator="id-generator")
	@Column(name="clientId")
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
	
	@OneToMany(mappedBy="client",cascade=CascadeType.ALL)
    private List<ClientDetails> details;

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
