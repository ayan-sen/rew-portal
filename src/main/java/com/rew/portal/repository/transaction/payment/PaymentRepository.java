package com.rew.portal.repository.transaction.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.payment.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>{

}
