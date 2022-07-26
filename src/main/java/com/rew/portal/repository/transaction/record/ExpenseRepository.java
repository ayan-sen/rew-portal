package com.rew.portal.repository.transaction.record;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rew.portal.model.transaction.record.ExpenseRecord;

@Repository
public interface ExpenseRepository  extends JpaRepository<ExpenseRecord, Integer> {
	
	public List<ExpenseRecord> findByExpenseDate(LocalDate expenseDate);

}
