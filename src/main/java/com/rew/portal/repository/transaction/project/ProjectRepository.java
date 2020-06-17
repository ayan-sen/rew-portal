package com.rew.portal.repository.transaction.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.model.transaction.project.ProjectId;

@Repository
public interface ProjectRepository extends JpaRepository<Project, ProjectId>{

	@Query("SELECT p FROM project_h p WHERE p.projectId=?1 AND p.amendmentNo=(SELECT MAX(q.amendmentNo) FROM project_h q WHERE q.projectId=?1)")
	public Project findLatest(String projectId);
	
	public List<Project> findByIsActive(boolean isActive);
}
