package com.ra2.Act1.controller;

import com.ra2.Act1.model.Customer; 
import com.ra2.Act1.repository.CustomerRepository;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/jdbctemplate")

public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@RequestMapping("/hello")
	public String jdbctemp() {
		
		return "hello";
		}
	@PostMapping("/init-db")
	public String initializeDatabase() {
		customerRepository.createTableCustomers();
		customerRepository.insertSampleData();
		return "Base de datdes inicialitzada correctament";
	}
	@GetMapping("/findAllCustomers")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
}
