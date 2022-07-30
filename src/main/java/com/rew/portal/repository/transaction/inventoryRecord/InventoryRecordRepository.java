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
	
	public List<InventoryRecord> findByProjectId(String projectId);
	
	public List<InventoryRecord> findByProjectIdAndItemType(String projectId, String itemType);
	
	public List<InventoryRecord> findByProjectIdAndItemTypeAndSiteId(String projectId, String itemType, String siteId);
	
	public List<InventoryRecord> findByProjectIdAndSiteId(String projectId, String siteId);
	
	public List<InventoryRecord> findByProjectIdAndItemTypeOrderByReferenceDateAsc(String projectId, String itemType);
	
	public List<InventoryRecord> findByProjectIdOrderByReferenceDateAsc(String projectId);
}
