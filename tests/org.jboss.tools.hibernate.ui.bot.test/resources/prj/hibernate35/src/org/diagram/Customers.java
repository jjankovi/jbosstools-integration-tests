package org.diagram;

// Generated Jul 16, 2012 4:06:14 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Customers generated by hbm2java
 */
public class Customers implements java.io.Serializable {

	private int customernumber;
	private Employees employees;
	private String addressline1;
	private String addressline2;
	private String city;
	private String contactfirstname;
	private String contactlastname;
	private String country;
	private Double creditlimit;
	private String customername;
	private String phone;
	private String postalcode;
	private String state;
	private Set orderses = new HashSet(0);
	private Set paymentses = new HashSet(0);

	public Customers() {
	}

	public Customers(int customernumber, String addressline1, String city,
			String contactfirstname, String contactlastname, String country,
			String customername, String phone) {
		this.customernumber = customernumber;
		this.addressline1 = addressline1;
		this.city = city;
		this.contactfirstname = contactfirstname;
		this.contactlastname = contactlastname;
		this.country = country;
		this.customername = customername;
		this.phone = phone;
	}

	public Customers(int customernumber, Employees employees,
			String addressline1, String addressline2, String city,
			String contactfirstname, String contactlastname, String country,
			Double creditlimit, String customername, String phone,
			String postalcode, String state, Set orderses, Set paymentses) {
		this.customernumber = customernumber;
		this.employees = employees;
		this.addressline1 = addressline1;
		this.addressline2 = addressline2;
		this.city = city;
		this.contactfirstname = contactfirstname;
		this.contactlastname = contactlastname;
		this.country = country;
		this.creditlimit = creditlimit;
		this.customername = customername;
		this.phone = phone;
		this.postalcode = postalcode;
		this.state = state;
		this.orderses = orderses;
		this.paymentses = paymentses;
	}

	public int getCustomernumber() {
		return this.customernumber;
	}

	public void setCustomernumber(int customernumber) {
		this.customernumber = customernumber;
	}

	public Employees getEmployees() {
		return this.employees;
	}

	public void setEmployees(Employees employees) {
		this.employees = employees;
	}

	public String getAddressline1() {
		return this.addressline1;
	}

	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}

	public String getAddressline2() {
		return this.addressline2;
	}

	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactfirstname() {
		return this.contactfirstname;
	}

	public void setContactfirstname(String contactfirstname) {
		this.contactfirstname = contactfirstname;
	}

	public String getContactlastname() {
		return this.contactlastname;
	}

	public void setContactlastname(String contactlastname) {
		this.contactlastname = contactlastname;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getCreditlimit() {
		return this.creditlimit;
	}

	public void setCreditlimit(Double creditlimit) {
		this.creditlimit = creditlimit;
	}

	public String getCustomername() {
		return this.customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostalcode() {
		return this.postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set getOrderses() {
		return this.orderses;
	}

	public void setOrderses(Set orderses) {
		this.orderses = orderses;
	}

	public Set getPaymentses() {
		return this.paymentses;
	}

	public void setPaymentses(Set paymentses) {
		this.paymentses = paymentses;
	}

}
