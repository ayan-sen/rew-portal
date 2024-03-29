package com.rew.portal.service.transaction.project;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.model.transaction.project.ProjectDetails;
import com.rew.portal.model.transaction.project.ProjectId;
import com.rew.portal.repository.transaction.project.ProjectRepository;

@Service
public class ProjectService {

	@Resource
	private ProjectRepository projectRepository;
	
	@Transactional
	public Project save(Project project) {
		List<ProjectDetails> details = project.getDetails();
		details.forEach(d -> d.setProject(project));
		return projectRepository.save(project);
	}
	
	public Project findById(String projectId, Integer amendmentNo) {
		Optional<Project> opt = projectRepository.findById(new ProjectId(projectId, amendmentNo));
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<Project> findAll() {
		List<Project> projects = projectRepository.findByIsActive(true);
		return projects;
	}
	
	public void delete(String projectId, Integer amendmentNo) throws ObjectNotFoundException {
		Project project = this.findById(projectId, amendmentNo);
		if(Objects.nonNull(project)) {
			project.setIsActive(false);
			projectRepository.save(project);
		} else {
			throw new ObjectNotFoundException("Project not found with order id", projectId);
		}
	}
	
	public void deleteDetail(String projectId, Integer amendmentNo, Integer detailId) throws ObjectNotFoundException { 
		Project project = this.findById(projectId, amendmentNo);
		if(Objects.nonNull(project)) {
			project.removeDetail(detailId);
			project.calculate();
			projectRepository.save(project);
		} else {
			throw new ObjectNotFoundException("Details not found with id ", detailId.toString());
		}
	}
}
