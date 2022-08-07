package com.rew.portal.controller.transaction.orderDespatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.transaction.orderDespatch.OrderDespatch;
import com.rew.portal.service.transaction.orderDespatch.OrderDespatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OrderDespatchController {

	@Resource
	private OrderDespatchService orderDespatchService;
	
	@PostMapping("/transaction/despatch")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody OrderDespatch orderDespatch) {
		Map<String, String> response = new HashMap<>();
		try {
			OrderDespatch op = orderDespatchService.save(orderDespatch);
			response.put("status", "success");
			response.put("message", "Record saved Successfully");
			response.put("id", op.getDespatchId().toString());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in order processing", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/transaction/despatches")
	public ResponseEntity<List<OrderDespatch>> findAll() {
		return new ResponseEntity<List<OrderDespatch>>(orderDespatchService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/transaction/despatches/project")
	public ResponseEntity<List<OrderDespatch>> findByProjectId(@RequestParam("projectId") String projectId) {
		return new ResponseEntity<List<OrderDespatch>>(orderDespatchService.getDespatchesByProjectId(projectId), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/transaction/despatches/detail/delete")
	public ResponseEntity<Map<String, String>> deleteDetail(@RequestParam("id") String despatchId, 
			@RequestParam("detailId") Integer detailId ) {
		Map<String, String> response = new HashMap<>();
		try {
			orderDespatchService.deleteDetail(despatchId, detailId);
			response.put("status", "success");
			response.put("message", "Details removed successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in order processing record deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order processing record deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/transaction/despatches/materials")
	public ResponseEntity<List<Map<String, Object>>> findMaterialsByProject(@RequestParam("projectId") String projectId, @RequestParam("siteId") String siteId) {
		return new ResponseEntity<List<Map<String, Object>>>(orderDespatchService.getMaterialListByProject(projectId, siteId), HttpStatus.OK);
	}
}
