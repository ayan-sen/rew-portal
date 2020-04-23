package com.rew.portal.model.admin.client;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="client_d")
public class ClientDetails implements Serializable{

	private static final long serialVersionUID = -705596178079573618L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer detailId;
	
	@Column(name="address")
	private String address;
	
	@Column(name="pincode")
	private Long pincode;
	
	@Column(name="identifier")
	private String identifier;
	
	@Column(name="emailId")
	private String emailId;
	
	@Column(name="contactNo")
	private Long contactNo; 
	
	@Column(name="comments")
	private String comments;
	
	@JsonIgnore
	@Setter
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Client.class)
	@JoinColumn(name = "clientId", referencedColumnName = "clientId")
	private Client client;
	
	public String getClientId() {
		if(this.client != null) {
			return this.client.getClientId();
		}
		return null;
	}
}
