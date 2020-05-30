package com.rew.portal.repository.transaction.orderProcessing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.orderProcessing.OrderProcessing;

@Repository
public interface OrderProcessingRepository extends JpaRepository<OrderProcessing, Integer>{

	public List<OrderProcessing> findByIsActive(boolean isActive);
}
