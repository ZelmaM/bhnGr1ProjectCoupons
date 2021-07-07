/*
Creating by Zelma Milev
*/
package com.zm.utils;

import com.zm.beans.Company;
import com.zm.beans.Coupon;
import com.zm.beans.Customer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/*
  פונקציות עזר לטיפול בResultSet למניעת כפילויות בכתיבת קוד
 */
public class ResultSetUtils {
    public static boolean isCountGtZero(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return resultSet.getInt(1) > 0;
    }

    public static boolean isFound(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }

    public static int getOneInt(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return resultSet.getInt(1);
    }

    public static Customer getCustomer(ResultSet resultSet) throws SQLException {
        int customerId = resultSet.getInt(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String email = resultSet.getString(4);
        String password = resultSet.getString(5);
        return new Customer(customerId, firstName, lastName, email, password);
    }

    public static Company getCompany(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String email = resultSet.getString(3);
        String password = resultSet.getString(4);
        return new Company(id, name, email, password);
    }

    public static Coupon getCoupon(ResultSet resultSet) throws SQLException {
        int couponID = resultSet.getInt(1);
        int companyID = resultSet.getInt(2);
        int categoryID = resultSet.getInt(3);
        String title = resultSet.getString(4);
        String description = resultSet.getString(5);
        Date startDate = resultSet.getDate(6);
        Date endDate = resultSet.getDate(7);
        int amount = resultSet.getInt(8);
        double price = resultSet.getDouble(9);
        String image = resultSet.getString(10);
        return new Coupon(couponID, companyID, categoryID, title, description, startDate, endDate, amount, price, image);
    }

    public static Map<Integer, Object> setCompanyMap(Coupon coupon) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getCompanyId());
        map.put(2, coupon.getCategoryId());
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStartDate());
        map.put(6, coupon.getEndDate());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        return map;
    }

    public static Map<Integer, Object> setCompanyMapWithId(Coupon coupon) {
        Map<Integer, Object> map = setCompanyMap(coupon);
        map.put(10, coupon.getId());
        return map;
    }
}
