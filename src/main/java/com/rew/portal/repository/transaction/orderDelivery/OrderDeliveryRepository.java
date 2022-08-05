package com.rew.portal.repository.transaction.orderDelivery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;

@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, String> {

	public List<OrderDelivery> findByIsActive(boolean isActive);
	
	public List<OrderDelivery> findByProjectId(String projectId);
}
