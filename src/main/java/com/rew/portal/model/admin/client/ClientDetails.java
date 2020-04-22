package com.rew.portal.model.admin.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name="client_d")
@IdClass(ClientDetailsPk.class)
public class ClientDetails {

	@Id
	@Column(name="clientId")
	private String clientId;
	
	@Id
	@Column(name="detailId")
	private Integer detailId;
	
	@Column(name="address")
	private String address;
	
	@Column(name="pincode")
	private Integer pincode;
	
	@Column(name="state")
	private String state;
	
	@Column(name="identifier")
	private String identifier;
	
	@Column(name="emailId")
	private String emailId;
	
	@Column(name="contactNo")
	private Integer contactNo; 
	
	@Column(name="comments")
	private String comments;
	
	@ManyToOne
	@JoinColumn(name="clientId",referencedColumnName="clientId",insertable=false, updatable=false)
	private Client client;
	
}
