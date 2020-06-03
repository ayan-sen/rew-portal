package com.rew.portal;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.rew.portal.repository.transaction.orderProcessing.OrderProcessingRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.rew.portal.repository")
@EnableTransactionManagement
public class RewPortalApplication implements CommandLineRunner {

	/*@Resource
	private TransactionRepository transactionRepository;*/
	@Resource
	private OrderProcessingRepository orderProcessingRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RewPortalApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<Map<String, Object>> list = orderProcessingRepository.getMaterialListByProject("REW/P/116/2019-20");
		
		System.out.println(list);
	}

}


