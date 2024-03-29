package com.rew.portal.service.transaction.inventory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.inventory.InventoryRecord;
import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.model.transaction.project.ProjectDetails;
import com.rew.portal.repository.transaction.inventoryRecord.InventoryRecordRepository;
import com.rew.portal.repository.transaction.project.ProjectRepository;

@Service
public class InventoryService {

	@Resource
	private InventoryRecordRepository inventoryRecordRepository;
	
	@Resource
	private ProjectRepository projectRepository;

	
	@SuppressWarnings("rawtypes")
	public Map<String, Collection> getStatus() {
		List<InventoryRecord> records = inventoryRecordRepository.findAll();
		Map<String, Map<String, Map<String, Double>>> grouping = records
				.stream()
				.filter(i -> StringUtils.equals(i.getItemType(), "R"))
				.collect(
						Collectors
								.groupingBy(
										inv -> StringUtils.join(inv.getMaterialName() , "(" , inv.getUnitName() , ")")
										 ,
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
	
	
	public Map<String, Collection> getProjectProgressStatus(String projectId) {
		
		Project project = projectRepository.findLatest(projectId);
		List<ProjectDetails> details = project.getDetails();
		Double maxQuantiry = details.stream().map(d -> d.getQuantity()).mapToDouble(v -> v).max().orElse(0.0);
		project.getExpectedDeliveryDate();
		
		List<InventoryRecord> records = inventoryRecordRepository.findByProjectIdAndItemTypeOrderByReferenceDateAsc(projectId, "P");
		
		Set<LocalDate> dates = records.stream()
										.map(r -> r.getReferenceDate())
										.collect(Collectors.toCollection(LinkedHashSet::new));
		
		List<String> materials = new ArrayList<>();
		List<Map<String, Object>> series = new ArrayList<>();
		Map<String, List<InventoryRecord>> matList = records.stream()
															.collect(Collectors.groupingBy(InventoryRecord::getMaterialName));
		Map<String, Collection> chartData = new HashMap<>();
		matList.entrySet().stream().forEachOrdered(r -> {
			List<Double> matQuantity = new ArrayList<>();
			List<InventoryRecord> matRecords = r.getValue();
			materials.add(r.getKey());
			dates.stream().forEachOrdered(d -> { 
				Double inQuantity = matRecords.stream()
											.filter(m -> m.getReferenceDate().compareTo(d) <= 0)
											.filter(m -> m.getInOutFlag().equals("IN"))
											.collect(Collectors.summingDouble(m -> m.getQuantity()));
				Double outQuantity = matRecords.stream()
											.filter(m -> m.getReferenceDate().compareTo(d) <= 0)
											.filter(m -> m.getInOutFlag().equals("OUT"))
											.collect(Collectors.summingDouble(m -> m.getQuantity()));
				matQuantity.add(inQuantity - outQuantity);
			});
			
			Map<String, Object> seriesMap = new HashMap<>();
			seriesMap.put("name", r.getKey());
			seriesMap.put("data", matQuantity);
			series.add(seriesMap);
		});

		Set<String> dateString = dates.stream()
									.map(r -> r.format(DateTimeFormatter.ofPattern("dd/MM")))
									.collect(Collectors.toCollection(LinkedHashSet::new));

		chartData.put("labels", dateString);
		chartData.put("series", series);
		chartData.put("max", Arrays.asList(maxQuantiry));
		chartData.put("materials", materials);
		return chartData;
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Map<String, Object>> getProductionStatusByProject(String projectId) {
		Project project = projectRepository.findLatest(projectId);
		List<InventoryRecord> records = inventoryRecordRepository.findByProjectIdOrderByReferenceDateAsc(projectId);
		Map<String, Map<String, Map<String, Map<String, Double>>>> grouping = records
				.stream()
				//.filter(i -> StringUtils.equals(i.getItemType(), "R"))
				.collect(
						Collectors.groupingBy(inv-> inv.getItemType().toString(), 
						Collectors
								.groupingBy(
										inv -> StringUtils.join(inv.getMaterialName() , "(" , inv.getUnitName() , ")")
										 ,
										Collectors
												.groupingBy(
														InventoryRecord::getSiteId,
														Collectors
																.groupingBy(
																		InventoryRecord::getInOutFlag,
																		Collectors
																				.summingDouble(InventoryRecord::getQuantity))))));
		
		Map<String, Object> projectItemMap = new HashMap<>();
		 project.getDetails().stream().forEach(det -> {
			projectItemMap.put(StringUtils.join(det.getRmName(), "(" , det.getUnitName() , ")"), det.getQuantity());
		});
		 
		
		
		Map<String, Object> rawMaterialByProject = enrich(grouping.get("R"), project);
		Map<String, Object> productsByProject = enrich(grouping.get("P"), project);
		
		Map<String, Map<String, Object>> projectCurrentStatus = new HashMap<>();
		projectCurrentStatus.put("rawMaterials", rawMaterialByProject);
		projectCurrentStatus.put("products", productsByProject);
		return projectCurrentStatus;
	}

	private Map<String, Object> enrich(Map<String, Map<String, Map<String, Double>>> materials, Project project) {
		Set<String> labels = materials.keySet();
		List<Double> dumdumQuantity = new ArrayList<>();
		List<Double> singurQuantity = new ArrayList<>();
		Map<String, Object> projectItemMap = new HashMap<>();
		 project.getDetails().stream().forEach(det -> {
			projectItemMap.put(StringUtils.join(det.getRmName(), "(" , det.getUnitName() , ")"), det.getQuantity());
		});
		
		
		Map<String, Object> processingDetails = new HashMap<>();

		labels.stream().forEachOrdered(
				lebel -> {
					Map<String, Map<String, Double>> quantityMap = materials
							.get(lebel);
					Map<String, Double> dumdumQuantityMap = quantityMap
							.getOrDefault("DUMDUM", Collections.emptyMap());
					Map<String, Double> singurQuantityMap = quantityMap
							.getOrDefault("SINGUR", Collections.emptyMap());
					double dumdum = dumdumQuantityMap
							.getOrDefault("IN", 0.0)
							- dumdumQuantityMap.getOrDefault("OUT", 0.0);
					dumdumQuantity.add(dumdum);
					double singur = singurQuantityMap
							.getOrDefault("IN", 0.0)
							- singurQuantityMap.getOrDefault("OUT", 0.0);
					singurQuantity.add(singur);
					
					//processingDetails.put(lebel, singur + dumdum);
					
					Object quantity = projectItemMap.get(lebel);
					
					processingDetails.put("item", lebel);
					processingDetails.put("processedQuantity", singur + dumdum);
					if(quantity != null) {
						processingDetails.put("quantity", quantity);
					}
					
					
					
					
				});

		
		return processingDetails;
	}
	
	
	
}
