package com.rew.portal.model.transaction.orderProcessing;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@Entity(name="orderprocessing_h")
@Table(name="orderprocessing_h")
public class OrderProcessing implements Serializable {

	private static final long serialVersionUID = -6850360047765278492L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="processId", length=20)
	private Integer processId;
	
	@Column(name="projectId", length=20, nullable=false)
	private String projectId;
	
	@Column(name="processDate", nullable=false)
	private LocalDate processDate;
	
	@Column(name="siteId", length=10, nullable=false)
	private String siteId;
	
	@Column(name="notes", length=100)
	private String notes;
	
	@Setter
	@JsonProperty("isActive")
	@Column(name="isActive", length=1, nullable=false)
	private Boolean isActive = true;
	
	@OneToMany(mappedBy = "orderProcessing", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<OrderProcessingDetails> details = new ArrayList<>();
	
	public void removeDetail(int detailId) {
		details.removeIf(d -> d.getProcessDetailsId() == detailId);
	}
}