package com.ra2.Act1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ra2.Act1.model.Customer;

@Repository
public class CustomerRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final class CustomerRowMapper implements RowMapper<Customer> {
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setId(rs.getLong("id"));
			customer.setFirstName(rs.getString("f_name"));
			customer.setLastName(rs.getString("l_name"));
			customer.setAge(rs.getInt("age"));
			customer.setCicle(rs.getString("cicle"));
			customer.setYear(rs.getInt("year"));
			return customer;
		}
	}
	
	public void createTableCustomers() {
	    jdbcTemplate.execute("DROP TABLE IF EXISTS customers");
	    jdbcTemplate.execute("CREATE TABLE customers (id SERIAL PRIMARY KEY,f_name VARCHAR(255),l_name VARCHAR(255),age INTEGER,cicle VARCHAR(255),year INTEGER)");
	}
	public void insertSampleData() {
	    jdbcTemplate.update("INSERT INTO customers (f_name, l_name, age, cicle, year) VALUES (?, ?, ?, ?, ?)","John", "Doe", 20, "DAW", 2);
	    jdbcTemplate.update("INSERT INTO customers (f_name, l_name, age, cicle, year) VALUES (?, ?, ?, ?, ?)", "Jane", "Smith", 21, "DAM", 1);
	    jdbcTemplate.update("INSERT INTO customers (f_name, l_name, age, cicle, year) VALUES (?, ?, ?, ?, ?)", "Bob", "Johnson", 22, "ASIR", 2);
	}
	public List<Customer> findAll(){
		return jdbcTemplate.query("SELECT id, f_name, l_name, age, cicle, year FROM customers", new CustomerRowMapper());
	}
}
