package com.rew.portal.service.transaction.invoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.inventory.InventoryRecord;
import com.rew.portal.model.transaction.invoice.Invoice;
import com.rew.portal.model.transaction.invoice.InvoiceDetails;
import com.rew.portal.model.transaction.record.TransactionRecord;
import com.rew.portal.repository.transaction.inventoryRecord.InventoryRecordRepository;
import com.rew.portal.repository.transaction.invoice.InvoiceRepository;
import com.rew.portal.repository.transaction.orderProcessing.OrderProcessingRepository;
import com.rew.portal.repository.transaction.record.TransactionRecordRepository;
import com.rew.portal.service.transaction.inventory.InventoryService;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private TransactionRecordRepository transactionRecordRepository;
	
	@Autowired
	private OrderProcessingRepository orderProcessingRepository;
	
	@Autowired
	private InventoryRecordRepository inventoryRecordRepository;

	@Transactional
	public Invoice save(Invoice invoice) {
		List<InvoiceDetails> details = invoice.getDetails();
		details.forEach(d -> d.setInvoice(invoice));

		if (StringUtils.isNotEmpty(invoice.getInvoiceId())) {
			transactionRecordRepository.deleteByReferenceId(invoice.getInvoiceId());
		}

		Invoice inv = invoiceRepository.save(invoice);
		TransactionRecord record = TransactionRecord.createFromInvoice(inv);
		transactionRecordRepository.save(record);

		return inv;
	}
	
	public Invoice findById(String invoiceId) {
		Optional<Invoice> opt = invoiceRepository.findById(invoiceId);
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<Invoice> findAll() {
		return invoiceRepository.findAll();
	}
	
	@Transactional
	public void delete(String invoiceId) throws ObjectNotFoundException {
		Invoice invoice = this.findById(invoiceId);
		if(Objects.nonNull(invoice)) {
			transactionRecordRepository.deleteByReferenceId(invoiceId);
			invoiceRepository.deleteById(invoiceId);
			
		} else {
			throw new ObjectNotFoundException("Invoice not found : ", invoiceId);
		}
	}
	
	@Transactional
	public void deleteDetail(String invoiceId, int detailId) throws ObjectNotFoundException {
		Invoice invoice = this.findById(invoiceId);
		if(Objects.nonNull(invoice)) {
			Optional<InvoiceDetails> opodtls = invoice.getDetails().stream().filter(dtl -> dtl.getInvoiceDetailsId() == detailId).findFirst();
			if(opodtls.isPresent()) {
				invoice.removeDetail(detailId);
				invoiceRepository.save(invoice);
				transactionRecordRepository.deleteByReferenceId(invoice.getInvoiceId());
				TransactionRecord record = TransactionRecord.createFromInvoice(invoice);
				transactionRecordRepository.save(record);
			}
		} else {
			throw new ObjectNotFoundException("Invoice item not found with id ", invoiceId);
		}
	}
	
	public List<Map<String, Object>> getMaterialListByProject(String projectId, String siteId) {
		List<Map<String, Object>> materials = orderProcessingRepository.getProductsByProject(projectId);
		Map<String, Double> inventoryStatus = this.getProductStatus(projectId, siteId);
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
	
	private Map<String, Double> getProductStatus(String projectId, String siteId) {
		List<InventoryRecord> records = inventoryRecordRepository.findByProjectId(projectId);

		Map<String, Double> productMap = records
				.stream()
				.filter(record -> record.getReferenceType().equals("OS"))
				.filter(record -> record.getInOutFlag().equals("OUT"))
				.collect(Collectors.groupingBy(
							InventoryRecord::getMaterialCode, Collectors.summingDouble(InventoryRecord::getQuantity)));
		return productMap;

	}

}
