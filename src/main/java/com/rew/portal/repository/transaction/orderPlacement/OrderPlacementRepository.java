package com.rew.portal.repository.transaction.orderPlacement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;

@Repository
public interface OrderPlacementRepository extends JpaRepository<OrderPlacement, String> {

	public List<OrderPlacement> findByIsActive(boolean isActive);
}
