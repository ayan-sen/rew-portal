package com.rew.portal.controller.transaction.orderPlacement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;
import com.rew.portal.service.transaction.orderPlacement.OrderPlacementService;

@Slf4j
@RestController
public class OrderPlacementController {

	@Resource
	private OrderPlacementService orderPlacementService;
	
	@PostMapping("/transaction/order")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody OrderPlacement orderPlacement) {
		Map<String, String> response = new HashMap<>();
		try {
			orderPlacementService.save(orderPlacement);
			response.put("status", "success");
			response.put("message", "Record saved Successfully");
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in order creation", e);
			response.put("status", "failure");
			response.put("message",
					"Error occurred during order creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/transaction/orders")
	public ResponseEntity<List<OrderPlacement>> findAll() {
		return new ResponseEntity<List<OrderPlacement>>(
				orderPlacementService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/transaction/orders/find")
	public ResponseEntity<OrderPlacement> findById(@RequestParam("id") String orderId) {
		OrderPlacement orderPlacement = orderPlacementService.findById(orderId);
		return Objects.isNull(orderPlacement) ? 
				new ResponseEntity<OrderPlacement>(HttpStatus.NOT_FOUND) 
				: new ResponseEntity<OrderPlacement>(orderPlacement, HttpStatus.OK);
	}
	
	@DeleteMapping("/transaction/orders/delete")
	public ResponseEntity<Map<String, String>> delete(@RequestParam("id") String orderId) {
		Map<String, String> response = new HashMap<>();
		try {
			orderPlacementService.delete(orderId);
			response.put("status", "success");
			response.put("message", "Order deleted successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in order deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/transaction/orders/detail/delete")
	public ResponseEntity<Map<String, String>> deleteDetail(@RequestParam("id") String orderId, 
			@RequestParam("detailId") Integer detailId ) {
		Map<String, String> response = new HashMap<>();
		try {
			orderPlacementService.deleteDetail(orderId, detailId);
			response.put("status", "success");
			response.put("message", "Order details removed successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in order detail deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order details deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/transaction/orders/invoice", produces = MediaType.APPLICATION_PDF_VALUE )
	public ResponseEntity<InputStreamResource> generateInvoice(@RequestParam("id") String orderId) {
		
		try {
			ByteArrayInputStream bis = orderPlacementService.generateInvoice(orderId);
			HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Disposition", "inline; filename=Invoice_"+orderId+".pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
		} catch (Exception e) {
			return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
