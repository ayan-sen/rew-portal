package com.rew.portal.repository.transaction.orderProcessing;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.orderProcessing.OrderProcessing;
import com.rew.portal.model.transaction.record.ExpenseRecord;

@Repository
public interface OrderProcessingRepository extends JpaRepository<OrderProcessing, Integer> {

	public List<OrderProcessing> findByIsActiveOrderByProcessDateDescSiteIdAscProjectIdAsc(boolean isActive);
	
	
	@Query(value="SELECT r.code, r.name, r.unitId, u.unitName, r.type  FROM raw_material r, unit u "
				+ "WHERE CODE IN ("
				+ "SELECT DISTINCT d.rmid FROM project_d d WHERE projectId=?1 "
				+ "UNION "
				+ "SELECT DISTINCT i.materialCode FROM inventory_record i WHERE projectId=?1 "
				+ "UNION "
				+ "SELECT DISTINCT r.code FROM raw_material r WHERE r.type in ('R','S') " 
				+ ") AND r.unitId = u.unitId", nativeQuery=true)
	public List<Map<String, Object>> getMaterialListByProject(String projectId);
	
	
	public List<OrderProcessing> findByProcessDateOrderByProjectIdAsc(LocalDate processDate);
	
	public List<OrderProcessing> findByProcessDateBetweenOrderByProjectIdAsc(LocalDate expenseDate, LocalDate toDate);
	
	@Query(value="SELECT r.code, r.name, r.unitId, u.unitName, r.type  FROM raw_material r, unit u "
			+ "WHERE CODE IN ("
			+ "SELECT DISTINCT d.rmid FROM project_d d WHERE projectId=?1 "
			+ "UNION "
			+ "SELECT DISTINCT i.materialCode FROM inventory_record i WHERE projectId=?1 "
			+ "UNION "
			+ "SELECT DISTINCT r.code FROM raw_material r WHERE r.type in ('P') " 
			+ ") AND r.unitId = u.unitId AND r.type in ('P')", nativeQuery=true)
public List<Map<String, Object>> getProductsByProject(String projectId);
	
}
