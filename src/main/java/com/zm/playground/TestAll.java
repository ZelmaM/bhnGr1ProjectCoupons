/*
Creating by Zelma Milev
*/
package com.zm.playground;

import com.zm.beans.*;
import com.zm.dao.CouponDao;
import com.zm.dao.CustomerDao;
import com.zm.db.ConnectionPool;
import com.zm.dbdao.CouponDBDAO;
import com.zm.dbdao.CustomerDBDAO;
import com.zm.exceptions.CouponSystemException;
import com.zm.facades.*;
import com.zm.facades.LoginManager;
import com.zm.job.CouponExpirationDailyJob;
import com.zm.utils.ArtUtils;
import com.zm.utils.CreationUtils;
import com.zm.utils.PrintUtils;

import java.sql.SQLException;
import java.time.LocalDate;

import static com.zm.utils.DateUtils.convertToDataViaData;

public class TestAll {
    private static Coupon c;

    private static Company company;

    public static void main(String[] args) throws SQLException, CouponSystemException, InterruptedException {
        System.out.println("START");
        CreationUtils.CreateFoundation();

        PrintUtils.PrintTestOneHeading("START THREAD");
        Thread thread = new Thread(new CouponExpirationDailyJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("< THREAD STATE >" + thread.getState());

        PrintUtils.PrintTestOneHeading("ADMINISTRATOR LOG IN");
        PrintUtils.PrintTestTwoHeading("Wrong Administrator password");
        testAdministrator((AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin123", ClientType.ADMINISTRATOR));
        PrintUtils.PrintTestTwoHeading("Log in with correct parameters");
        testAdministrator((AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR));

        PrintUtils.PrintTestOneHeading("COMPANY LOG IN");
        PrintUtils.PrintTestTwoHeading("Non existent company log in");
        testCompanyFacade((CompanyFacade) LoginManager.getInstance().login("c5Email_notExist", "c5Password_notExist", ClientType.COMPANY));
        PrintUtils.PrintTestTwoHeading("Log in with correct parameters");
        testCompanyFacade((CompanyFacade) LoginManager.getInstance().login("c5Email", "c5Password", ClientType.COMPANY));

        PrintUtils.PrintTestTwoHeading("Non existent customer log in");
        testCustomerFacade((CustomerFacade) LoginManager.getInstance().login("c3Em_notExist", "c3Pass_notExist", ClientType.CUSTOMER));
        PrintUtils.PrintTestTwoHeading("Log in with correct parameters");
        testCustomerFacade((CustomerFacade) LoginManager.getInstance().login("c3Em", "c3Pass", ClientType.CUSTOMER));


        PrintUtils.PrintTestOneHeading("CLOSE ALL CONNECTIONS ");

        ConnectionPool.getInstance().closeAllConnections();
        System.out.println("< THREAD STATE >" + thread.getState());

        System.out.println("END");

    }

    public static void testAdministrator(AdminFacade adminFacade) throws CouponSystemException, SQLException {
        System.out.println("--- ADMINISTRATOR TEST STARTED ---");
        if (adminFacade == null) {
            System.out.println(ErrorMsg.ADMINISTRATOR_ACCESS_DENIED.getMsg());
            return;
        }
        System.out.println(ArtUtils.ADMIN_FACADE);
        //TODO Admin Facade companies - basis
        PrintUtils.PrintTestOneHeading(" ALL COMPANIES (BASIS)");
        adminFacade.getAllCompanies().forEach(System.out::println);

        PrintUtils.PrintTestOneHeading(" SHOW ONE COMPANY");

        int companyNum = 18;
        PrintUtils.PrintTestTwoHeading(" Details of one non existing company #" + companyNum);
        try {
            System.out.println(adminFacade.getOneCompany(companyNum));
        } catch (CouponSystemException e) {
            System.out.println("company #" + companyNum + " " + e.getMessage());
        }
        companyNum = 1;
        PrintUtils.PrintTestTwoHeading(" Details of one existing company #" + companyNum);
        try {
            company = adminFacade.getOneCompany(companyNum);
            System.out.println(company);

        } catch (CouponSystemException e) {
            System.out.println("company #" + companyNum + " " + e.getMessage());
        }
        PrintUtils.PrintTestOneHeading(" ADD COMPANIES ");
        //TODO Admin Facade companies - ADD
        PrintUtils.PrintTestTwoHeading(" Add existing company #" + companyNum);
        try {
            adminFacade.addCompany(company);
            System.out.println(company.toString() + " === Added successfully === ");
        } catch (CouponSystemException e) {
            System.out.println(company.toString() + " !!! NOT added !!!  <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading("Change the name and try to add ");
        Company company1 = new Company(company.getName().concat("new"), company.getEmail(), company.getPassword());
        try {
            adminFacade.addCompany(company1);
            System.out.println(company1.toString() + " === Added successfully === ");
        } catch (CouponSystemException e) {
            System.out.println(company1.toString() + " !!! NOT added !!!  <" + e.getMessage() + ">");
        }

        PrintUtils.PrintTestTwoHeading("Change the name&email and try to add ");
        company1 = new Company(company.getName().concat("new"), company.getEmail().concat("new"), company.getPassword());
        try {
            adminFacade.addCompany(company1);
            System.out.println(company1.toString() + " === Added successfully === ");
        } catch (CouponSystemException e) {
            System.out.println(company1.toString() + " !!! NOT added !!!  <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Add new company ");
        company = new Company("cA7Name", "cA7Email", "cA7Password");
        try {
            adminFacade.addCompany(company);
            System.out.println(company.toString() + "=== Added successfully ===");
        } catch (CouponSystemException e) {
            System.out.println(company.toString() + " !!! NOT ADDED !!!  <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Add new company with empty name");
        company = new Company("", "cA7Email", "cA7Password");
        try {
            adminFacade.addCompany(company);
            System.out.println(company.toString() + "=== Added successfully ===");
        } catch (CouponSystemException e) {
            System.out.println(company.toString() + " !!! NOT ADDED !!!  <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Add new company with empty email");
        company = new Company("cA7Name", "", "cA7Password");
        try {
            adminFacade.addCompany(company);
            System.out.println(company.toString() + "=== Added successfully ===");
        } catch (CouponSystemException e) {
            System.out.println(company.toString() + " !!! NOT ADDED !!!  <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" ALL COMPANIES AFTER ADD ");
        adminFacade.getAllCompanies().forEach(System.out::println);
        //TODO Admin Facade companies - UPDATE
        PrintUtils.PrintTestOneHeading(" UPDATE COMPANIES ");
        PrintUtils.PrintTestTwoHeading(" Update non existing company ");
        company.setId(18);
        try {
            adminFacade.updateCompany(company);
            System.out.println(company.toString() + " ===  Successful update === ");
        } catch (CouponSystemException e) {
            System.out.println(company.toString() + " !!! Unsuccessful update !!!  <" + e.getMessage() + ">");
        }
        companyNum = 6;
        PrintUtils.PrintTestTwoHeading(" get company #" + companyNum + " and try to change name");
        try {
            company = (adminFacade.getOneCompany(companyNum));

        } catch (CouponSystemException e) {
            System.out.println("company #" + companyNum + " " + e.getMessage());
        }
        company = adminFacade.getOneCompany(companyNum);
        company.setName(company.getName().concat("new***"));
        try {
            adminFacade.updateCompany(company);
            System.out.println(company.toString() + " ===  Successful update === ");
        } catch (CouponSystemException e) {
            System.out.println(company.toString() + " !!! Unsuccessful update !!!  <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" get company " + companyNum + " and try to change email to existing email  ");
        try {
            company = (adminFacade.getOneCompany(companyNum));

        } catch (CouponSystemException e) {
            System.out.println("company #" + companyNum + " " + e.getMessage());
        }
        company.setEmail("cA7Email");
        try {
            adminFacade.updateCompany(company);
            System.out.println(company.toString() + " ===  Successful update === ");
        } catch (CouponSystemException e) {
            System.out.println(company.toString() + " !!! Unsuccessful update !!!  <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" get company " + companyNum + " and try to change email to non existing email  ");
        company.setEmail(company.getEmail().concat("*12*").toUpperCase());
        try {
            adminFacade.updateCompany(company);
            System.out.println(company.toString() + " ===  Successful update === ");
        } catch (CouponSystemException e) {
            System.out.println(company.toString() + " !!! Unsuccessful update !!!  <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" ALL COMPANIES AFTER UPDATE");
        adminFacade.getAllCompanies().forEach(System.out::println);
        //TODO Admin Facade companies - DELETE
        PrintUtils.PrintTestOneHeading(" DELETE COMPANIES");
        System.out.println("NUMBER OF COMPANIES  " + adminFacade.getAllCompanies().size());

        companyNum = 15;
        PrintUtils.PrintTestTwoHeading(String.format(" Delete non existing company #%d", companyNum));
        try {
            adminFacade.deleteCompany(companyNum);
            System.out.println("company #" + companyNum + " --- Successful delete --- ");
        } catch (CouponSystemException e) {
            System.out.println("company #" + companyNum + "!!! Unsuccessful delete !!! <" + e.getMessage() + ">");
        }
        System.out.println("NUMBER OF COMPANIES  " + adminFacade.getAllCompanies().size());

        companyNum = 1;
        PrintUtils.PrintTestTwoHeading(String.format(" Delete existing company #%d", companyNum));
        try {
            adminFacade.deleteCompany(companyNum);
            System.out.println("company #" + companyNum + " --- Successful delete --- ");
        } catch (CouponSystemException e) {
            System.out.println("company #" + companyNum + "!!! Unsuccessful delete !!! <" + e.getMessage() + ">");
        }
        System.out.println("NUMBER OF COMPANIES  " + adminFacade.getAllCompanies().size());

        PrintUtils.PrintTestTwoHeading(" ALL COMPANIES AFTER DELETE");
        adminFacade.getAllCompanies().forEach(System.out::println);

        PrintUtils.PrintTestTwoHeading(" PRINT ALL COMPANY'S COUPONS ");
        adminFacade.getAllCompanies().forEach(company -> {
//            PrintUtils.PrintTestTwoHeading("--- Company #" + company.getId() + " coupons ---");
            try {
                company.getCoupons().forEach(System.out::println);
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
        });

        PrintUtils.PrintTestTwoHeading(" PRINT ALL CUSTOMER'S COUPONS ");
        adminFacade.getAllCustomers().forEach(customer -> {
//            PrintUtils.PrintTestTwoHeading(" Customer #" + customer.getId() + " coupons ");
            try {
                customer.getCoupons().forEach(System.out::println);
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
        });
        //TODO Admin Facade customers - basis
        PrintUtils.PrintTestOneHeading(" ALL CUSTOMERS BASIS ");
        adminFacade.getAllCustomers().forEach(System.out::println);
        //TODO Admin Facade customers - ADD
        PrintUtils.PrintTestOneHeading(" ADD CUSTOMER");
        PrintUtils.PrintTestTwoHeading(" Add customer with email unique");
        Customer customer;
        customer = new Customer("c4fName", "c4lName", "c4EmNew", "c4Pass");
        try {
            adminFacade.addCustomer(customer);
            System.out.println(customer.toString() + "  Added successfully");
        } catch (CouponSystemException e) {
            System.out.println(customer.toString() + " is not added <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Add customer with the same email ");

        customer.setFirstName(customer.getFirstName().concat("new"));
        customer.setLastName(customer.getLastName().concat("NEW"));
        customer.setPassword(customer.getPassword().concat("NeW"));
        try {
            adminFacade.addCustomer(customer);
            System.out.println(customer.toString() + "  Added successfully");
        } catch (CouponSystemException e) {
            System.out.println(customer.toString() + " is not added <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Change the email and add ");
        customer.setEmail(customer.getEmail().concat("$NeW"));
        try {
            adminFacade.addCustomer(customer);
            System.out.println(customer.toString() + "  Added successfully");
        } catch (CouponSystemException e) {
            System.out.println(customer.toString() + " is not added <" + e.getMessage() + ">");
        }

        PrintUtils.PrintTestTwoHeading("ALL CUSTOMERS AFTER ADD");
        adminFacade.getAllCustomers().forEach(System.out::println);

//TODO Admin Facade customers - UPDATE

        PrintUtils.PrintTestOneHeading(" UPDATE CUSTOMERS");
        PrintUtils.PrintTestTwoHeading(" Update not existing customer");

        int customerNum = 17;
        customer = new Customer(customerNum, "c4fNameU", "c4lNameU", "c4Emn", "c4Pass");
        try {
            adminFacade.updateCustomer(customer);
            System.out.println(customer.toString() + "  Updated successfully");
        } catch (CouponSystemException e) {
            System.out.println(customer.toString() + " is not updated <" + e.getMessage() + ">");
        }
        customerNum = 1;
        PrintUtils.PrintTestTwoHeading("get one customer #" + customerNum + " and try to change a email to existing email");
        try {
            customer = adminFacade.getOneCustomer(customerNum);
            customer.setEmail(adminFacade.getOneCustomer(customerNum + 1).getEmail());
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
        try {
            adminFacade.updateCustomer(customer);
            System.out.println(customer.toString() + "  Updated successfully");
        } catch (CouponSystemException e) {
            System.out.println(customer.toString() + " is not updated <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading("Change a email to unique email");
        try {
            customer.setEmail(customer.getEmail().concat("**12**"));
            customer.setPassword(customer.getPassword().concat("**new**"));
            adminFacade.updateCustomer(customer);
            System.out.println(customer.toString() + "  Updated successfully");
        } catch (CouponSystemException e) {
            System.out.println(customer.toString() + " is not updated <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading("ALL CUSTOMERS AFTER UPDATE");
        adminFacade.getAllCustomers().forEach(System.out::println);
        //TODO Admin facade Show one CUSTOMER
        PrintUtils.PrintTestOneHeading(" SHOW ONE CUSTOMER");
        customerNum = 1;
        PrintUtils.PrintTestTwoHeading(" Show exist customer #" + customerNum);
        try {
            System.out.println(adminFacade.getOneCustomer(customerNum));
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
        customerNum = 31;
        PrintUtils.PrintTestTwoHeading(" Show not exist customer #" + customerNum);
        try {
            System.out.println(adminFacade.getOneCustomer(customerNum));
        } catch (SQLException | CouponSystemException e) {
            System.out.println(e.getMessage());
        }
//TODO Admin Facade customers - DELETE
        PrintUtils.PrintTestOneHeading(" DELETE CUSTOMERS");
        customerNum = 6;
        PrintUtils.PrintTestTwoHeading(" Delete exist customer #" + customerNum);
        try {
            adminFacade.deleteCustomer(customerNum);
            System.out.println("Customer #" + customerNum + " Successfully deleted");
        } catch (SQLException | CouponSystemException e) {
            System.out.println("Customer #" + customerNum + " Unsuccessful delete <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Delete the same customer #" + customerNum);

        try {
            adminFacade.deleteCustomer(customerNum);
            System.out.println("Customer #" + customerNum + " Successfully deleted");
        } catch (SQLException | CouponSystemException e) {
            System.out.println("Customer #" + customerNum + " Unsuccessful delete <" + e.getMessage() + ">");
        }

        PrintUtils.PrintTestTwoHeading("ALL CUSTOMERS AFTER DELETE");
        adminFacade.getAllCustomers().forEach(System.out::println);
        System.out.println("--- ADMINISTRATOR  TEST ENDED ---");
    }

    public static void testCompanyFacade(CompanyFacade companyFacade) throws SQLException {
        System.out.println("--- COMPANY FACADE TEST STARTED ---");

        if (companyFacade == null) {
            System.out.println(ErrorMsg.COMPANY_ACCESS_DENIED.getMsg());
            return;
        }

        System.out.println(ArtUtils.COMPANY_FACADE);
        //TODO Company Facade - basis details
        PrintUtils.PrintTestOneHeading(" COMPANY #" + companyFacade.getCompanyID() + " DETAILS ");
        System.out.println(companyFacade.getCompanyDetails());
        PrintUtils.PrintTestOneHeading(" ALL COMPANY COUPONS BASIS ");
        try {
            companyFacade.getCompanyCoupons().forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println("<" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestOneHeading(" ALL COMPANY'S COUPONS PER CATEGORIES OR PRICE ");
        PrintUtils.PrintTestTwoHeading(" All coupons per category 3 (ELECTRICITY)");

        try {
            companyFacade.getCompanyCoupons(Category.ELECTRICITY).forEach(System.out::println);

        } catch (CouponSystemException e) {
            System.out.println("<" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" All coupons per category 1 (FOOD)");
        try {
            companyFacade.getCompanyCoupons(Category.FOOD).forEach(System.out::println);

        } catch (CouponSystemException e) {
            System.out.println("<" + e.getMessage() + ">");
        }

        PrintUtils.PrintTestTwoHeading(" All Company's coupons price less than 500.70");
        try {
            companyFacade.getCompanyCoupons(500.70).forEach(System.out::println);

        } catch (CouponSystemException e) {
            System.out.println("<" + e.getMessage() + ">");
        }
        //TODO Company Facade -ADD COMPANY COUPONS
        PrintUtils.PrintTestOneHeading(" ADD COMPANY COUPONS ");
        PrintUtils.PrintTestTwoHeading(" Add coupon with the same title ");

        c = new Coupon(1, 5, 5, "coupon5T", "coupon5D", convertToDataViaData(LocalDate.now()),
                convertToDataViaData(LocalDate.now().plusYears(1)), 700, 600.44, "555555555555555");
        c.setImage("new image");
        c.setPrice(c.getPrice() * 0.2);
        try {
            companyFacade.addCoupon(c);
        } catch (CouponSystemException e) {
            System.out.println(c.toString() + " is not added <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Add coupon to another company ");
        c = new Coupon(1, 3, 2, "coupon5tNew0", "coupon5D", convertToDataViaData(LocalDate.now()),
                convertToDataViaData(LocalDate.now().plusYears(1)), 700, 600.44, "555555555555555");
        try {
            companyFacade.addCoupon(c);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(c.toString() + " is not added <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Add coupons ");
        c = (new Coupon(1, 5, 1, "coupon5tNew11", "coupon5D", convertToDataViaData(LocalDate.now()),
                convertToDataViaData(LocalDate.now().plusYears(1)), 0, 600.44, "555555555555555"));
        try {
            companyFacade.addCoupon(c);
        } catch (CouponSystemException e) {
            System.out.println(c.toString() + " is not added <" + e.getMessage() + ">");
        }

        c = new Coupon(1, 5, 1, "T", "coupon5D", convertToDataViaData(LocalDate.now().minusDays(11)),
                convertToDataViaData(LocalDate.now().minusDays(1)), 700, 600.44, "pag tokef1");
        for (int i = 1; i < 7; i++) {
            c.setTitle(c.getTitle().concat("_" + i));
            c.setCategoryId(i);
            try {
                companyFacade.addCoupon(c);
            } catch (CouponSystemException e) {
                System.out.println(c.toString() + " is not added <" + e.getMessage() + ">");
            }
        }

        PrintUtils.PrintTestTwoHeading(" All Company's coupons AFTER ADD");
        try {
            companyFacade.getCompanyCoupons().forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
        PrintUtils.PrintTestTwoHeading(" All Company's coupons per FOOD-1 (after add)");
        try {
            companyFacade.getCompanyCoupons(Category.FOOD).forEach(System.out::println);

        } catch (CouponSystemException e) {
            System.out.println("<" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" All Company's coupons price less than 600.11 (after added)");
        try {
            companyFacade.getCompanyCoupons(600.11).forEach(System.out::println);

        } catch (CouponSystemException e) {
            System.out.println("<" + e.getMessage() + ">");
        }
        //TODO Company Facade -DELETE COMPANY COUPONS
        PrintUtils.PrintTestOneHeading(" DELETE COMPANY'S COUPONS");
        int couponNum = 20;
        PrintUtils.PrintTestTwoHeading(" delete not existing coupon # " + couponNum);
        try {
            companyFacade.deleteCoupon(couponNum);
        } catch (CouponSystemException e) {
            System.out.println("Coupon #" + couponNum + " is not deleted<" + e.getMessage() + ">");
        }
        couponNum = 8;
        PrintUtils.PrintTestTwoHeading(" delete existing coupon # " + couponNum);
        try {
            companyFacade.deleteCoupon(couponNum);
        } catch (CouponSystemException e) {
            System.out.println("Coupon #" + couponNum + " is not deleted <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" All Company's coupons AFTER DELETE");
        try {
            companyFacade.getCompanyCoupons().forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println("<" + e.getMessage() + ">");
        }

        //TODO Company Facade -UPDATE COMPANY COUPONS
        PrintUtils.PrintTestOneHeading(" UPDATE COMPANY COUPONS ");
        PrintUtils.PrintTestTwoHeading(" Update not exist coupon (coupon ID) ");

        c = new Coupon(25, 5, 2, "coupon5tNew0", "coupon5D", convertToDataViaData(LocalDate.now()),
                convertToDataViaData(LocalDate.now().plusYears(1)), 700, 600.44, "555555555555555");
        try {
            companyFacade.updateCoupon(c);
        } catch (CouponSystemException | SQLException e) {
            System.out.println(c.toString() + " is not updated <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Update another company coupon ");
        c = new Coupon(9, 3, 2, "coupon5tNew0", "coupon5D", convertToDataViaData(LocalDate.now()),
                convertToDataViaData(LocalDate.now().plusYears(1)), 900, 609.44, "555555555555555");
        try {
            companyFacade.updateCoupon(c);
        } catch (CouponSystemException e) {
            System.out.println(c.toString() + " is not updated <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Update company coupon #" + c.getId());
        c.setCompanyId(companyFacade.getCompanyID());
        try {
            companyFacade.updateCoupon(c);

        } catch (CouponSystemException e) {
            System.out.println(c.toString() + " is not updated <" + e.getMessage() + ">");
        }

        PrintUtils.PrintTestTwoHeading(" All Company's coupons AFTER UPDATE");
        try {
            companyFacade.getCompanyCoupons().forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println("<" + e.getMessage() + ">");
        }

        System.out.println("--- COMPANY #" + companyFacade.getCompanyID() + "  TEST ENDED ---");
    }

    private static void testCustomerFacade(CustomerFacade customerFacade) throws SQLException {
        System.out.println("--- CUSTOMER FACADE TEST STARTED---");

        if (customerFacade == null) {
            System.out.println(ErrorMsg.CUSTOMER_ACCESS_DENIED.getMsg());
            return;
        }
        //TODO Customer Facade -basic details
        System.out.println(ArtUtils.CUSTOMER_FACADE);
        PrintUtils.PrintTestOneHeading(" CUSTOMER #" + customerFacade.getCustomerID() + " DETAILS");
        System.out.println(customerFacade.getCustomerDetails());

        PrintUtils.PrintTestOneHeading(" ALL EXISTING COUPONS ");
        CouponDao couponDao = new CouponDBDAO();
//        couponDao.getAllCoupons().forEach(coupon -> System.out.println(coupon.toString()));
        couponDao.getAllCoupons().forEach(System.out::println);

        PrintUtils.PrintTestOneHeading(" ALL COUPONS PER CUSTOMERS ");
        CustomerDao customerDao = new CustomerDBDAO();
        customerDao.getAllCustomers().forEach(customer -> {
            PrintUtils.PrintTestTwoHeading(" Customer #" + customer.getId() + " coupons ");
            try {
                customer.getCoupons().forEach(System.out::println);
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
        });
        PrintUtils.PrintTestOneHeading("ALL CUSTOMER'S COUPONS");
        try {
            customerFacade.getCustomerCoupons().forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
        PrintUtils.PrintTestTwoHeading(" ALL CUSTOMER'S COUPONS PER CATEGORY 3 ELECTRONIC ");

        try {
            customerFacade.getCustomerCoupons(Category.ELECTRONIC).forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }

        PrintUtils.PrintTestTwoHeading(" ALL CUSTOMER'S COUPONS WITH PRICE LESS THAN 500.00 ");
        try {
            customerFacade.getCustomerCoupons(500.00).forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
        //TODO Customer Facade -ADD CUSTOMER'S COUPONS
        PrintUtils.PrintTestOneHeading(" ADD CUSTOMER'S COUPONS ");
        PrintUtils.PrintTestTwoHeading(" Add existing coupons #4,#5 ");
        c = new Coupon(4, 5, 2, "coupon5tNew", "coupon5u22p", convertToDataViaData(LocalDate.now()),
                convertToDataViaData(LocalDate.now().plusYears(1)), 750, 600.44, "555555555555555");
        try {
            customerFacade.purchaseCoupon(c);
            System.out.println("Successful Purchase Coupon #" + c.getId());
        } catch (CouponSystemException | SQLException e) {
            System.out.println(c.toString() + "Unsuccessful Purchase Coupon <" + e.getMessage() + ">");
        }
        c = new Coupon(5, 5, 2, "coupon5tNew", "coupon5u22p", convertToDataViaData(LocalDate.now()),
                convertToDataViaData(LocalDate.now().plusYears(1)), 750, 600.44, "555555555555555");
        try {
            customerFacade.purchaseCoupon(c);
            System.out.println("Successful Purchase Coupon #" + c.getId());

        } catch (CouponSystemException | SQLException e) {
            System.out.println(c.toString() + "Unsuccessful Purchase Coupon <" + e.getMessage() + ">");
        }
        PrintUtils.PrintTestTwoHeading(" Add all coupons from#1 to #20 ");

        for (int i = 1; i < 20; i++) {
            try {
                customerFacade.purchaseCoupon(i);
                System.out.println("Successful Purchase Coupon #" + i);
            } catch (CouponSystemException | SQLException e) {
                System.out.println("Unsuccessful Purchase Coupon #" + i + "<" + e.getMessage() + ">");
            }
        }
//TODO Customer Facade - details after purchase
        PrintUtils.PrintTestOneHeading(" All CUSTOMER'S coupons AFTER ADD");

        try {
            customerFacade.getCustomerCoupons().forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
        PrintUtils.PrintTestTwoHeading(" All CUSTOMER'S coupons per category 3 ( ELECTRONIC) ");

        try {
            customerFacade.getCustomerCoupons(Category.ELECTRONIC).forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
        PrintUtils.PrintTestTwoHeading(" All CUSTOMER'S coupons per category 5 (VACATION)");

        try {
            customerFacade.getCustomerCoupons(Category.VACATION).forEach(System.out::println);

        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }
        PrintUtils.PrintTestTwoHeading(" All CUSTOMER'S coupons price less than max 600.00");
        try {
            customerFacade.getCustomerCoupons(600.00).forEach(System.out::println);
        } catch (CouponSystemException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("--- CUSTOMER #" + customerFacade.getCustomerID() + " TEST ENDED ---");

    }
}