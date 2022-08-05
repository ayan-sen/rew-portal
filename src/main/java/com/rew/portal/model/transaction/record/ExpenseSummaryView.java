package com.rew.portal.model.transaction.record;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ExpenseSummaryView {
	
	private Map<String, Double> catagorisedExpenses;
	private Double totalExpense;
	private List<ExpenseRecord> details;
	

}
