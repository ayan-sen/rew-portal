package com.rew.portal.controller.report;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.service.report.ReportService;

@RestController
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/report/project")
	public ResponseEntity<Map<String,Object>> getProductionReportProjectWise(@RequestParam("projectId") String projectId) {
		return ResponseEntity.ok(reportService.getProjectWiseProductionReport(projectId));
		
	}

}
