package com.rew.portal.model.transaction.project;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProjectId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String projectId;
	private Integer amendmentNo;
}
