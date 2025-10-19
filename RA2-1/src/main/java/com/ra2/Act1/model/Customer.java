package com.ra2.Act1.model;

public class Customer {
	private long id;
	private String firstName, lastName, cicle;
	private int age, year;
	public Customer() {
		
	}
	//TODO A LA VEZ
	public Customer(long id, String firstName, String lastName, int age, String cicle, int year) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.cicle = cicle;
		this.year = year;
	}
	//RESULTADO
	@Override
	public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s', age=%d, cicle='%s', year=%d]",
                id, firstName, lastName, age, cicle, year);
    }
	//SETTERS Y GETTERS
	public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCicle() { return cicle; }
    public void setCicle(String cicle) { this.cicle = cicle; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
