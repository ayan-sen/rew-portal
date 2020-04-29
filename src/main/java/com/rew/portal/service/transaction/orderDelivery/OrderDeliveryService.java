package com.rew.portal.service.transaction.orderDelivery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javassist.NotFoundException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;
import com.rew.portal.model.transaction.orderDelivery.OrderDeliveryDetails;
import com.rew.portal.repository.transaction.orderDelivery.OrderDeliveryRepository;

@Service
public class OrderDeliveryService {

	@Resource
	private OrderDeliveryRepository orderDeliveryRepository;
	
	public OrderDelivery save(OrderDelivery orderDelivery) {
		List<OrderDeliveryDetails> details = orderDelivery.getDetails();
		details.forEach(d -> d.setOrderDelivery(orderDelivery));
		
		return orderDeliveryRepository.save(orderDelivery);
	}
	
	public OrderDelivery findById(String clientId) {
		Optional<OrderDelivery> opt = orderDeliveryRepository.findById(clientId);
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<OrderDelivery> findAll() {
		return orderDeliveryRepository.findByIsActive(true);
	}
	
	public void delete(String orderDeliveryId) throws NotFoundException {
		OrderDelivery orderDelivery = this.findById(orderDeliveryId);
		if(Objects.nonNull(orderDelivery)) {
			orderDelivery.setIsActive(false);
			orderDeliveryRepository.save(orderDelivery);
		} else {
			throw new NotFoundException("Order not found with order id" + orderDeliveryId);
		}
	}
	
	public void deleteDetail(String orderDeliveryId, int detailId) throws NotFoundException {
		OrderDelivery orderDelivery = this.findById(orderDeliveryId);
		if(Objects.nonNull(orderDelivery)) {
			orderDelivery.removeDetail(detailId);
			orderDeliveryRepository.save(orderDelivery);
		} else {
			throw new NotFoundException("Delivery item not found with id" + orderDeliveryId);
		}
	}
}
