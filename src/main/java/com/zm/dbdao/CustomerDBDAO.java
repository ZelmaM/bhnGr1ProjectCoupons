/*
Creating by Zelma Milev
*/
package com.zm.dbdao;

import com.zm.beans.Customer;
import com.zm.dao.CustomerDao;
import com.zm.utils.DBUtils;
import com.zm.utils.ResultSetUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
מחלקת מכילה כל הפונקציות CRUD לטיפול בטבלת לקחות- customers
*/
public class CustomerDBDAO implements CustomerDao {
    private static final String QUERY_INSERT = "INSERT INTO `bhn_gr1_zm_pr1`.`customers` (`first_name`,`last_name` , `email`,`password`) VALUES ( ?,?, ?,?);\n";
    private static final String QUERY_UPDATE = "UPDATE `bhn_gr1_zm_pr1`.`customers` SET `first_name` = ?,`last_name`  =?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `bhn_gr1_zm_pr1`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_ONE = "SELECT * FROM `bhn_gr1_zm_pr1`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `bhn_gr1_zm_pr1`.`customers`";
    private static final String QUERY_GET_COUNT_EMAIL = "SELECT COUNT(*) FROM `bhn_gr1_zm_pr1`.`customers` WHERE (`email` = ?);";
    private static final String QUERY_GET_ID_PER_EMAIL_PASS = "SELECT (`id`) FROM `bhn_gr1_zm_pr1`.`customers` WHERE (`email` = ? AND `password` = ?);";

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        DBUtils.runStatement(QUERY_INSERT, map);
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());

        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        map.put(5, customer.getId());
        DBUtils.runStatement(QUERY_UPDATE, map);
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        DBUtils.runStatement(QUERY_DELETE, map);
    }

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        return ResultSetUtils.isFound(DBUtils.runStatementWithResultSet(QUERY_GET_ID_PER_EMAIL_PASS, map));
    }

    @Override
    public int getCustomerID(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        return ResultSetUtils.getOneInt(DBUtils.runStatementWithResultSet(QUERY_GET_ID_PER_EMAIL_PASS, map));
    }

    @Override
    public boolean isCustomerExists(int customerID) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        return ResultSetUtils.isFound(DBUtils.runStatementWithResultSet(QUERY_GET_ONE, map));
    }

    @Override
    public Customer getOneCustomer(int customerId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ONE, map);
        resultSet.next();
        return ResultSetUtils.getCustomer(resultSet);
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL);
        while (resultSet.next()) {
            customerList.add(ResultSetUtils.getCustomer(resultSet));
        }
        return customerList;
    }

    @Override
    public boolean isEmailExist(String email) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        return ResultSetUtils.isCountGtZero(DBUtils.runStatementWithResultSet(QUERY_GET_COUNT_EMAIL, map));
    }

}