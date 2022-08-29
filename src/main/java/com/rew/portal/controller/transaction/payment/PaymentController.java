package com.rew.portal.controller.transaction.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.transaction.payment.Payment;
import com.rew.portal.model.transaction.record.TransactionRecord;
import com.rew.portal.service.transaction.payment.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PaymentController {
	
	@Resource
	private PaymentService paymentService;
	
	@PostMapping("/transaction/payment")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody Payment payment) {
		Map<String, String> response = new HashMap<>();
		try {
			Payment op = paymentService.save(payment);
			response.put("status", "success");
			response.put("message", "Record saved Successfully");
			response.put("id", op.getPaymentId().toString());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in order processing", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during order creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/transaction/payments")
	public ResponseEntity<List<Payment>> findAll() {
		return new ResponseEntity<List<Payment>>(paymentService.findAll(), HttpStatus.OK);
	}
	
//	@GetMapping("/transaction/paymentes/project")
//	public ResponseEntity<List<Payment>> findByProjectId(@RequestParam("projectId") String projectId) {
//		return new ResponseEntity<List<Payment>>(paymentService.getDespatchesByProjectId(projectId), HttpStatus.OK);
//	}
//	
	
	@DeleteMapping("/transaction/payment/detail/delete")
	public ResponseEntity<Map<String, String>> deleteDetail(@RequestParam("id") String paymentId, 
			@RequestParam("detailId") Integer detailId ) {
		Map<String, String> response = new HashMap<>();
		try {
			paymentService.deleteDetail(paymentId, detailId);
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
	
	
	@GetMapping("/transaction/payment/find")
	public ResponseEntity<Payment> findById(@RequestParam("id") String paymentId) {
		Payment payment = paymentService.findById(paymentId);
		return Objects.isNull(payment) ? 
				new ResponseEntity<Payment>(HttpStatus.NOT_FOUND) 
				: new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}
	
	@GetMapping("/transaction/record/client")
	public List<TransactionRecord> findByClient(@RequestParam("clientId") String clientId, @RequestParam("paymentType") String paymentType) {
		return paymentService.findByClientIdAndBuySellFlag(clientId, paymentType);
	}
	
	@GetMapping("/transaction/payment/client")
	public List<Payment> findPaymentDetailsByClient(@RequestParam("clientId") String clientId) {
		return paymentService.findPaymentsByClient(clientId);
	}

}
