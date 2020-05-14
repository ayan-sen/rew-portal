package com.rew.portal.repository.transaction.inventoryRecord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.inventory.InventoryRecord;

@Repository
public interface InventoryRecordRepository extends JpaRepository<InventoryRecord, String> {

	public List<InventoryRecord> findByReferenceId(String referenceId);
	
	public void deleteByReferenceId(String referenceId);
	
	public List<InventoryRecord> findByReferenceIdAndReferenceDetailId(String referenceId, Integer referenceDetailId);
	
	public void deleteByReferenceIdAndReferenceDetailId(String referenceId, Integer referenceDetailId);
}
