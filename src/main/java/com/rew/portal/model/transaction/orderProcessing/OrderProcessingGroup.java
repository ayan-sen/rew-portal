package com.rew.portal.model.transaction.orderProcessing;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderProcessingGroup implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9157662521203914299L;
	private String projectId;
	private LocalDate logDate;

}
