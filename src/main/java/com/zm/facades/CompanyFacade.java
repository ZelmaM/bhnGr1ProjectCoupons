/*
Creating by Zelma Milev
*/
package com.zm.facades;

import com.zm.beans.*;
import com.zm.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
/*
Company מכילה את הלוגיקה העסקית של – CompanyFacade
 */
public class CompanyFacade extends ClientFacade {
    private int companyID;

    /*
        optional
     */
    public CompanyFacade() {
        super();
    }

    public int getCompanyID() {
        return this.companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public void addCoupon(Coupon coupon) throws SQLException, CouponSystemException {

        if (this.getCompanyID() != coupon.getCompanyId()) {
            throw new CouponSystemException(ErrorMsg.COUPON_WRONG_COMPANY_ID_ADD);
        }
        if (this.couponDao.isCompanyTheSameTitleCouponsExists(coupon.getCompanyId(), coupon.getTitle())) {
            throw new CouponSystemException(ErrorMsg.COUPON_WRONG_TITLE);
        }
        this.couponDao.addCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon) throws SQLException, CouponSystemException {
        if (coupon.getCompanyId() != getCompanyID()) {
            throw new CouponSystemException(ErrorMsg.COUPON_WRONG_COMPANY_ID_UPDATE);
        }
        if (!this.couponDao.isCouponExists(coupon.getId())) {
            throw new CouponSystemException(ErrorMsg.COUPON_ID_NOT_EXIST);
        }
        this.couponDao.updateCoupon(coupon);
    }

    public void deleteCoupon(int couponID) throws CouponSystemException, SQLException {
        if (!this.couponDao.isCouponExists(couponID)) {
            throw new CouponSystemException(ErrorMsg.COUPON_ID_NOT_EXIST);
        }
        this.couponDao.deleteCouponPurchaseCouponID(couponID);
        this.couponDao.deleteCoupon(couponID);
    }

    public Company getCompanyDetails() throws SQLException {
        return this.companyDao.getOneCompany(getCompanyID());
    }

    public List<Coupon> getCompanyCoupons() throws SQLException, CouponSystemException {
        if (!this.couponDao.isCompanyCouponsExists(getCompanyID())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_WITHOUT_COUPONS);
        }
        return this.couponDao.getAllCompanyCoupons(getCompanyID());
    }

    public List<Coupon> getCompanyCoupons(Category category) throws SQLException, CouponSystemException {
        if (getCompanyCoupons().stream().noneMatch(coupon -> coupon.getCategoryId() == category.getVal())) {
//        if (getCompanyCoupons().stream().filter(coupon -> coupon.getCategoryId() == category.getVal()).count() == 0) {
            throw new CouponSystemException(ErrorMsg.COMPANY_WITHOUT_CATEGORIES_COUPONS);
        }
        return getCompanyCoupons().stream().filter(coupon -> coupon.getCategoryId() == category.getVal()).collect(Collectors.toList());
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) throws SQLException, CouponSystemException {
        return getCompanyCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice).collect(Collectors.toList());

    }

    @Override
    public Boolean login(String email, String password) throws SQLException {
        return (this.companyDao.isCompanyExists(email, password));
    }
}
