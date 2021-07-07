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

public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    List<Coupon> coupons = new ArrayList<>();

    public Company(int id, String name, String email, String password) {
        this(name, email, password);
        this.id = id;
    }

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public List<Coupon> getCoupons() throws SQLException, CouponSystemException {

        CouponDao couponDao = new CouponDBDAO();
        if (!couponDao.isCompanyCouponsExists(this.id)) {
            throw new CouponSystemException(ErrorMsg.COMPANY_WITHOUT_COUPONS);
        }
        coupons = couponDao.getAllCompanyCoupons(this.id);
        return coupons;

    }

}
