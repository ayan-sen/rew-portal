package com.rew.portal.repository.transaction.orderProcessing;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.orderProcessing.OrderProcessing;

@Repository
public interface OrderProcessingRepository extends JpaRepository<OrderProcessing, Integer> {

	public List<OrderProcessing> findByIsActive(boolean isActive);
	
	
	@Query(value="SELECT r.code, r.name, r.unitId, u.unitName, r.type  FROM raw_material r, unit u "
				+ "WHERE CODE IN ("
				+ "SELECT DISTINCT d.rmid FROM project_d d WHERE projectId=?1 "
				+ "UNION "
				+ "SELECT DISTINCT i.materialCode FROM inventory_record i WHERE projectId=?1 "
				+ "UNION "
				+ "SELECT DISTINCT r.code FROM raw_material r WHERE r.type='S' " 
				+ ") AND r.unitId = u.unitId", nativeQuery=true)
	public List<Map<String, Object>> getMaterialListByProject(String projectId);
}
