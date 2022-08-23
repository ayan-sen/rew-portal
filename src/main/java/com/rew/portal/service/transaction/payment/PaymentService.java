package com.rew.portal.service.transaction.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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
			
		} else {
			throw new ObjectNotFoundException("Payment details not found with order id ", paymentId);
		}
	}
	
	@Transactional
	public void deleteDetail(String paymentId, int detailId) throws ObjectNotFoundException {
		Payment payment = this.findById(paymentId);
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
}
