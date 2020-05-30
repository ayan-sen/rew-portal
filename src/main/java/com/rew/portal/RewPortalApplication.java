package com.rew.portal;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.rew.portal.model.transaction.project.ProjectDetails;
import com.rew.portal.repository.transaction.project.ProjectDetailsRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.rew.portal.repository")
@EnableTransactionManagement
public class RewPortalApplication implements CommandLineRunner {

	@Resource
	private ProjectDetailsRepository projectDetailsRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RewPortalApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		ProjectDetails dtl = projectDetailsRepository.findOneByProject_ProjectIdAndProject_AmendmentNoAndRmId("REW/P/116/2019-20", 0, "CABLETRAY");
		System.out.println(">>>>>>>>>>>>>>>>" + dtl);
	}

}


