/*
Creating by Zelma Milev
*/
package com.zm.facades;

import com.zm.dao.CompanyDao;
import com.zm.dao.CouponDao;
import com.zm.dao.CustomerDao;
import com.zm.dbdao.CompanyDBDAO;
import com.zm.dbdao.CouponDBDAO;
import com.zm.dbdao.CustomerDBDAO;

import java.sql.SQLException;
/*
Clients- נחשב כל מי שיכול להשתמש במערכת. שלושת סוגי ה Client
המנהל הראשי של כל המערכת  –  Administrator
כל אחת מהחברות שבמערכת – Company
כל אחד מהלקוחות שבמערכת – Customer
*/

public abstract class ClientFacade {
    protected CompanyDao companyDao;
    protected CustomerDao customerDao;
    protected CouponDao couponDao;

    public ClientFacade() {
        companyDao = new CompanyDBDAO();
        customerDao = new CustomerDBDAO();
        couponDao = new CouponDBDAO();
    }

    public abstract Boolean login(String email, String password) throws SQLException;
}
