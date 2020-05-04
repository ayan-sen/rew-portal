package com.rew.portal.repository.transaction.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String>{

}
