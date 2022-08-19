package com.rew.portal.controller.transaction.orderProcessing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.transaction.orderProcessing.OrderProcessing;
import com.rew.portal.model.transaction.orderProcessing.OrderProcessingDetails;
import com.rew.portal.service.transaction.orderProcessing.OrderProcessingService;

@Slf4j
@RestController
public class OrderProcessingController {

	@Resource
	private OrderProcessingService orderProcessingService;
	
	@PostMapping("/transaction/process")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody OrderProcessing orderProcessing) {
		Map<String, String> response = new HashMap<>();
		try {
			OrderProcessing op = orderProcessingService.save(orderProcessing);
			response.put("status", "success");
			response.put("message", "Record saved Successfully");
			response.put("id", op.getProcessId().toString());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in order processing", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/transaction/processes")
	public ResponseEntity<List<OrderProcessing>> findAll() {
		return new ResponseEntity<List<OrderProcessing>>(orderProcessingService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/transaction/processes/find")
	public ResponseEntity<OrderProcessing> findById(@RequestParam("id") Integer processingId) {
		OrderProcessing orderProcessing = orderProcessingService.findById(processingId);
		return Objects.isNull(orderProcessing) ? 
				new ResponseEntity<OrderProcessing>(HttpStatus.NOT_FOUND) 
				: new ResponseEntity<OrderProcessing>(orderProcessing, HttpStatus.OK);
	}
	
	@DeleteMapping("/transaction/processes/delete")
	public ResponseEntity<Map<String, String>> delete(@RequestParam("id") Integer processingId) {
		Map<String, String> response = new HashMap<>();
		try {
			orderProcessingService.delete(processingId);
			response.put("status", "success");
			response.put("message", "Order process data deleted successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in order process deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order process deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/transaction/processes/detail/delete")
	public ResponseEntity<Map<String, String>> deleteDetail(@RequestParam("id") Integer processingId, 
			@RequestParam("detailId") Integer detailId ) {
		Map<String, String> response = new HashMap<>();
		try {
			orderProcessingService.deleteDetail(processingId, detailId);
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
	
	@GetMapping("/transaction/processes/materials")
	public ResponseEntity<List<Map<String, Object>>> findMaterialsByProject(@RequestParam("projectId") String projectId, @RequestParam("siteId") String siteId) {
		return new ResponseEntity<List<Map<String, Object>>>(orderProcessingService.getMaterialListByProject(projectId, siteId), HttpStatus.OK);
	}
	
	@GetMapping("/transaction/processes/date")
	public ResponseEntity<Map<Object, Map<Object, List<Object>>>> findbyDate(@RequestParam(name = "logDate", required = true) String logDate,
			@RequestParam(name = "toDate", required = false) String toDate) {
		LocalDate date = LocalDate.parse(logDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate endDate = null;
		if(StringUtils.isNotEmpty(toDate)) {
			endDate = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		return ResponseEntity.ok(orderProcessingService.findByProcessDate(date, endDate));
	}
}
