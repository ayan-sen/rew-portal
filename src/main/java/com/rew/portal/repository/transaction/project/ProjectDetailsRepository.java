package com.rew.portal.repository.transaction.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.project.ProjectDetails;

@Repository
public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, Integer> {

	public ProjectDetails findOneByProject_ProjectIdAndProject_AmendmentNoAndRmId(String projectId, Integer amendmentNo, String rmId);
	
	
}
