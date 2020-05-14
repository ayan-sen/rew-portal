package com.rew.portal.controller.transaction.orderDelivery;

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

import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;
import com.rew.portal.service.transaction.orderDelivery.OrderDeliveryService;

@Slf4j
@RestController
public class OrderDeliveryController {

	@Resource
	private OrderDeliveryService orderDeliveryService;
	
	@PostMapping("/transaction/delivery")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody OrderDelivery orderDelivery) {
		Map<String, String> response = new HashMap<>();
		try {
			orderDeliveryService.save(orderDelivery);
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
	
	
	@GetMapping("/transaction/deliveries")
	public ResponseEntity<List<OrderDelivery>> findAll() {
		return new ResponseEntity<List<OrderDelivery>>(orderDeliveryService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/transaction/deliveries/find")
	public ResponseEntity<OrderDelivery> findById(@RequestParam("id") String deliveryId) {
		OrderDelivery orderDelivery = orderDeliveryService.findById(deliveryId);
		return Objects.isNull(orderDelivery) ? 
				new ResponseEntity<OrderDelivery>(HttpStatus.NOT_FOUND) 
				: new ResponseEntity<OrderDelivery>(orderDelivery, HttpStatus.OK);
	}
	
	@DeleteMapping("/transaction/deliveries/delete")
	public ResponseEntity<Map<String, String>> delete(@RequestParam("id") String deliveryId) {
		Map<String, String> response = new HashMap<>();
		try {
			orderDeliveryService.delete(deliveryId);
			response.put("status", "success");
			response.put("message", "Order delivery deleted successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in order deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/transaction/deliveries/detail/delete")
	public ResponseEntity<Map<String, String>> deleteDetail(@RequestParam("id") String deliveryId, 
			@RequestParam("detailId") Integer detailId ) {
		Map<String, String> response = new HashMap<>();
		try {
			orderDeliveryService.deleteDetail(deliveryId, detailId);
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
}
