package com.rew.portal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rew.portal.model.transaction.orderPlacement.OrderPlacement;
import com.rew.portal.model.transaction.orderPlacement.OrderPlacementDetails;
import com.rew.portal.service.transaction.orderPlacement.OrderPlacementService;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.rew.portal.repository")
public class RewPortalApplication implements CommandLineRunner {

	@Resource
	private OrderPlacementService orderPlacementService;
	
	public static void main(String[] args) {
		SpringApplication.run(RewPortalApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		
		OrderPlacementDetails dtl1 = OrderPlacementDetails.builder()
										.quantity(100.0)
										.rmId("IRON")
										.unitId("KG")
										.build();
		
		List<OrderPlacementDetails> dtls = new ArrayList<>();
		dtls.add(dtl1);
		
		OrderPlacement op = OrderPlacement.builder()
				.expectedDeliveryDate(LocalDate.now())
				.siteId("DUMDUM")
				.status("DRAFT")
				.isActive(true)
				.details(dtls)
				.build();
		
		
		orderPlacementService.save(op);
	}

}


