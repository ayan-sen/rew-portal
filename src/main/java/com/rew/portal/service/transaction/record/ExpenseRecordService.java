package com.rew.portal.service.transaction.record;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.rew.portal.model.transaction.record.ExpenseCategory;
import com.rew.portal.model.transaction.record.ExpenseRecord;
import com.rew.portal.model.transaction.record.ExpenseSummaryView;
import com.rew.portal.repository.transaction.record.ExpenseCategoryRepository;
import com.rew.portal.repository.transaction.record.ExpenseRepository;

@Service
public class ExpenseRecordService {
	
	@Resource
	private ExpenseRepository expenseRepository;
	
	@Resource
	private ExpenseCategoryRepository expenseCategoryRepository;
	
	public void save(ExpenseRecord expense) {
		expenseRepository.save(expense);
	}
	
	
	public List<ExpenseRecord> findAll() {
		return expenseRepository.findAll();
	}
	
	public List<ExpenseRecord> findAllByDate(LocalDate expenseDate, LocalDate toDate) {
		if(Objects.isNull(toDate)) {
			return expenseRepository.findByExpenseDate(expenseDate);
		}
		return expenseRepository.findByExpenseDateBetween(expenseDate, toDate);
	}
	
	
	public ExpenseSummaryView getExpenseSummaryView(LocalDate expenseDate, LocalDate toDate) {
		List<ExpenseRecord> records = this.findAllByDate(expenseDate, toDate);
		Double totalExpense = records.stream().map(rec -> rec.getAmount()).reduce(0D, Double::sum);
		Map<String, Double> summary = records.stream().collect(Collectors.groupingBy(rec -> rec.getCategoryName(), Collectors.summingDouble(rec -> rec.getAmount())));
		return new ExpenseSummaryView(summary, totalExpense, records);
		
		
	}
	
	
	public void delete(Integer id) throws ObjectNotFoundException {
		expenseRepository.deleteById(id);
	}
	
	public List<ExpenseCategory> findAllCategories() {
		return this.expenseCategoryRepository.findAll();
	}

}
