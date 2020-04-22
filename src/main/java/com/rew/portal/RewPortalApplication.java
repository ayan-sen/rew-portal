package com.rew.portal;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rew.portal.model.admin.client.Client;
import com.rew.portal.repository.admin.client.ClientRepository;

@SpringBootApplication
@EnableJpaRepositories
public class RewPortalApplication implements CommandLineRunner {

	@Resource
	private ClientRepository clientRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RewPortalApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		Client client = Client.builder().clientName("ABB Exports")
										.clientType("Client")
										.gstinNo("ABC123")
										.isActive(true)
										.build();
		Client c = clientRepository.save(client);
		System.out.println(c);
		
	}

}


