package com.rew.portal.service.transaction.orderDelivery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javassist.NotFoundException;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.inventory.InventoryRecord;
import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;
import com.rew.portal.model.transaction.orderDelivery.OrderDeliveryDetails;
import com.rew.portal.model.transaction.record.TransactionRecord;
import com.rew.portal.repository.transaction.inventoryRecord.InventoryRecordRepository;
import com.rew.portal.repository.transaction.orderDelivery.OrderDeliveryRepository;
import com.rew.portal.repository.transaction.record.TransactionRecordRepository;

@Service
public class OrderDeliveryService {

	@Resource
	private OrderDeliveryRepository orderDeliveryRepository;
	
	@Resource
	private InventoryRecordRepository inventoryRecordRepository;
	
	@Resource
	private TransactionRecordRepository transactionRecordRepository;
	
	@Transactional
	public OrderDelivery save(OrderDelivery orderDelivery) {
		List<OrderDeliveryDetails> details = orderDelivery.getDetails();
		details.forEach(d -> d.setOrderDelivery(orderDelivery));
		
		if(StringUtils.isNotEmpty(orderDelivery.getDeliveryId())) {
			inventoryRecordRepository.deleteByReferenceId(orderDelivery.getDeliveryId());
			transactionRecordRepository.deleteByReferenceId(orderDelivery.getDeliveryId());
		} 

		OrderDelivery delivery = orderDeliveryRepository.save(orderDelivery);
		List<InventoryRecord> records =InventoryRecord.createFromOrderDelivery(delivery);	
		records.forEach(record -> inventoryRecordRepository.save(record) );
		
		TransactionRecord record = TransactionRecord.createFromOrderDelivery(delivery);
		transactionRecordRepository.save(record);
		
		return delivery;
	}
	
	public OrderDelivery findById(String clientId) {
		Optional<OrderDelivery> opt = orderDeliveryRepository.findById(clientId);
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<OrderDelivery> findAll() {
		return orderDeliveryRepository.findByIsActive(true);
	}
	
	@Transactional
	public void delete(String orderDeliveryId) throws NotFoundException {
		OrderDelivery orderDelivery = this.findById(orderDeliveryId);
		if(Objects.nonNull(orderDelivery)) {
			orderDelivery.setIsActive(false);
			orderDeliveryRepository.save(orderDelivery);
			inventoryRecordRepository.deleteByReferenceId(orderDelivery.getDeliveryId());
			transactionRecordRepository.deleteByReferenceId(orderDelivery.getDeliveryId());
			TransactionRecord record = TransactionRecord.createFromOrderDelivery(orderDelivery);
			transactionRecordRepository.save(record);
		} else {
			throw new NotFoundException("Order not found with order id" + orderDeliveryId);
		}
	}
	
	@Transactional
	public void deleteDetail(String orderDeliveryId, int detailId) throws NotFoundException {
		OrderDelivery orderDelivery = this.findById(orderDeliveryId);
		if(Objects.nonNull(orderDelivery)) {
			orderDelivery.removeDetail(detailId);
			orderDeliveryRepository.save(orderDelivery);
			inventoryRecordRepository.deleteByReferenceIdAndReferenceDetailId(orderDeliveryId, detailId);
			transactionRecordRepository.deleteByReferenceId(orderDelivery.getDeliveryId());
			TransactionRecord record = TransactionRecord.createFromOrderDelivery(orderDelivery);
			transactionRecordRepository.save(record);
		} else {
			throw new NotFoundException("Delivery item not found with id" + orderDeliveryId);
		}
	}
}
