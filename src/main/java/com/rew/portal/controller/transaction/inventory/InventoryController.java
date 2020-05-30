package com.rew.portal.controller.transaction.inventory;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.service.transaction.inventory.InventoryService;

@RestController
public class InventoryController {

	@Resource
	private InventoryService inventoryService;

	@SuppressWarnings("rawtypes")
	@GetMapping("/transaction/inventory")
	public ResponseEntity<Map<String, Collection>> getStatus() {
		return new ResponseEntity<Map<String, Collection>>(inventoryService.getStatus(), HttpStatus.OK);
	}
	
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/transaction/inventory/project")
	public ResponseEntity<Map<String, Double>> getStatusByProjectIdAndSiteId(
			@RequestParam(name="projectId", required=true) String projectId,
			@RequestParam(name="siteId", required=true) String siteId) {
		return new ResponseEntity<Map<String, Double>>(inventoryService.
				getMaterialStatusByProjectIdAndSiteId(projectId, siteId), HttpStatus.OK);
	}
}
