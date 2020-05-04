package com.rew.portal.controller.transaction.project;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.service.transaction.project.ProjectService;

@Slf4j
@RestController
public class ProjectController {

	@Resource
	private ProjectService projectService;
	
	@GetMapping("/transaction/projects")
	public ResponseEntity<List<Project>> findAll() {
		return new ResponseEntity<List<Project>>(
				projectService.findAll(), HttpStatus.OK);
	}
}
