package com.rew.portal.service.transaction.orderDespatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.inventory.InventoryRecord;
import com.rew.portal.model.transaction.orderDespatch.OrderDespatch;
import com.rew.portal.model.transaction.orderDespatch.OrderDespatchDetails;
import com.rew.portal.model.transaction.project.ProjectDetails;
import com.rew.portal.repository.transaction.inventoryRecord.InventoryRecordRepository;
import com.rew.portal.repository.transaction.orderDespatch.OrderDespatchRepository;
import com.rew.portal.repository.transaction.orderProcessing.OrderProcessingRepository;
import com.rew.portal.repository.transaction.project.ProjectDetailsRepository;
import com.rew.portal.service.transaction.inventory.InventoryService;
import com.rew.portal.service.transaction.project.ProjectService;

@Service
public class OrderDespatchService {
	
	@Autowired
	private OrderDespatchRepository orderDespatchRepository;
	

	@Autowired
	private OrderProcessingRepository orderProcessingRepository;

	@Resource
	private InventoryRecordRepository inventoryRecordRepository;
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private InventoryService inventoryService;
	
	@Resource
	private ProjectDetailsRepository projectDetailsRepository;
	
	@Transactional
	public OrderDespatch save(OrderDespatch orderDespatch) {
		List<OrderDespatchDetails> details = orderDespatch.getDetails();
		details.forEach(d -> d.setOrderDespatch(orderDespatch));
		
		if(orderDespatch.getDespatchId() != null) {
			inventoryRecordRepository.deleteByReferenceId(orderDespatch.getDespatchId().toString());
		} 
		OrderDespatch despatch = orderDespatchRepository.save(orderDespatch);
		List<InventoryRecord> records =InventoryRecord.createFromOrderDespatch(despatch);	
		records.forEach(record -> inventoryRecordRepository.save(record) );
		return despatch;
	}
	
	@Transactional	
	public OrderDespatch findById(String despatchId) {
		Optional<OrderDespatch> opt = orderDespatchRepository.findById(despatchId);
		if(opt.isPresent()) {
			OrderDespatch op = opt.get();
			String projectId = op.getProjectId();
			Map<String, Double> inventoryStatus = inventoryService.getMaterialStatusByProjectIdAndSiteId(projectId, op.getSiteId());
			List<OrderDespatchDetails> details = op.getDetails();
			details.forEach(detail -> {
				String materialCode = detail.getMaterialId();
				Double availableQuantity = inventoryStatus.getOrDefault(materialCode, 0.0);
				detail.setAvailableQuantity(availableQuantity);
				
				
			});
			return op;
		}
		return null;
	}
	
	@Transactional
	public List<OrderDespatch> findAll() {
		List<OrderDespatch> despatches = orderDespatchRepository.findAll();
		return despatches;
	}
	
	@Transactional
	public void deleteDetail(String despatchId, Integer detailId) throws ObjectNotFoundException { 
		OrderDespatch orderDespatch = this.findById(despatchId);
		if(Objects.nonNull(orderDespatch)) {
			orderDespatch.removeDetail(detailId);
			orderDespatchRepository.save(orderDespatch);
			inventoryRecordRepository.deleteByReferenceIdAndReferenceDetailId(despatchId.toString(), detailId);
		} else {
			throw new ObjectNotFoundException("Details not found with id ", detailId.toString());
		}
	}
	
	public List<Map<String, Object>> getMaterialListByProject(String projectId, String siteId) {
		List<Map<String, Object>> materials = orderProcessingRepository.getMaterialListByProject(projectId);
		Map<String, Double> inventoryStatus = inventoryService.getMaterialStatusByProjectIdAndSiteId(projectId, siteId);
		List<Map<String, Object>> enrichedMaterialsList = new ArrayList<>();
		materials.forEach(material -> {
			Map<String, Object> materialNew = new HashMap<>();
			String matType = (String) material.getOrDefault("type", "");
			String matCode = (String) material.getOrDefault("code", "");
			materialNew.put("unitId", material.getOrDefault("unitid", ""));
			materialNew.put("unitName", material.getOrDefault("unitname", ""));
			materialNew.put("name", material.getOrDefault("name", ""));
			materialNew.put("unitName", material.getOrDefault("unitname", ""));
			materialNew.put("code", matCode);
			materialNew.put("type", matType);
			Double availableQuantity = inventoryStatus.getOrDefault(material.getOrDefault("code", ""), 0.0);
			materialNew.put("availableQuantity", availableQuantity);
			
			enrichedMaterialsList.add(materialNew);
		});
		return enrichedMaterialsList;
	}
	
	public List<OrderDespatch> getDespatchesByProjectId(String projectId) {
		return orderDespatchRepository.findByProjectId(projectId);
	}
	
}
