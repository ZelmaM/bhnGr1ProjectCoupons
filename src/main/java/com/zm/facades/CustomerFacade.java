/*
Creating by Zelma Milev
*/
package com.zm.facades;

import com.zm.beans.Category;
import com.zm.beans.Coupon;
import com.zm.beans.Customer;
import com.zm.beans.ErrorMsg;
import com.zm.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.zm.utils.DateUtils.convertToDataViaData;
/*
Customer מכילה את הלוגיקה העסקית של – CustomerFacade
 */
public class CustomerFacade extends ClientFacade {
    private int customerID;

    public CustomerFacade() {
    }

    public int getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void purchaseCoupon(Coupon coupon) throws SQLException, CouponSystemException {

        if (!this.couponDao.isCouponExists(coupon.getId())) {
            throw new CouponSystemException(ErrorMsg.COUPON_ID_NOT_EXIST);
        }
        Coupon couponDB = this.couponDao.getOneCoupon(coupon.getId());
        if (couponDB.getAmount() == 0) {
            throw new CouponSystemException(ErrorMsg.COUPON_EXHAUSTED);
        }
        if (couponDB.getEndDate().before(convertToDataViaData(LocalDate.now()))) {
            throw new CouponSystemException(ErrorMsg.COUPON_TIME_EXPIRED);
        }
        if (this.couponDao.isCustomerCouponExists(getCustomerID())) {
            if (getCustomerCoupons().stream().anyMatch(coupon1 -> coupon1.getId() == coupon.getId())) {
                throw new CouponSystemException(ErrorMsg.COUPON_DOUBLE_PURCHASE);
            }
        }
        this.couponDao.addCouponPurchase(getCustomerID(), coupon.getId());
        couponDB.setAmount(couponDB.getAmount()-1);
        this.couponDao.updateCoupon(couponDB);

    }
    public void purchaseCoupon(Integer couponID) throws SQLException, CouponSystemException {

        if (!this.couponDao.isCouponExists(couponID)) {
            throw new CouponSystemException(ErrorMsg.COUPON_ID_NOT_EXIST);
        }
        Coupon couponDB = this.couponDao.getOneCoupon(couponID);
        if (couponDB.getAmount() == 0) {
            throw new CouponSystemException(ErrorMsg.COUPON_EXHAUSTED);
        }
        if (couponDB.getEndDate().before(convertToDataViaData(LocalDate.now()))) {
            throw new CouponSystemException(ErrorMsg.COUPON_TIME_EXPIRED);

        }
        if (this.couponDao.isCustomerCouponExists(getCustomerID())) {
            if (getCustomerCoupons().stream().anyMatch(coupon1 -> coupon1.getId() == couponID)) {
                throw new CouponSystemException(ErrorMsg.COUPON_DOUBLE_PURCHASE);
            }
        }

        this.couponDao.addCouponPurchase(getCustomerID(), couponID);
        couponDB.setAmount(couponDB.getAmount()-1);
        this.couponDao.updateCoupon(couponDB);

    }


    public Customer getCustomerDetails() throws SQLException {
        return this.customerDao.getOneCustomer(getCustomerID());
    }

    public List<Coupon> getCustomerCoupons() throws SQLException, CouponSystemException {
        if (!this.couponDao.isCustomerCouponExists(getCustomerID())) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_WITHOUT_COUPONS);
        }
        return this.couponDao.getAllCustomersCoupons(getCustomerID());
    }

    public List<Coupon> getCustomerCoupons(Category category) throws SQLException, CouponSystemException {
        return getCustomerCoupons().stream().filter(coupon -> coupon.getCategoryId() == category.getVal()).collect(Collectors.toList());
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) throws SQLException, CouponSystemException {
        return getCustomerCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice).collect(Collectors.toList());
    }

    @Override
    public Boolean login(String email, String password) throws SQLException {
        return (this.customerDao.isCustomerExists(email, password));
    }
}
