package com.rew.portal.service.transaction.orderDelivery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.inventory.InventoryRecord;
import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;
import com.rew.portal.model.transaction.orderDelivery.OrderDeliveryDetails;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacementDetails;
import com.rew.portal.model.transaction.record.TransactionRecord;
import com.rew.portal.repository.transaction.inventoryRecord.InventoryRecordRepository;
import com.rew.portal.repository.transaction.orderDelivery.OrderDeliveryRepository;
import com.rew.portal.repository.transaction.record.TransactionRecordRepository;
import com.rew.portal.service.transaction.orderPlacement.OrderPlacementService;

@Service
public class OrderDeliveryService {

	@Resource
	private OrderDeliveryRepository orderDeliveryRepository;
	
	@Resource
	private InventoryRecordRepository inventoryRecordRepository;
	
	@Resource
	private TransactionRecordRepository transactionRecordRepository;
	
	@Resource
	private OrderPlacementService orderPlacementService;
	
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
		
		OrderPlacement op = orderPlacementService.findById(orderDelivery.getOrderId());
		List<OrderPlacementDetails> opDetails = op.getDetails();
		
		// update order placement details quantity
		details.forEach(dtl -> {
			OrderPlacementDetails opDtl = opDetails.stream().filter(d -> StringUtils.equals(d.getRmId(),dtl.getRmId())).findFirst().get();
			Double alreadyOrderedQuantity  = ((opDtl.getAlreadyOrderedQuantity() != null ? opDtl.getAlreadyOrderedQuantity() : 0.0) + dtl.getQuantity()) 
													- (dtl.getOldQuantity() != null ? dtl.getOldQuantity() : 0.0) ;
			opDtl.setAlreadyOrderedQuantity(alreadyOrderedQuantity);
		});
		orderPlacementService.save(op);
		
		return delivery;
	}
	
	public OrderDelivery findById(String clientId) {
		OrderDelivery opt = orderDeliveryRepository.findById(clientId).orElse(null);
		if(opt != null) {
			List<OrderDeliveryDetails> details = opt.getDetails();
			List<OrderPlacementDetails> opDetails = opt.getOrderPlacement().getDetails();
			details.forEach(dtl ->  {
				OrderPlacementDetails opDtl = opDetails.stream().filter(d -> StringUtils.equals(d.getRmId(), dtl.getRmId())).findFirst().orElse(null);
				dtl.setRemainingQuantity((opDtl.getQuantity() - opDtl.getAlreadyOrderedQuantity()) + dtl.getQuantity());
				dtl.setOldQuantity(dtl.getQuantity());
			});
		}
		return opt;
	}
	
	public List<OrderDelivery> findAll() {
		return orderDeliveryRepository.findByIsActive(true);
	}
	
	@Transactional
	public void delete(String orderDeliveryId) throws ObjectNotFoundException {
		OrderDelivery orderDelivery = this.findById(orderDeliveryId);
		if(Objects.nonNull(orderDelivery)) {
			orderDelivery.setIsActive(false);
			orderDeliveryRepository.save(orderDelivery);
			inventoryRecordRepository.deleteByReferenceId(orderDelivery.getDeliveryId());
			transactionRecordRepository.deleteByReferenceId(orderDelivery.getDeliveryId());
			TransactionRecord record = TransactionRecord.createFromOrderDelivery(orderDelivery);
			transactionRecordRepository.save(record);
			
			List<OrderDeliveryDetails> details = orderDelivery.getDetails();
			OrderPlacement op = orderPlacementService.findById(orderDelivery.getOrderId());
			List<OrderPlacementDetails> opDetails = op.getDetails();
			// update order placement details quantity
			details.forEach(dtl -> {
				System.out.println(dtl.getRmId());
				OrderPlacementDetails opDtl = opDetails.stream().filter(d -> StringUtils.equals(d.getRmId(),dtl.getRmId())).findFirst().get();
				System.out.println("DONE");
				Double alreadyOrderedQuantity  = (opDtl.getAlreadyOrderedQuantity() != null ? opDtl.getAlreadyOrderedQuantity() : 0.0) - dtl.getQuantity(); 
				opDtl.setAlreadyOrderedQuantity(alreadyOrderedQuantity);
			});
			orderPlacementService.save(op);
			
		} else {
			throw new ObjectNotFoundException("Order not found with order id ", orderDeliveryId);
		}
	}
	
	@Transactional
	public void deleteDetail(String orderDeliveryId, int detailId) throws ObjectNotFoundException {
		OrderDelivery orderDelivery = this.findById(orderDeliveryId);
		if(Objects.nonNull(orderDelivery)) {
			Optional<OrderDeliveryDetails> opodtls = orderDelivery.getDetails().stream().filter(dtl -> dtl.getDetailId() == detailId).findFirst();
			if(opodtls.isPresent()) {
				OrderDeliveryDetails odtls = opodtls.get();
				
				orderDelivery.removeDetail(detailId);
				orderDeliveryRepository.save(orderDelivery);
				inventoryRecordRepository.deleteByReferenceIdAndReferenceDetailId(orderDeliveryId, detailId);
				transactionRecordRepository.deleteByReferenceId(orderDelivery.getDeliveryId());
				TransactionRecord record = TransactionRecord.createFromOrderDelivery(orderDelivery);
				transactionRecordRepository.save(record);
				
				
				OrderPlacement oph = orderDelivery.getOrderPlacement();
				List<OrderPlacementDetails> opDetails = oph.getDetails();
				
				
				//update order placement table order quantity
				Optional<OrderPlacementDetails> opDtl = opDetails.stream().filter(d -> StringUtils.equals(d.getRmId(),odtls.getRmId())).findFirst();
				if(opDtl.isPresent()) {
					OrderPlacementDetails op = opDtl.get();
					Double alreadyOrderedQuantity  =  op.getAlreadyOrderedQuantity() - odtls.getQuantity();
					op.setAlreadyOrderedQuantity(alreadyOrderedQuantity);
				}
				orderPlacementService.save(oph);
			}
		} else {
			throw new ObjectNotFoundException("Delivery item not found with id ", orderDeliveryId);
		}
	}
}
