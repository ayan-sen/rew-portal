package com.rew.portal.model.transaction.record;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rew.portal.model.admin.unit.Unit;

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
public class ExpenseRecord implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2325785071705071302L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="expenseId", length=20, nullable=false)
	private Integer expenseId;
	
	@JsonIgnore
	@Column(name="expenseDate", nullable=false)
	private LocalDate expenseDate;
	
	@Transient
	private String expenseDateString;
	
	@Column(name="description", length=200, nullable=false)
	private String description;
	
	@Column(name="sgstAmount", length=20, nullable=false)
	private Double amount;
	
	@Column(name="categoryId", length=20, nullable=true)
	private String categoryId;
	
	@Transient
	private String categoryName;
	
	
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="categoryId",insertable=false,updatable=false)
	private ExpenseCategory expenseCategory;

	public String getExpenseDateString() {
		if (this.expenseDate != null) {
			return this.expenseDate.format(DateTimeFormatter
					.ofPattern("yyyy-MM-dd"));
		}
		return null;
		
	}
	
	public void setExpenseDateString(String expenseDateString) {
		this.expenseDateString = expenseDateString;
		
		if (StringUtils.isNotEmpty(expenseDateString)) {  
			this.expenseDate = LocalDate.parse(
					this.expenseDateString,
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}

	public String getCategoryName() {
		if(this.expenseCategory != null) {
			return this.expenseCategory.getCategoryName();
		}
		return this.categoryName;
	}

}
