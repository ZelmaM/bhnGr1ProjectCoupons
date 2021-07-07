/*
Creating by Zelma Milev
*/
package com.zm.beans;

import com.zm.dao.CouponDao;
import com.zm.dbdao.CouponDBDAO;
import com.zm.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    List<Coupon> coupons = new ArrayList<>();

    public Customer(int id, String firstName, String lastName, String email, String password) {
        this(firstName, lastName, email, password);
        this.id = id;

    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public List<Coupon> getCoupons() throws SQLException, CouponSystemException {

        CouponDao couponDao = new CouponDBDAO();
        if (!couponDao.isCustomerCouponExists(this.id)) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_WITHOUT_COUPONS);
        }
        coupons = couponDao.getAllCustomersCoupons(this.id);
        return coupons;

    }

}
