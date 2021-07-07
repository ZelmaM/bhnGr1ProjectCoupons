package com.zm.dao;

import com.zm.beans.Coupon;

import java.sql.SQLException;
import java.util.List;

/*
Data Access Objects מחלקות לבצע פעולות CRUD
 על הטבלאות במסד הנתונים. מחלקות אלו לא מבצעות את הלוגיקה ((Create/Read/Update/Delete
 */
public interface CouponDao {
    void addCoupon(Coupon coupon) throws SQLException;
    void updateCoupon(Coupon coupon) throws SQLException;
    void deleteCoupon(int couponId) throws SQLException;
    void deleteAllCompanyCoupons(int companyId) throws SQLException;
    boolean isCompanyCouponsExists(int companyId) throws SQLException;
    boolean isCouponExists(int couponID) throws SQLException;
    boolean isCustomerCouponExists(int customerID) throws SQLException;
    boolean isExpiredCouponExists() throws SQLException;

    boolean isCompanyTheSameTitleCouponsExists(int companyId, String title) throws SQLException;
    void addCouponPurchase(int customerId ,int couponId) throws SQLException;
    void deleteCouponPurchase(int customerId ,int couponId) throws SQLException;
    void deleteCouponPurchaseCustomerID(int customerId ) throws SQLException;
    void deleteCouponPurchaseCouponID(int couponId) throws SQLException;

    Coupon getOneCoupon(int couponId) throws SQLException;

    List<Coupon> getAllCoupons() throws SQLException;
    List<Coupon> getAllCompanyCoupons(int companyId) throws SQLException;
    List<Coupon> getAllCustomersCoupons(int customerId) throws SQLException;
    List<Coupon> getAllExpiredCoupons() throws SQLException;
}

