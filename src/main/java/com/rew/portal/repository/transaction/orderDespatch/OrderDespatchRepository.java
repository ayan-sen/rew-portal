package com.rew.portal.repository.transaction.orderDespatch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.orderDespatch.OrderDespatch;

@Repository
public interface OrderDespatchRepository extends JpaRepository<OrderDespatch, String> {

}
