package com.rew.portal.service.transaction.orderProcessing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
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
		List<OrderProcessing> processings = orderProcessingRepository.findByIsActiveOrderByProcessDateDescSiteIdAscProjectIdAsc(true);
		return processings;
	}
	
	@Transactional
	public void delete(Integer processingId) throws ObjectNotFoundException {
		OrderProcessing orderProcessing = this.findById(processingId);
		if(Objects.nonNull(orderProcessing)) {
			orderProcessing.setIsActive(false);
			orderProcessingRepository.save(orderProcessing);
			inventoryRecordRepository.deleteByReferenceId(processingId.toString());
		} else {
			throw new ObjectNotFoundException("Order not found with order id", processingId.toString());
		}
	}
	
	@Transactional
	public void deleteDetail(Integer processingId, Integer detailId) throws ObjectNotFoundException { 
		OrderProcessing orderProcessing = this.findById(processingId);
		if(Objects.nonNull(orderProcessing)) {
			orderProcessing.removeDetail(detailId);
			orderProcessingRepository.save(orderProcessing);
			inventoryRecordRepository.deleteByReferenceIdAndReferenceDetailId(processingId.toString(), detailId);
		} else {
			throw new ObjectNotFoundException("Details not found with id ", detailId.toString());
		}
	}
	
	public List<Map<String, Object>> getMaterialListByProject(String projectId, String siteId) {
		List<Map<String, Object>> materials = orderProcessingRepository.getMaterialListByProject(projectId);
		Map<String, Double> inventoryStatus = this.getMaterialStatusByProjectIdAndSiteId(projectId, siteId);
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
	
	public Map<Object, Map<Object, List<Object>>> findByProcessDate(LocalDate processDate, LocalDate toDate) {
		List<OrderProcessing> works = new ArrayList<>();
		if(Objects.isNull(toDate)) {
			works = orderProcessingRepository.findByProcessDateOrderByProjectIdAsc(processDate);
		} else {
			works = orderProcessingRepository.findByProcessDateBetweenOrderByProjectIdAsc(processDate, toDate);
		}
		
		return works.stream().collect(Collectors.groupingBy(w -> w.getProjectId(), Collectors.groupingBy(w -> w.getProcessDate(), 
				Collectors.mapping(w -> w.getDetails(), 
						Collectors.flatMapping(w -> w.stream(), Collectors.toList())))));
	}
	
	public Map<String, Double> getMaterialStatusByProjectIdAndSiteId(String projectId, String siteId) {
		List<InventoryRecord> records = inventoryRecordRepository.findByProjectId(projectId).stream().filter(r -> !r.getItemType().equalsIgnoreCase("R")).collect(Collectors.toList());
		
		List<InventoryRecord> rawMaterials = inventoryRecordRepository.findByItemType("R");
		
		records.addAll(rawMaterials);

		Map<String, Double> productMap = records
				.stream()
				.filter(m -> !StringUtils.equalsIgnoreCase(m.getReferenceType(), "OS"))
				.collect(Collectors.groupingBy(
							InventoryRecord::getMaterialCode, 
							Collectors.groupingBy(InventoryRecord::getInOutFlag ,
								Collectors.summingDouble(InventoryRecord::getQuantity))))
				.entrySet().stream()
							.collect(Collectors.toMap(entry -> entry.getKey(), 
									entry -> entry.getValue().getOrDefault("IN", 0.0) - entry.getValue().getOrDefault("OUT", 0.0)));
		return productMap;

	}
}
