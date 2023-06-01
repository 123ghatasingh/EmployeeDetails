package com.interview.entity;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> phoneNumbers;
	private LocalDate doj;
	private double salary;

	public LocalDate getDoj() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSalary() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}
}
