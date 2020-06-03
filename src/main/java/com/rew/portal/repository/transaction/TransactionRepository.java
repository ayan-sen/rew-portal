package com.rew.portal.repository.transaction;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {

	/*@Autowired
	EntityManagerFactory emf;
	
	public List<Map<String, Object>> getMaterialListByProject(String projectId) {
		
		EntityManager em = emf.createEntityManager();
		
		String query = "SELECT r FROM RawMaterial r";
		
		return em.createQuery(query).getResultList();
		
	}*/
}
