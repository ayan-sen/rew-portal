package com.rew.portal.controller.transaction.record;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rew.portal.model.transaction.record.ExpenseCategory;
import com.rew.portal.model.transaction.record.ExpenseRecord;
import com.rew.portal.model.transaction.record.ExpenseSummaryView;
import com.rew.portal.service.transaction.record.ExpenseRecordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ExpenseController {
	
	@Resource
	private ExpenseRecordService expenseRecordService;

	@PostMapping("/transaction/expense")
	public ResponseEntity<Map<String, String>> create(
			@RequestBody ExpenseRecord expenseRecord) {
		Map<String, String> response = new HashMap<>();
		try {
			expenseRecordService.save(expenseRecord);
			response.put("status", "success");
			response.put("message", "Expense record created Successfully");
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception in expense record creation", e);
			response.put("status", "failure");
			response.put("message",
					"Error occurred during expense record creation");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/transaction/expenses")
	public ResponseEntity<List<ExpenseRecord>> findAllExpenses() {
		return new ResponseEntity<List<ExpenseRecord>>(
				expenseRecordService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/transaction/expense/date")
	public ResponseEntity<List<ExpenseRecord>> findAllExpenses(@RequestParam(name="expenseDate", required = true) String expenseDate, @RequestParam(name = "toDate", required = false) String toDate) {
		LocalDate date = LocalDate.parse(expenseDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate endDate = null;
		if(StringUtils.isNotEmpty(toDate)) {
			endDate = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		return new ResponseEntity<List<ExpenseRecord>>(
				expenseRecordService.findAllByDate(date, endDate), HttpStatus.OK);
	}

	
	@GetMapping("/transaction/expense/report")
	public ResponseEntity<ExpenseSummaryView> findExpenseReports(@RequestParam(name="expenseDate", required = true) String expenseDate, @RequestParam(name = "toDate", required = false) String toDate) {
		LocalDate date = LocalDate.parse(expenseDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate endDate = null;
		if(StringUtils.isNotEmpty(toDate)) {
			endDate = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		return new ResponseEntity<ExpenseSummaryView>(
				expenseRecordService.getExpenseSummaryView(date, endDate), HttpStatus.OK);
	}


	@DeleteMapping("/transaction/expense/{code}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable Integer code) {
		Map<String, String> response = new HashMap<>();
		try {
			expenseRecordService.delete(code);
			response.put("status", "success");
			response.put("message", "Expense deleted successfully");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception in expense deletion", e);
			response.put("status", "failure");
			response.put("message", "Error occurred during expense deletion");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/transaction/categories")
	public ResponseEntity<List<ExpenseCategory>> findAll() {
		return  new ResponseEntity<List<ExpenseCategory>>(expenseRecordService.findAllCategories(), HttpStatus.OK);
	}

}
