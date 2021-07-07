package com.zm.dao;

import com.zm.beans.Customer;

import java.sql.SQLException;
import java.util.List;

/*
Data Access Objects מחלקות לבצע פעולות CRUD
 על הטבלאות במסד הנתונים. מחלקות אלו לא מבצעות את הלוגיקה ((Create/Read/Update/Delete
 */
public interface CustomerDao {
    void addCustomer(Customer customer) throws SQLException;
    void updateCustomer(Customer customer) throws SQLException;
    void deleteCustomer(int customerId) throws SQLException;

    boolean isCustomerExists(String email,String password) throws SQLException;
    boolean isCustomerExists(int customerID) throws SQLException;
    int getCustomerID(String email,String password) throws SQLException;
    Customer getOneCustomer(int customerId) throws SQLException;

    List<Customer> getAllCustomers() throws SQLException;

    boolean isEmailExist(String email) throws SQLException;
}
