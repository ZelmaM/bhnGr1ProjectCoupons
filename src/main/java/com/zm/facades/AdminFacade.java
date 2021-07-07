/*
Creating by Zelma Milev
*/
package com.zm.facades;

import com.zm.beans.Company;
import com.zm.beans.Coupon;
import com.zm.beans.Customer;
import com.zm.beans.ErrorMsg;
import com.zm.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.List;
/*
Administrator מכילה את הלוגיקה העסקית של – AdminFacade
*/

public class AdminFacade extends ClientFacade {

    @Override
    public Boolean login(String email, String password) {
        return (email.equals("admin@admin.com") && password.equals("admin"));
    }

    public void addCompany(Company company) throws CouponSystemException, SQLException {
        if (company.getName().equals("")) {
            throw new CouponSystemException(ErrorMsg.COMPANY_NAME_EMPTY);
        }
        if (company.getEmail().equals("")) {
            throw new CouponSystemException(ErrorMsg.COMPANY_EMAIL_EMPTY);
        }
        if (this.companyDao.isNameOrEmailExist(company.getName(),"")) {
            throw new CouponSystemException(ErrorMsg.COMPANY_NAME_EXIST);
        }
        if (this.companyDao.isNameOrEmailExist("", company.getEmail())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_EMAIL_EXIST);
        }
        this.companyDao.addCompany(company);
    }

    public void updateCompany(Company company) throws SQLException, CouponSystemException {
        if (company.getName().equals("")) {
            throw new CouponSystemException(ErrorMsg.COMPANY_NAME_EMPTY);
        }
        if (company.getEmail().equals("")) {
            throw new CouponSystemException(ErrorMsg.COMPANY_EMAIL_EMPTY);
        } if (!this.companyDao.isCompanyExists(company.getId())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_ID_NOT_EXIST_UPDATE);
        }
        if (!getOneCompany(company.getId()).getName().equalsIgnoreCase(company.getName())) {
            throw new CouponSystemException(ErrorMsg.UPDATE_COMPANY_NAME);
        }
        if ((this.companyDao.isNameOrEmailExist("", company.getEmail()))&&
        (!getOneCompany(company.getId()).getEmail().equals(company.getEmail())))
        {
            throw new CouponSystemException(ErrorMsg.COMPANY_EMAIL_EXIST);
        }
        this.companyDao.updateCompany(company);
    }

    public void deleteCompany(int CompanyID) throws SQLException, CouponSystemException {
        if (!this.companyDao.isCompanyExists(CompanyID)) {
            throw new CouponSystemException(ErrorMsg.COMPANY_ID_NOT_EXIST_DELETE);
        }
        List<Coupon> couponList = couponDao.getAllCompanyCoupons(CompanyID);
        for (Coupon c : couponList) {
            couponDao.deleteCouponPurchaseCouponID(c.getId());
            couponDao.deleteCoupon(c.getId());
        }
            this.companyDao.deleteCompany(CompanyID);
    }

    public Company getOneCompany(int CompanyID) throws SQLException, CouponSystemException {
        if (!this.companyDao.isCompanyExists(CompanyID)) {
            throw new CouponSystemException(ErrorMsg.COMPANY_ID_NOT_EXIST);
        }
        return this.companyDao.getOneCompany(CompanyID);

    }

    public List<Company> getAllCompanies() throws SQLException {
        return this.companyDao.getAllCompanies();
    }

    public void addCustomer(Customer customer) throws CouponSystemException, SQLException {
        if (this.customerDao.isEmailExist(customer.getEmail())) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_EMAIL_EXIST);
        }
            this.customerDao.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws SQLException, CouponSystemException {
        if (!this.customerDao.isCustomerExists(customer.getId())) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_ID_NOT_EXIST_UPDATE);
        }
        if (this.customerDao.isEmailExist(customer.getEmail())) {
            throw new CouponSystemException(ErrorMsg.UPDATE_CUSTOMER_EMAIL);
        }
        this.customerDao.updateCustomer(customer);

    }

    public void deleteCustomer(int CustomerID) throws SQLException, CouponSystemException {
        if (!this.customerDao.isCustomerExists(CustomerID)) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_ID_NOT_EXIST_DELETE);
        }
            this.couponDao.deleteCouponPurchaseCustomerID(CustomerID);
            this.customerDao.deleteCustomer(CustomerID);


    }

    public Customer getOneCustomer(int CustomerID) throws SQLException, CouponSystemException {
        if (!this.customerDao.isCustomerExists(CustomerID)) {
            throw  new CouponSystemException(ErrorMsg.CUSTOMER_ID_NOT_EXIST);
        }   return this.customerDao.getOneCustomer(CustomerID);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return this.customerDao.getAllCustomers();
    }
}
