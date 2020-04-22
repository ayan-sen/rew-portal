package com.rew.portal.repository.admin.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.admin.client.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
