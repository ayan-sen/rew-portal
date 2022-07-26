package com.rew.portal.service.transaction.record;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.record.ExpenseRecord;
import com.rew.portal.repository.transaction.record.ExpenseRepository;

@Service
public class ExpenseRecordService {
	
	@Resource
	private ExpenseRepository expenseRepository;
	
	public void save(ExpenseRecord expense) {
		expenseRepository.save(expense);
	}
	
	
	public List<ExpenseRecord> findAll() {
		return expenseRepository.findAll();
	}
	
	public List<ExpenseRecord> findAllByDate(LocalDate expenseDate) {
		return expenseRepository.findByExpenseDate(expenseDate);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		expenseRepository.deleteById(id);
	}

}
