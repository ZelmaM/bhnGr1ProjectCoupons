/*
Creating by Zelma Milev
*/
package com.zm.utils;

import com.zm.beans.Company;
import com.zm.beans.Coupon;
import com.zm.beans.Customer;
import com.zm.dao.CompanyDao;
import com.zm.dao.CouponDao;
import com.zm.dao.CustomerDao;
import com.zm.db.DatabaseManager;
import com.zm.dbdao.CompanyDBDAO;
import com.zm.dbdao.CouponDBDAO;
import com.zm.dbdao.CustomerDBDAO;
import com.zm.exceptions.CouponSystemException;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.zm.utils.DateUtils.convertToDataViaData;
/*
מחלקת לביצוע ובדיקה פעילות כלליות על טבלאות במסד הנתונים
מחלקת  לא מבצעות את הלוגיקה  הקשורה לאפליקציה אלא אך ורק פעולות
 ((Create/Read/Update/Delete) CRUD
כלליות
*/
public class CreationUtils {
    public static void CreateFoundation() throws SQLException {
        System.out.println("START CREATE FOUNDATION ");

        DatabaseManager.dropAndCreate();

        System.out.println(ArtUtils.CUSTOMER);
//TODO CUSTOMER
        CustomerDao customerDao = new CustomerDBDAO();
        PrintUtils.PrintTestOneHeading("List of customers (empty table)");
        customerDao.getAllCustomers().forEach(System.out::println);

//TODO CUSTOMER - insert

        System.out.println(ArtUtils.INSERT);
        customerDao.addCustomer(new Customer(1, "c1fName", "c1lName", "c1Em", "c1Pass"));
        customerDao.addCustomer(new Customer(2, "c2fName", "c2lName", "c2Em", "c2Pass"));
        customerDao.addCustomer(new Customer(3, "c3fName", "c3lName", "c3Em", "c3Pass"));
        customerDao.addCustomer(new Customer(4, "c4fName", "c4lName", "c4Em", "c4Pass"));
        customerDao.addCustomer(new Customer(5, "c5fName", "c5lName", "c5Em", "c5Pass"));

        PrintUtils.PrintTestOneHeading("List of customers after adding");
        customerDao.getAllCustomers().forEach(System.out::println);

//TODO CUSTOMER update

        System.out.println(ArtUtils.UPDATE);
        Customer cUpdate = customerDao.getOneCustomer(2);
        cUpdate.setFirstName("c2FUpd");
        cUpdate.setLastName("c2LUpd");
        cUpdate.setPassword("c2PassUpd");
        customerDao.updateCustomer(cUpdate);

        PrintUtils.PrintTestOneHeading("Get Customer #2 after update");
        System.out.println(customerDao.getOneCustomer(cUpdate.getId()));

//TODO CUSTOMER get one

        System.out.println(ArtUtils.GET_ONE);
        PrintUtils.PrintTestOneHeading("Get Customer #1 ,#4");

        System.out.println(customerDao.getOneCustomer(1));
        System.out.println(customerDao.getOneCustomer(4));

//TODO CUSTOMER delete

        System.out.println(ArtUtils.DELETE);
        PrintUtils.PrintTestOneHeading("Delete Customer #4");
        customerDao.deleteCustomer(4);

//TODO CUSTOMER list all

        PrintUtils.PrintTestOneHeading("List of customers after basic operations");
        System.out.println(ArtUtils.GET_ALL);
        customerDao.getAllCustomers().forEach(System.out::println);
//TODO COMPANY
        System.out.println(ArtUtils.COMPANY);
        CompanyDao companyDao = new CompanyDBDAO();
//TODO COMPANY list empty table

        PrintUtils.PrintTestOneHeading("List of companies (empty table)");
        companyDao.getAllCompanies().forEach(System.out::println);
//TODO COMPANY insert

        System.out.println(ArtUtils.INSERT);
        companyDao.addCompany(new Company(1, "c1Name", "c1Email", "c1Password"));
        companyDao.addCompany(new Company(2, "c2Name", "c2Email", "c2Password"));
        companyDao.addCompany(new Company(3, "c3Name", "c3Email", "c3Password"));
        companyDao.addCompany(new Company(4, "c4Name", "c4Email", "c4Password"));
        companyDao.addCompany(new Company(5, "c5Name", "c5Email", "c5Password"));

        PrintUtils.PrintTestOneHeading("List of companies after adding");
        companyDao.getAllCompanies().forEach(System.out::println);
//TODO COMPANY update

        System.out.println(ArtUtils.UPDATE);
        Company cUpd = companyDao.getOneCompany(2);
        cUpd.setName("c2Upd");
        cUpd.setPassword("c2PasswordUpd");
        companyDao.updateCompany(cUpd);

        PrintUtils.PrintTestOneHeading("Show company #2 after update");
        System.out.println(companyDao.getOneCompany(cUpd.getId()));

//TODO COMPANY get one

        System.out.println(ArtUtils.GET_ONE);

//TODO COMPANY delete

        System.out.println(ArtUtils.DELETE);
        PrintUtils.PrintTestOneHeading("Delete company #4 ");
        companyDao.deleteCompany(4);

//TODO COMPANY list all

        System.out.println(ArtUtils.GET_ALL);
        PrintUtils.PrintTestOneHeading("List of companies after basic operations");
        companyDao.getAllCompanies().forEach(System.out::println);

        PrintUtils.PrintTestOneHeading("PRINT ALL COMPANIES WITH THEIR COUPONS (BEFORE ADD COUPONS)");
        companyDao.getAllCompanies().forEach(company -> {
            PrintUtils.PrintTestTwoHeading("--- Company #" + company.getId() + " coupons ---");
            try {
                company.getCoupons().forEach(System.out::println);
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
        });

//TODO COUPONS

        System.out.println(ArtUtils.COUPON);
        CouponDao couponDao = new CouponDBDAO();

//TODO COUPONS list empty table

        PrintUtils.PrintTestOneHeading("List of coupons (empty table)");
        try {
            couponDao.getAllCoupons().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//TODO COUPONS insert

        System.out.println(ArtUtils.INSERT);
        try {
            couponDao.addCoupon(new Coupon(1, 1, 1, "coupon1T", "coupon1D", convertToDataViaData(LocalDate.of(2021, 1, 1)),
                    convertToDataViaData(LocalDate.of(2022, 1, 1)), 100, 100.11, "11111111111111111"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCoupon(new Coupon(1, 1, 2, "coupon1T", "coupon1D", convertToDataViaData(LocalDate.of(2021, 1, 1)),
                    convertToDataViaData(LocalDate.of(2021, 6, 1)), 100, 100.11, "11111111111111111"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCoupon(new Coupon(1, 1, 3, "coupon1T", "coupon1D", convertToDataViaData(LocalDate.of(2021, 1, 1)),
                    convertToDataViaData(LocalDate.of(2021, 5, 1)), 100, 100.11, "11111111111111111"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCoupon(new Coupon(2, 2, 2, "coupon2T", "coupon2D", convertToDataViaData(LocalDate.of(2021, 2, 2)),
                    convertToDataViaData(LocalDate.of(2022, 2, 2)), 200, 200.22, "22222222222222222"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCoupon(new Coupon(3, 3, 3, "coupon3T", "coupon3D", convertToDataViaData(LocalDate.of(2021, 3, 3)),
                    convertToDataViaData(LocalDate.of(2022, 3, 3)), 300, 300.33, "33333333333333333"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCoupon(new Coupon(4, 5, 4, "coupon4T", "coupon4D", convertToDataViaData(LocalDate.now()),
                    convertToDataViaData(LocalDate.of(2021, 4, 4)), 400, 400.44, "44444444444444444"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCoupon(new Coupon(5, 5, 5, "coupon5T", "coupon5D", convertToDataViaData(LocalDate.now()),
                    convertToDataViaData(LocalDate.now().plusYears(1)), 500, 500.44, "555555555555555"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCoupon(new Coupon(6, 5, 3, "coupon6T", "coupon6D", Date.valueOf(LocalDate.now()),
                    Date.valueOf(LocalDate.now().plusDays(100)), 653, 500.66, "666666655555555333333666"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCoupon(new Coupon(7, 5, 2, "coupon7T", "coupon7D", convertToDataViaData(LocalDate.now()),
                    convertToDataViaData(LocalDate.now().plusDays(200)), 752, 500.77, "7777777555552222"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        PrintUtils.PrintTestOneHeading("List of coupons after adding");
        try {
            couponDao.getAllCoupons().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//TODO COUPONS update

        System.out.println(ArtUtils.UPDATE);
        PrintUtils.PrintTestOneHeading("Show coupon #2 after update");
        Coupon couponUpdate = null;
        try {
            couponUpdate = couponDao.getOneCoupon(2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (couponUpdate != null) {
            couponUpdate.setTitle("coupon2TUpd");
            couponUpdate.setDescription("coupon2DUpd");
            couponUpdate.setStartDate(convertToDataViaData(LocalDate.now()));
            couponUpdate.setEndDate(convertToDataViaData(LocalDate.now().plusMonths(11)));

            try {
                couponDao.updateCoupon(couponUpdate);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                System.out.println(couponDao.getOneCoupon(couponUpdate.getId()));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        //TODO COUPONS get one

        System.out.println(ArtUtils.GET_ONE);
        PrintUtils.PrintTestOneHeading("Show coupon #1 ");
        try {
            System.out.println(couponDao.getOneCoupon(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//TODO COUPONS delete

        System.out.println(ArtUtils.DELETE);
        PrintUtils.PrintTestOneHeading("Delete coupon #1 ");
        try {
            couponDao.deleteCoupon(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //TODO COUPONS get all

        System.out.println(ArtUtils.GET_ALL);
        PrintUtils.PrintTestOneHeading("List of coupons after basic operation");
        try {
            couponDao.getAllCoupons().forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//TODO ALL COMPANIES --> COUPONS
        PrintUtils.PrintTestOneHeading(" PRINT ALL COMPANIES WITH THEIR COUPONS ");
        companyDao.getAllCompanies().forEach(company -> {
            PrintUtils.PrintTestTwoHeading("--- Company #" + company.getId() + " coupons ---");
            try {
                company.getCoupons().forEach(System.out::println);
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
        });
//TODO CUSTOMER -->COUPONS
        PrintUtils.PrintTestOneHeading(" PRINT ALL CUSTOMER'S COUPONS BEFORE COUPON PURCHASE ");
        customerDao.getAllCustomers().forEach(customer -> {
            PrintUtils.PrintTestTwoHeading(" Customer #" + customer.getId() + " coupons ");
            try {
                customer.getCoupons().forEach(System.out::println);
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
        });

        PrintUtils.PrintTestOneHeading("PURCHASE COUPON");
        try {
            couponDao.addCouponPurchase(1, 2);
            System.out.println("successful purchase  coupon 2 to customer 1");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCouponPurchase(1, 3);
            System.out.println("successful purchase  coupon 3 to customer 1");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCouponPurchase(1, 5);
            System.out.println("successful purchase  coupon 5 to customer 1");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCouponPurchase(2, 2);
            System.out.println("successful purchase  coupon 2 to customer 2");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCouponPurchase(3, 2);
            System.out.println("successful purchase  coupon 2 to customer 3");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCouponPurchase(3, 3);
            System.out.println("successful purchase  coupon 3 to customer 3");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            couponDao.addCouponPurchase(5, 5);
            System.out.println("successful purchase  coupon 5 to customer 5");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        PrintUtils.PrintTestOneHeading(" PRINT ALL CUSTOMER'S COUPONS BEFORE DELETE ");
        customerDao.getAllCustomers().forEach(customer -> {
            PrintUtils.PrintTestTwoHeading(" Customer #" + customer.getId() + " coupons -");
            try {
                customer.getCoupons().forEach(System.out::println);
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
        });

        PrintUtils.PrintTestOneHeading(" DELETE CUSTOMER #3 COUPON #2");
        try {
            couponDao.deleteCouponPurchase(3, 2);
            System.out.println("successful delete  coupon 2 from customer 3");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        PrintUtils.PrintTestOneHeading(" PRINT ALL CUSTOMER'S COUPONS ");
        customerDao.getAllCustomers().forEach(customer -> {
            PrintUtils.PrintTestTwoHeading(" Customer #" + customer.getId() + " coupons ");
            try {
                customer.getCoupons().forEach(System.out::println);
            } catch (SQLException | CouponSystemException e) {
                System.out.println(e.getMessage());
            }
        });

        System.out.println("END CREATE FOUNDATION ");

    }

}
