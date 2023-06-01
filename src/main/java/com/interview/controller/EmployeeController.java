package com.interview.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.dao.TaxDeductionDTO;
import com.interview.entity.Employee;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private List<Employee> employees = new ArrayList<>();

	@PostMapping
	public ResponseEntity<String> storeEmployeeDetails(@Valid @RequestBody Employee employee) {
		// Validate and store the employee details
		employees.add(employee);
		return ResponseEntity.ok("Employee details stored successfully.");
	}

	@GetMapping("/tax-deduction")
	public ResponseEntity<List<TaxDeductionDTO>> getTaxDeductions() {
		// Calculate and return tax deductions for the current financial year
		List<TaxDeductionDTO> taxDeductions = new ArrayList<>();

		// Iterate through employees and calculate tax deduction for each employee
		for (Employee employee : employees) {
			TaxDeductionDTO taxDeduction = calculateTaxDeductionDTO(employee);
			taxDeductions.add(taxDeduction);
		}

		return ResponseEntity.ok(taxDeductions);
	}

	// Helper method to calculate tax deduction for an employee
	private TaxDeductionDTO calculateTaxDeductionDTO(Employee employee) {
		TaxDeductionDTO taxDeduction = new TaxDeductionDTO();

		// Set employee details
		taxDeduction.setEmployeeCode(employee.getEmployeeId());
		taxDeduction.setFirstName(employee.getFirstName());
		taxDeduction.setLastName(employee.getLastName());

		// Calculate yearly salary considering DOJ
		LocalDate currentDate = LocalDate.now();
		int currentYear = currentDate.getYear();
		int dojYear = employee.getDoj().getYear();
		int monthsWorked = (currentYear - dojYear) * 12 + currentDate.getMonthValue()
				- employee.getDoj().getMonthValue();
		double yearlySalary = employee.getSalary() * monthsWorked / 12.0;
		taxDeduction.setYearlySalary(yearlySalary);

		// Calculate tax amount
		double taxAmount = 0.0;
		if (yearlySalary > 1000000) {
			taxAmount = (yearlySalary - 1000000) * 0.2 + 500000 * 0.1 + 250000 * 0.05;
		} else if (yearlySalary > 500000) {
			taxAmount = (yearlySalary - 500000) * 0.1 + 250000 * 0.05;
		} else if (yearlySalary > 250000) {
			taxAmount = (yearlySalary - 250000) * 0.05;
		}
		taxDeduction.setTaxAmount(taxAmount);

		// Calculate cess amount
		double cessAmount = 0.0;
		if (yearlySalary > 2500000) {
			double cessableAmount = yearlySalary - 2500000;
			cessAmount = cessableAmount * 0.02;
		}
		taxDeduction.setTaxAmount(cessAmount);

		return taxDeduction;
	}
}
