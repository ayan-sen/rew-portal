package com.rew.portal.service.admin.client;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javassist.NotFoundException;

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
	
	public Client findById(String clientId) {
		Optional<Client> opt = clientRepository.findById(clientId);
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<Client> findAll() {
		return clientRepository.findByIsActive(true);
	}
	
	public void deleteClient(String clientId) throws NotFoundException {
		Client client = this.findById(clientId);
		if(Objects.nonNull(client)) {
			client.setActive(false);
			clientRepository.save(client);
		} else {
			throw new NotFoundException("Client not found with client id" + clientId);
		}
	}
	
	public void deleteClientDetail(String clientId, int detailId) throws NotFoundException {
		Client client = this.findById(clientId);
		if(Objects.nonNull(client)) {
			client.removeDetail(detailId);
			clientRepository.save(client);
		} else {
			throw new NotFoundException("Client not found with client id" + clientId);
		}
	}
}
