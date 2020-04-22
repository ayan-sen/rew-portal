package com.rew.portal.controller.admin.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.admin.client.Client;
import com.rew.portal.service.admin.service.ClientService;

@Slf4j
@RestController
public class ClientController {

	@Resource
	private ClientService clientService;

	@PostMapping("/admin/client")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody Client client) {
		Map<String, String> response = new HashMap<>();
		try {
			clientService.save(client);
			response.put("status", "success");
			response.put("message", "Client/Cistomer saved Successfully");
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in client creation", e);
			response.put("status", "failure");
			response.put("message",
					"Error occurred during client/customer creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/admin/clients")
	public ResponseEntity<List<Client>> findAll() {
		return new ResponseEntity<List<Client>>(
				clientService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/admin/clients/{code}")
	public ResponseEntity<Client> findById(@PathVariable String clientId) {
		Client client = clientService.findById(clientId);
		return Objects.isNull(client) ? 
				new ResponseEntity<Client>(HttpStatus.NOT_FOUND) 
				: new ResponseEntity<Client>(client, HttpStatus.OK);
	}
}
