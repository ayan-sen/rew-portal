package com.rew.portal.model.transaction.record;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Entity(name="expense_record")
@Table(name="expense_record")
public class ExpenseRecord {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="expenseId", length=20, nullable=false)
	private Integer expenseId;
	
	@JsonIgnore
	@Column(name="expenseDate", length=20, nullable=false)
	private LocalDate expenseDate;
	
	@Transient
	private String expenseDateString;
	
	@Column(name="description", length=20, nullable=false)
	private String description;
	
	@Column(name="sgstAmount", length=20, nullable=false)
	private Double amount;

}
