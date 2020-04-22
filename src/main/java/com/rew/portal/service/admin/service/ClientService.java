package com.rew.portal.service.admin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rew.portal.model.admin.client.Client;
import com.rew.portal.model.admin.client.ClientDetails;
import com.rew.portal.repository.admin.client.ClientRepository;

@Service
public class ClientService {

	@Resource
	private ClientRepository clientRepository;
	
	public Client save(Client client) {
		
		List<ClientDetails> details = client.getDetails();
		details.forEach(d -> d.setClient(client));
		
		
		return clientRepository.save(client);
	}
}
