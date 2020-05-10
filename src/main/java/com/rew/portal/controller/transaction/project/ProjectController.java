package com.rew.portal.controller.transaction.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.service.transaction.project.ProjectService;

@Slf4j
@RestController
public class ProjectController {

	@Resource
	private ProjectService projectService;
	
	@PostMapping("/transaction/project")
	public ResponseEntity<Map<String, String>> save(
			@RequestBody Project project) {
		Map<String, String> response = new HashMap<>();
		try {
			projectService.save(project);
			response.put("status", "success");
			response.put("message", "Record saved Successfully");
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in project saving", e);
			response.put("status", "failure");
			response.put("message",
					"Error occurred during project creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/transaction/projects")
	public ResponseEntity<List<Project>> findAll() {
		return new ResponseEntity<List<Project>>(
				projectService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/transaction/projects/find")
	public ResponseEntity<Project> findById(@RequestParam("id") String projectId, 
			@RequestParam("amendmentNo") Integer amendmentNo) {
		Project project = projectService.findById(projectId, amendmentNo);
		return Objects.isNull(project) ? 
				new ResponseEntity<Project>(HttpStatus.NOT_FOUND) 
				: new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@DeleteMapping("/transaction/projects/delete")
	public ResponseEntity<Map<String, String>> delete(@RequestParam("id") String projectId, @RequestParam("amendmentNo") Integer amendmentNo) {
		Map<String, String> response = new HashMap<>();
		try {
			projectService.delete(projectId, amendmentNo);
			response.put("status", "success");
			response.put("message", "Project deleted successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in project deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during project deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/transaction/projects/detail/delete")
	public ResponseEntity<Map<String, String>> deleteDetail(@RequestParam("id") String projectId, 
			@RequestParam("amendmentNo") Integer amendmentNo, @RequestParam("detailId") Integer detailId ) {
		Map<String, String> response = new HashMap<>();
		try {
			projectService.deleteDetail(projectId, amendmentNo, detailId);
			response.put("status", "success");
			response.put("message", "project items removed successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in project detail deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during project item deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
