package com.rew.portal.service.transaction.payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.payment.Payment;
import com.rew.portal.model.transaction.payment.PaymentDetails;
import com.rew.portal.model.transaction.payment.PaymentType;
import com.rew.portal.model.transaction.record.TransactionRecord;
import com.rew.portal.repository.transaction.payment.PaymentRepository;
import com.rew.portal.repository.transaction.record.TransactionRecordRepository;

@Service
public class PaymentService {

	@Resource
	private TransactionRecordRepository transactionRecordRepository;
	
	@Resource
	private PaymentRepository paymentRepository;
	
	@Transactional
	public Payment save(Payment payment) {
		//payment.removeEmptyValues();
		List<PaymentDetails> details = payment.getDetails();
		List<TransactionRecord> records = new ArrayList<>();
		details.forEach(d -> {
			d.setPayment(payment);
			TransactionRecord record = transactionRecordRepository.findByReferenceId(d.getItemId());
			TransactionRecord updatedRecord = record.addPaymentDetails(d);
			records.add(updatedRecord);
			if(payment.getPaymentType().equals(PaymentType.SEND)) {
				d.setDeliveryId(d.getItemId());	
			}
			if(payment.getPaymentType().equals(PaymentType.RECEIVE)) {
				d.setInvoiceId(d.getItemId());
			}
		});
		Payment savedPayment = paymentRepository.save(payment);
		TransactionRecord otherPaymentRecord = TransactionRecord.createForOtherPayment(savedPayment);
		records.add(otherPaymentRecord);
		records.forEach(record -> transactionRecordRepository.save(record));
		return savedPayment;
	}
	
	public Payment findById(String paymentId) {
		Payment payment = paymentRepository.findById(paymentId).orElse(null);
		return payment;
	}
	
	public List<Payment> findAll() {
		return paymentRepository.findAll();
	}
	
	@Transactional
	public void delete(String paymentId) throws ObjectNotFoundException {
		Payment payment = this.findById(paymentId);
		TransactionRecord otherPaymentRecord = TransactionRecord.createForOtherPayment(payment);
		if(Objects.nonNull(payment)) {
			paymentRepository.deleteById(paymentId);
			List<PaymentDetails> details = payment.getDetails();
			details.forEach(d -> {
				if(payment.getPaymentType().equals(PaymentType.SEND)) {
					TransactionRecord record = transactionRecordRepository.findByReferenceId(d.getDeliveryId());
					TransactionRecord updatedRecord = record.removePaymentDetails(d);
					transactionRecordRepository.save(updatedRecord);
				}
				if(payment.getPaymentType().equals(PaymentType.RECEIVE)) {
					TransactionRecord record = transactionRecordRepository.findByReferenceId(d.getInvoiceId());
					TransactionRecord updatedRecord = record.removePaymentDetails(d);
					transactionRecordRepository.save(updatedRecord);
				}
			});
			transactionRecordRepository.save(otherPaymentRecord);
			
		} else {
			throw new ObjectNotFoundException("Payment details not found with order id ", paymentId);
		}
	}
	
	@Transactional
	public void deleteDetail(String paymentId, int detailId) throws ObjectNotFoundException {
		Payment payment = this.findById(paymentId);
		TransactionRecord otherPaymentRecord = TransactionRecord.createForOtherPayment(payment);
		if(Objects.nonNull(payment)) {
			Optional<PaymentDetails> opodtls = payment.getDetails().stream().filter(dtl -> dtl.getPaymentDetailId() == detailId).findFirst();
			if(opodtls.isPresent()) {
//				OrderDeliveryDetails odtls = opodtls.get();
				PaymentDetails paymentDetail = opodtls.get();
				payment.removeDetail(detailId);
				paymentRepository.save(payment);
				
				if(payment.getPaymentType().equals(PaymentType.SEND)) {
					TransactionRecord record = transactionRecordRepository.findByReferenceId(paymentDetail.getDeliveryId());
					TransactionRecord updatedRecord = record.removePaymentDetails(paymentDetail);
					transactionRecordRepository.save(updatedRecord);
				}
				if(payment.getPaymentType().equals(PaymentType.RECEIVE)) {
					TransactionRecord record = transactionRecordRepository.findByReferenceId(paymentDetail.getInvoiceId());
					TransactionRecord updatedRecord = record.removePaymentDetails(paymentDetail);
					transactionRecordRepository.save(updatedRecord);
				}
			}
			transactionRecordRepository.save(otherPaymentRecord);
		} else {
			throw new ObjectNotFoundException("Payment details not found with order id ", paymentId);
		}
	}
	
	public List<TransactionRecord> findByClientIdAndBuySellFlag(String clientId, String paymentType) {
		if(PaymentType.RECEIVE.toString().equals(paymentType)) {
			return transactionRecordRepository.findByClientIdAndBuySellFlagAndIsPaymentDoneOrderByReferenceDateAsc(clientId, "S", false);
		}
		return transactionRecordRepository.findByClientIdAndBuySellFlagAndIsPaymentDoneOrderByReferenceDateAsc(clientId, "B", false);
	}
	
	public List<Payment> findPaymentsByClient(String clientId) {
		List<Payment> payments = paymentRepository.findByClientIdOrderByPaymentDateDesc(clientId);
		Double totalPayment = payments.stream().map(p -> p.getTotalPayment()).reduce(0.0, (a, b) ->a + b);
		
		return payments;
	}

	public Map<String, Object> findPaymentDetails(LocalDate fromDate, LocalDate toDate, String clientId) {
		List<Payment> payments = new ArrayList<>();
		if(Objects.nonNull(toDate) && Objects.nonNull(fromDate) && StringUtils.isNotEmpty(clientId)) {
			payments = paymentRepository.findByClientIdAndPaymentDateBetweenOrderByPaymentDateDesc(clientId, fromDate, toDate);
		}
		if(Objects.isNull(toDate) && Objects.nonNull(fromDate) && StringUtils.isNotEmpty(clientId)) {
			payments = paymentRepository.findByClientIdAndPaymentDateOrderByPaymentDateDesc(clientId, fromDate);
		}
		if(Objects.isNull(toDate) && Objects.isNull(fromDate) && StringUtils.isNotEmpty(clientId)) {
			payments = paymentRepository.findByClientIdOrderByPaymentDateDesc(clientId);
		}
		
		if(Objects.nonNull(toDate) && Objects.nonNull(fromDate) && StringUtils.isEmpty(clientId)) {
			payments = paymentRepository.findByPaymentDateBetweenOrderByPaymentDateDesc(fromDate, toDate);
		}
		
		Double totalPayment = payments.stream().map(p -> p.getTotalPayment()).reduce(0.0, (a, b) ->a + b);
		Map<String, Map<PaymentType, Double>> paymentGrouping = payments.stream()
				.collect(Collectors.groupingBy(p -> p.getClient().getClientName(), 
						Collectors.groupingBy(p ->p.getPaymentType(), Collectors.summingDouble(p -> p.getTotalPayment()))));
		
		Map<String, Object> details = new HashMap<>();
		details.put("view", paymentGrouping);
		details.put("details", payments);
		
		return details;
		
		
	}
}
