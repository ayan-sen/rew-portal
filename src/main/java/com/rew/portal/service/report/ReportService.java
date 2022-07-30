package com.rew.portal.service.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.inventory.InventoryRecord;
import com.rew.portal.repository.transaction.inventoryRecord.InventoryRecordRepository;
import com.rew.portal.service.transaction.inventory.InventoryService;

@Service
public class ReportService {
	
	@Autowired
	private InventoryService inventoryService;
	
	@Resource
	private InventoryRecordRepository inventoryRecordRepository;
	
	public Map<String, Object> getProjectWiseProductionReport(String projectId) {
		
		Map<String, Map<String, Double>> inventoryStatus = inventoryService.getProductionStatusByProject(projectId);
		
		List<Map<String, Object>> rawMaterialRecords = inventoryRecordRepository.findByProjectIdAndItemTypeOrderByReferenceDateAsc(projectId, "R").stream().map(inv ->{
			Map<String, Object> view = new HashMap<>();
			view.put("date", inv.getReferenceDate());
			view.put("material", inv.getMaterialName());
			view.put("quantity", inv.getQuantity());
			view.put("unit", inv.getUnitName());
			return view;
		}).collect(Collectors.toList());
		List<Map<String, Object>> productRecords = inventoryRecordRepository.findByProjectIdAndItemTypeOrderByReferenceDateAsc(projectId, "P").stream().map(inv ->{
			Map<String, Object> view = new HashMap<>();
			view.put("date", inv.getReferenceDate());
			view.put("material", inv.getMaterialName());
			view.put("quantity", inv.getQuantity());
			view.put("unit", inv.getUnitName());
			return view;
		}).collect(Collectors.toList());
		
		Map<String, Object> projectLog = new HashMap<>();
		projectLog.put("summary", inventoryStatus);
		projectLog.put("rawMaterialRecords", rawMaterialRecords);
		projectLog.put("productRecords", productRecords);
		
		return projectLog;
		
	}

}
