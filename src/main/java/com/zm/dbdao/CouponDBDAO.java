/*
Creating by Zelma Milev
*/
package com.zm.dbdao;

import com.zm.beans.Coupon;
import com.zm.dao.CouponDao;
import com.zm.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zm.utils.ResultSetUtils.*;
/*
מחלקת מכילה כל הפונקציות CRUD לטיפול בטבלאות :
coupons,customers_vs_coupons
*/
public class CouponDBDAO implements CouponDao {
    private static final String QUERY_INSERT = "INSERT INTO `bhn_gr1_zm_pr1`.`coupons`" +
            " (`company_id`, `category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`) " +
            "VALUES ( ?, ?,?,?,?,?,?,?,?);\n";
    private static final String QUERY_UPDATE = "UPDATE `bhn_gr1_zm_pr1`.`coupons` SET " +
            "`company_id`=?, `category_id`=?,`title`=?,`description`=?,`start_date`=?," +
            "`end_date`=?,`amount`=?,`price`=?,`image`=? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `bhn_gr1_zm_pr1`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_DELETE_COMPANIES_COUPONS = "DELETE FROM `bhn_gr1_zm_pr1`.`coupons` WHERE (`company_id` = ?);";
    private static final String QUERY_GET_ONE_COUPON = "SELECT * FROM `bhn_gr1_zm_pr1`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS = "SELECT * FROM `bhn_gr1_zm_pr1`.`coupons`";
    private static final String QUERY_GET_ALL_COUPONS_PER_COMPANY = "SELECT * FROM `bhn_gr1_zm_pr1`.`coupons` WHERE (`company_id` = ?);";
    private static final String QUERY_GET_ALL_EXPIRED_COUPONS = "SELECT * FROM `bhn_gr1_zm_pr1`.`coupons` WHERE (`end_date` < ?);";
    private static final String QUERY_GET_COUNT_END_DAY = "SELECT COUNT(*) FROM `bhn_gr1_zm_pr1`.`coupons` WHERE (`end_date` < ?);";
    private static final String QUERY_GET_ALL_COUPONS_PER_CUSTOMER = "SELECT * FROM ( SELECT * FROM `bhn_gr1_zm_pr1`.`coupons`)   `a` INNER JOIN ( SELECT * FROM `bhn_gr1_zm_pr1`.`customers_vs_coupons` WHERE `customer_id` = ?)   `b` ON `a`.`id`= `b`.`coupon_id` ;";
    private static final String QUERY_INSERT_CUSTOMER_VS_COUPONS = "INSERT INTO `bhn_gr1_zm_pr1`.`customers_vs_coupons` (`customer_id`,`coupon_id` ) VALUES ( ?,?);\n";
    private static final String QUERY_DELETE_CUSTOMER_VS_COUPONS = "DELETE FROM `bhn_gr1_zm_pr1`.`customers_vs_coupons` WHERE (`customer_id` = ? AND `coupon_id`= ?);";
    private static final String QUERY_DELETE_CUSTOMER_VS_COUPONS_COUPON_ID = "DELETE FROM `bhn_gr1_zm_pr1`.`customers_vs_coupons` WHERE (`coupon_id`= ?);";
    private static final String QUERY_DELETE_CUSTOMER_VS_COUPONS_CUSTOMER_ID = "DELETE FROM `bhn_gr1_zm_pr1`.`customers_vs_coupons` WHERE (`customer_id` = ? );";
    private static final String QUERY_GET_COUNT_COMPANY_ID = "SELECT COUNT(*) FROM `bhn_gr1_zm_pr1`.`coupons` WHERE (`company_id` = ?);";
    private static final String QUERY_GET_COUNT_CUSTOMER_ID = "SELECT COUNT(*) FROM `bhn_gr1_zm_pr1`.`customers_vs_coupons` WHERE (`customer_id` = ?);";
    private static final String QUERY_GET_COUNT_COMPANY_ID_TITLE = "SELECT COUNT(*) FROM `bhn_gr1_zm_pr1`.`coupons` WHERE (`company_id` = ? AND `title`=?);";

    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        DBUtils.runStatement(QUERY_INSERT, setCompanyMap(coupon));
    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        DBUtils.runStatement(QUERY_UPDATE, setCompanyMapWithId(coupon));
    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponId);
        DBUtils.runStatement(QUERY_DELETE, map);
    }

    @Override
    public void deleteAllCompanyCoupons(int companyId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        DBUtils.runStatement(QUERY_DELETE_COMPANIES_COUPONS,map);
    }

    @Override
    public boolean isCompanyCouponsExists(int companyId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        return isCountGtZero(DBUtils.runStatementWithResultSet(QUERY_GET_COUNT_COMPANY_ID, map));
    }

    @Override
    public boolean isCustomerCouponExists(int customerID) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        return isCountGtZero(DBUtils.runStatementWithResultSet(QUERY_GET_COUNT_CUSTOMER_ID, map));
    }

    @Override
    public boolean isExpiredCouponExists() throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, (LocalDate.now()));
        return isCountGtZero(DBUtils.runStatementWithResultSet(QUERY_GET_COUNT_END_DAY, map));
        }

    public boolean isCompanyTheSameTitleCouponsExists(int companyId, String title) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, title);
        return isCountGtZero(DBUtils.runStatementWithResultSet(QUERY_GET_COUNT_COMPANY_ID_TITLE, map));
    }

    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);
        DBUtils.runStatement(QUERY_INSERT_CUSTOMER_VS_COUPONS, map);
    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);
        DBUtils.runStatement(QUERY_DELETE_CUSTOMER_VS_COUPONS, map);
    }

    @Override
    public void deleteCouponPurchaseCustomerID(int customerId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        DBUtils.runStatement(QUERY_DELETE_CUSTOMER_VS_COUPONS_CUSTOMER_ID, map);
    }

    @Override
    public void deleteCouponPurchaseCouponID(int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponId);
        DBUtils.runStatement(QUERY_DELETE_CUSTOMER_VS_COUPONS_COUPON_ID, map);
    }

    @Override
    public boolean isCouponExists(int couponID) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        return isFound(DBUtils.runStatementWithResultSet(QUERY_GET_ONE_COUPON, map));
    }

    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ONE_COUPON, map);
        resultSet.next();
        return getCoupon(resultSet);
    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        List<Coupon> couponList = new ArrayList<>();
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_COUPONS);
        while (resultSet.next()) {
            couponList.add(getCoupon(resultSet));
        }
        return couponList;
    }

    @Override
    public List<Coupon> getAllCompanyCoupons(int companyID) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyID);
        List<Coupon> couponList = new ArrayList<>();
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_COUPONS_PER_COMPANY, map);
        while (resultSet.next()) {
            couponList.add(getCoupon(resultSet));
        }
        return couponList;
    }

    @Override
    public List<Coupon> getAllCustomersCoupons(int customerId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        List<Coupon> couponList = new ArrayList<>();
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_COUPONS_PER_CUSTOMER, map);
        while (resultSet.next()) {
            couponList.add(getCoupon(resultSet));
        }
        return couponList;
    }

    @Override
    public List<Coupon> getAllExpiredCoupons() throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, (LocalDate.now()));
        List<Coupon> couponList = new ArrayList<>();
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_EXPIRED_COUPONS, map);
        while (resultSet.next()) {
            couponList.add(getCoupon(resultSet));
        }
        return couponList;
    }
}
