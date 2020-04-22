package com.rew.portal.repository.admin.client;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.admin.client.Client;
@Transactional
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

}
