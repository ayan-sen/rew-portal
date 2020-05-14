package com.rew.portal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.rew.portal.model.transaction.orderDelivery.OrderDelivery;
import com.rew.portal.model.transaction.orderDelivery.OrderDeliveryDetails;
import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.model.transaction.project.ProjectDetails;
import com.rew.portal.service.transaction.orderDelivery.OrderDeliveryService;
import com.rew.portal.service.transaction.orderPlacement.OrderPlacementService;
import com.rew.portal.service.transaction.project.ProjectService;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.rew.portal.repository")
@EnableTransactionManagement
public class RewPortalApplication implements CommandLineRunner {

	@Resource
	private ProjectService projectService;
	@Resource
	private OrderPlacementService orderPlacementService;
	
	@Resource
	private OrderDeliveryService orderDeliveryService;
	
	public static void main(String[] args) {
		SpringApplication.run(RewPortalApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		OrderDeliveryDetails dtl1 = OrderDeliveryDetails.builder()
				.detailId(157)
				.quantity(100.0)
				.rmId("IRON")
				.unitId("KG")
				.rate(6.0)
				.quantity(100.0)
				.amount(600.0)
				.build();
		
		List<OrderDeliveryDetails> dtls = new ArrayList<>();
		dtls.add(dtl1);
				
		OrderDelivery project = OrderDelivery.builder()
								.deliveryId("REW/D/101/2019-20")
								.billNo("B123")
								.billDate(LocalDate.now())
								.orderId("REW/O/110/2019-20")
								.supplierId("P104")
								.supplierDetailsId(93)
								.consigneeId("P104")
								.consigneeDetailsId(93)
								.vehicleNo("WB-04-7895")
								.amount(600.0)
								.sgstAmount(27.0)
								.cgstAmount(27.0)
								.totalAmount(654.0)
								.siteId("DUMDUM")
								.details(dtls)
								.isActive(true)
								.build();
		
		orderDeliveryService.save(project);
		
		//orderPlacementService.generateInvoice("REW/O/110/2019-20");
		
		//OrderPlacement p = orderPlacementService.findById("REW/O/104/2019-20");
		//System.out.println(p);
	}

}


