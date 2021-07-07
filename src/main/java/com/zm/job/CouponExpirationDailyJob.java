/*
Creating by Zelma Milev
*/
package com.zm.job;

import com.zm.beans.Coupon;
import com.zm.dao.CouponDao;
import com.zm.dbdao.CouponDBDAO;
import com.zm.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.List;


public class CouponExpirationDailyJob implements Runnable  {
   static boolean quit = false;
    long timeToSlip = 1000 * 60 * 60 * 24;

    @Override
    public void run() {
        while (!quit) {
            try {
                deleteAllExpiredCoupons();
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
            try {
                timeToSlip = 1000;
                Thread.sleep(timeToSlip);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void deleteAllExpiredCoupons() throws SQLException, CouponSystemException {
        CouponDao couponDao = new CouponDBDAO();
        System.out.println("<SYSTEM WARNING> ALL COUPONS COUNT " + couponDao.getAllCoupons().size());
        if (!couponDao.isExpiredCouponExists()) {
            throw new CouponSystemException(" <SYSTEM WARNING> Not Expired Coupons");
        }
        System.out.println("<SYSTEM WARNING> START SYSTEM DELETE");
        List<Coupon> couponList = couponDao.getAllExpiredCoupons();
        for (Coupon c : couponList) {
            couponDao.deleteCouponPurchaseCouponID(c.getId());
            couponDao.deleteCoupon(c.getId());
            System.out.println("<SYSTEM WARNING>" + c.toString() + "Was expired and deleted <SYSTEM WARNING>");
        }
        System.out.println("<SYSTEM WARNING> END SYSTEM DELETE");
        System.out.println("<SYSTEM WARNING> ALL COUPONS COUNT " + couponDao.getAllCoupons().size());
    }


    public  static void stop() {
        quit = true;
        System.out.println("<SYSTEM WARNING> STOP THREAD");
    }
}
