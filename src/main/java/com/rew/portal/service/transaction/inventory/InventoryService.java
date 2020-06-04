package com.rew.portal.service.transaction.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.inventory.InventoryRecord;
import com.rew.portal.repository.transaction.inventoryRecord.InventoryRecordRepository;

@Service
public class InventoryService {

	@Resource
	private InventoryRecordRepository inventoryRecordRepository;

	@SuppressWarnings("rawtypes")
	public Map<String, Collection> getStatus() {
		List<InventoryRecord> records = inventoryRecordRepository.findAll();
		Map<String, Map<String, Map<String, Double>>> grouping = records
				.stream()
				.filter(i -> StringUtils.equals(i.getItemType(), "R"))
				.collect(
						Collectors
								.groupingBy(
										InventoryRecord::getMaterialCode,
										Collectors
												.groupingBy(
														InventoryRecord::getSiteId,
														Collectors
																.groupingBy(
																		InventoryRecord::getInOutFlag,
																		Collectors
																				.summingDouble(InventoryRecord::getQuantity)))));

		Set<String> labels = grouping.keySet();
		List<Double> dumdumQuantity = new ArrayList<>();
		List<Double> singurQuantity = new ArrayList<>();

		labels.stream().forEachOrdered(
				lebel -> {
					Map<String, Map<String, Double>> quantityMap = grouping
							.get(lebel);
					Map<String, Double> dumdumQuantityMap = quantityMap
							.getOrDefault("DUMDUM", Collections.emptyMap());
					Map<String, Double> singurQuantityMap = quantityMap
							.getOrDefault("SINGUR", Collections.emptyMap());
					dumdumQuantity.add(dumdumQuantityMap
							.getOrDefault("IN", 0.0)
							- dumdumQuantityMap.getOrDefault("OUT", 0.0));
					singurQuantity.add(singurQuantityMap
							.getOrDefault("IN", 0.0)
							- singurQuantityMap.getOrDefault("OUT", 0.0));
				});

		Map<String, Collection> chartData = new HashMap<>();
		chartData.put("labels", labels);
		chartData.put("dumdum", dumdumQuantity);
		chartData.put("singur", singurQuantity);
		return chartData;
	}

	public Map<String, Double> getMaterialStatusByProjectIdAndSiteId(String projectId, String siteId) {
		List<InventoryRecord> records = inventoryRecordRepository.findByProjectId(projectId);

		Map<String, Double> productMap = records
				.stream()
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
