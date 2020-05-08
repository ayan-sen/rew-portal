package com.rew.portal.service.transaction.project;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javassist.NotFoundException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;
import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.model.transaction.project.ProjectDetails;
import com.rew.portal.repository.transaction.project.ProjectRepository;

@Service
public class ProjectService {

	@Resource
	private ProjectRepository projectRepository;
	
	public Project save(Project project) {
		List<ProjectDetails> details = project.getDetails();
		details.forEach(d -> d.setProject(project));
		return projectRepository.save(project);
	}
	
	public Project findById(String projectId) {
		Optional<Project> opt = projectRepository.findById(projectId);
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<Project> findAll() {
		List<Project> projects = projectRepository.findAll();
		return projects;
	}
	
	public void delete(String projectId) throws NotFoundException {
		Project project = this.findById(projectId);
		if(Objects.nonNull(project)) {
			project.setIsActive(false);
			projectRepository.save(project);
		} else {
			throw new NotFoundException("Project not found with order id" + projectId);
		}
	}
	
	public void deleteDetail(String projectId, int detailId) throws NotFoundException { 
		Project project = this.findById(projectId);
		if(Objects.nonNull(project)) {
			project.removeDetail(detailId);
			projectRepository.save(project);
		} else {
			throw new NotFoundException("Details not found with id " + detailId);
		}
	}
}
