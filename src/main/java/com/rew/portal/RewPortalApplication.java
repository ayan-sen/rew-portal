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
import com.rew.portal.model.transaction.project.Project;
import com.rew.portal.model.transaction.project.ProjectDetails;
import com.rew.portal.service.transaction.orderPlacement.OrderPlacementService;
import com.rew.portal.service.transaction.project.ProjectService;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.rew.portal.repository")
public class RewPortalApplication implements CommandLineRunner {

	@Resource
	private ProjectService projectService;
	@Resource
	private OrderPlacementService orderPlacementService;
	
	public static void main(String[] args) {
		SpringApplication.run(RewPortalApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		ProjectDetails dtl1 = ProjectDetails.builder()
				.quantity(100.0)
				.rmId("IRON")
				.unitId("KG")
				.rate(5.0)
				.quantity(100.0)
				.amount(500.0)
				.build();
		
		List<ProjectDetails> dtls = new ArrayList<>();
		dtls.add(dtl1);
				
		Project project = Project.builder()
								.projectId("REW/P/110/2019-20")
								.amendmentNo(1)
								.amendmentDate(LocalDate.now())
								.description("BHEL Project")
								.customerId("P101")
								.consigneeId("P102")
								.purchaseOrderNo("100")
								.purchaseOrderDate(LocalDate.now())
								.workOrderReference("WR01")
								.expectedDeliveryDate(LocalDate.now())
								.status("DRAFT")
								.amount(500.0)
								.sgstAmount(22.5)
								.cgstAmount(22.5)
								.totalAmount(545.0)
								.details(dtls)
								.build();
								
		
		
		//OrderPlacement p = orderPlacementService.findById("REW/O/104/2019-20");
		//System.out.println(p);
	}

}


