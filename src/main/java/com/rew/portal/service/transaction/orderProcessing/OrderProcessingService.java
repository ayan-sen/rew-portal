package com.rew.portal.service.transaction.orderProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javassist.NotFoundException;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.inventory.InventoryRecord;
import com.rew.portal.model.transaction.orderProcessing.OrderProcessing;
import com.rew.portal.model.transaction.orderProcessing.OrderProcessingDetails;
import com.rew.portal.model.transaction.project.ProjectDetails;
import com.rew.portal.repository.transaction.inventoryRecord.InventoryRecordRepository;
import com.rew.portal.repository.transaction.orderProcessing.OrderProcessingRepository;
import com.rew.portal.repository.transaction.project.ProjectDetailsRepository;
import com.rew.portal.service.transaction.inventory.InventoryService;
import com.rew.portal.service.transaction.project.ProjectService;

@Service
public class OrderProcessingService {

	private static final String PRODUCT = "P";

	@Resource
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
	public OrderProcessing save(OrderProcessing orderProcessing) {
		List<OrderProcessingDetails> details = orderProcessing.getDetails();
		details.forEach(d -> d.setOrderProcessing(orderProcessing));
		
		if(orderProcessing.getProcessId() != null) {
			inventoryRecordRepository.deleteByReferenceId(orderProcessing.getProcessId().toString());
		} 
		OrderProcessing processing = orderProcessingRepository.save(orderProcessing);
		List<InventoryRecord> records =InventoryRecord.createFromOrderProcessing(processing);	
		records.forEach(record -> inventoryRecordRepository.save(record) );
		return processing;
	}
	
	@Transactional	
	public OrderProcessing findById(Integer processingId) {
		Optional<OrderProcessing> opt = orderProcessingRepository.findById(processingId);
		if(opt.isPresent()) {
			OrderProcessing op = opt.get();
			String projectId = op.getProjectId();
			Map<String, Double> inventoryStatus = inventoryService.getMaterialStatusByProjectIdAndSiteId(projectId, op.getSiteId());
			List<OrderProcessingDetails> details = op.getDetails();
			details.forEach(detail -> {
				String materialCode = detail.getMaterialId();
				Double availableQuantity = inventoryStatus.getOrDefault(materialCode, 0.0);
				detail.setAvailableQuantity(availableQuantity);
				if(StringUtils.equals(detail.getProcessType(), PRODUCT)) {
					ProjectDetails projectDetails = projectDetailsRepository.findOneByProject_ProjectIdAndProject_AmendmentNoAndRmId(projectId, 0, materialCode);
					if(projectDetails != null) {
						Double remainingQuantity = projectDetails.getQuantity() - availableQuantity;
						detail.setRemainingQuantity(remainingQuantity);
					}
				}
				
			});
			return op;
		}
		return null;
	}
	
	@Transactional
	public List<OrderProcessing> findAll() {
		List<OrderProcessing> processings = orderProcessingRepository.findByIsActive(true);
		return processings;
	}
	
	@Transactional
	public void delete(Integer processingId) throws NotFoundException {
		OrderProcessing orderProcessing = this.findById(processingId);
		if(Objects.nonNull(orderProcessing)) {
			orderProcessing.setIsActive(false);
			orderProcessingRepository.save(orderProcessing);
			inventoryRecordRepository.deleteByReferenceId(processingId.toString());
		} else {
			throw new NotFoundException("Order not found with order id" + processingId);
		}
	}
	
	@Transactional
	public void deleteDetail(Integer processingId, int detailId) throws NotFoundException { 
		OrderProcessing orderProcessing = this.findById(processingId);
		if(Objects.nonNull(orderProcessing)) {
			orderProcessing.removeDetail(detailId);
			orderProcessingRepository.save(orderProcessing);
			inventoryRecordRepository.deleteByReferenceIdAndReferenceDetailId(processingId.toString(), detailId);
		} else {
			throw new NotFoundException("Details not found with id " + detailId);
		}
	}
	
	public List<Map<String, Object>> getMaterialListByProject(String projectId, String siteId) {
		List<Map<String, Object>> materials = orderProcessingRepository.getMaterialListByProject(projectId);
		Map<String, Double> inventoryStatus = inventoryService.getMaterialStatusByProjectIdAndSiteId(projectId, siteId);
		List<Map<String, Object>> enrichedMaterialsList = new ArrayList<>();
		materials.forEach(material -> {
			Map<String, Object> materialNew = new HashMap<>();
			materialNew.putAll(material);
			
			String matType = (String) material.getOrDefault("type", "");
			String matCode = (String) material.getOrDefault("code", "");
			Double availableQuantity = inventoryStatus.getOrDefault(material.getOrDefault("code", ""), 0.0);
			materialNew.put("availableQuantity", availableQuantity);
			if(StringUtils.equals(matType, PRODUCT)) {
				ProjectDetails projectDetails = projectDetailsRepository.findOneByProject_ProjectIdAndProject_AmendmentNoAndRmId(projectId, 0, matCode);
				if(projectDetails != null) {
					Double remainingQuantity = projectDetails.getQuantity() - availableQuantity;
					materialNew.put("remainingQuantity", remainingQuantity);
				}
			}
			enrichedMaterialsList.add(materialNew);
		});
		return enrichedMaterialsList;
	}
}
