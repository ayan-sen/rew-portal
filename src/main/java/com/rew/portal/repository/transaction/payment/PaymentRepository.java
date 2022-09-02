package com.rew.portal.repository.transaction.payment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.payment.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>{

	public List<Payment> findByClientIdOrderByPaymentDateDesc(String clientId);
	
	public List<Payment> findByPaymentDateOrderByPaymentDateDesc(LocalDate paymentDate);
	
	public List<Payment> findByClientIdAndPaymentDateOrderByPaymentDateDesc(String clientId, LocalDate paymentDate);
	
	public List<Payment> findByPaymentDateBetweenOrderByPaymentDateDesc(LocalDate fromDate, LocalDate toDate);
	
	public List<Payment> findByClientIdAndPaymentDateBetweenOrderByPaymentDateDesc(String clientId, LocalDate fromDate, LocalDate toDate);
}
