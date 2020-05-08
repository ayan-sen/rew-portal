package com.rew.portal.service.transaction.orderPlacement;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javassist.NotFoundException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacementDetails;
import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.repository.transaction.orderPlacement.OrderPlacementRepository;
import com.rew.portal.repository.transaction.project.ProjectRepository;

@Service
public class OrderPlacementService {

	@Resource
	private OrderPlacementRepository orderPlacementRepository;
	
	@Resource
	private ProjectRepository projectRepository;
	
	public OrderPlacement save(OrderPlacement orderPlacement) {
		List<OrderPlacementDetails> details = orderPlacement.getDetails();
		details.forEach(d -> d.setOrderPlacement(orderPlacement));
		return orderPlacementRepository.save(orderPlacement);
	}
	
	public OrderPlacement findById(String clientId) {
		Optional<OrderPlacement> opt = orderPlacementRepository.findById(clientId);
		OrderPlacement op = opt.isPresent() ? opt.get() : null;
		if(Objects.nonNull(op)) {
			Project project = projectRepository.findLatest(op.getProjectId());
			op.setProject(project);
		}
		return op;
	}
	
	public List<OrderPlacement> findAll() {
		return orderPlacementRepository.findByIsActive(true);
	}
	
	public void delete(String orderPlacementId) throws NotFoundException {
		OrderPlacement orderPlacement = this.findById(orderPlacementId);
		if(Objects.nonNull(orderPlacement)) {
			orderPlacement.setIsActive(false);
			orderPlacementRepository.save(orderPlacement);
		} else {
			throw new NotFoundException("Order not found with order id" + orderPlacementId);
		}
	}
	
	public void deleteDetail(String orderPlacementId, int detailId) throws NotFoundException {
		OrderPlacement orderPlacement = this.findById(orderPlacementId);
		if(Objects.nonNull(orderPlacement)) {
			orderPlacement.removeDetail(detailId);
			orderPlacementRepository.save(orderPlacement);
		} else {
			throw new NotFoundException("Order detail not found with id " + orderPlacementId);
		}
	}
}
