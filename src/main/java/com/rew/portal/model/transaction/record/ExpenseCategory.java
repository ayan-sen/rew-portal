package com.rew.portal.model.transaction.record;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity(name="expense_category")
@Table(name="expense_category")
public class ExpenseCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="categoryId", length=20, nullable=false)
	private Integer categoryId;
	
	@Column(name="categoryName", nullable=false)
	private String categoryName;

}
