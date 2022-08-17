package com.rew.portal.controller.transaction.invoice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.transaction.invoice.Invoice;
import com.rew.portal.service.transaction.invoice.InvoiceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class InvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@PostMapping("/transaction/invoice")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody Invoice invoice) {
		Map<String, String> response = new HashMap<>();
		try {
			invoiceService.save(invoice);
			response.put("status", "success");
			response.put("message", "Record saved Successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in order creation", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/transaction/invoices")
	public ResponseEntity<List<Invoice>> findAll() {
		return new ResponseEntity<List<Invoice>>(invoiceService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/transaction/invoice/find")
	public ResponseEntity<Invoice> findById(@RequestParam("id") String deliveryId) {
		Invoice invoice = invoiceService.findById(deliveryId);
		return Objects.isNull(invoice) ? 
				new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND) 
				: new ResponseEntity<Invoice>(invoice, HttpStatus.OK);
	}
	
	@DeleteMapping("/transaction/invoices/delete")
	public ResponseEntity<Map<String, String>> delete(@RequestParam("id") String deliveryId) {
		Map<String, String> response = new HashMap<>();
		try {
			invoiceService.delete(deliveryId);
			response.put("status", "success");
			response.put("message", "Invoice deleted successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in order deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/transaction/invoices/detail/delete")
	public ResponseEntity<Map<String, String>> deleteDetail(@RequestParam("id") String invoiceId, 
			@RequestParam("detailId") Integer detailId ) {
		Map<String, String> response = new HashMap<>();
		try {
			invoiceService.deleteDetail(invoiceId, detailId);
			response.put("status", "success");
			response.put("message", "Delivery details removed successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in order detail deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order delivery details deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/transaction/invoice/products")
	public ResponseEntity<List<Map<String, Object>>> findMaterialsByProject(@RequestParam("projectId") String projectId) {
		return new ResponseEntity<List<Map<String, Object>>>(invoiceService.getMaterialListByProject(projectId, "SINGUR"), HttpStatus.OK);
	}
	
}
