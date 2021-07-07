package com.zm.beans;

public enum ErrorMsg {
    ADMINISTRATOR_ACCESS_DENIED("You have not ADMINISTRATOR access privileges"),

    COMPANY_ACCESS_DENIED("COMPANY ACCESS DENIED"),
    COMPANY_EMAIL_EXIST("Cannot add company with exiting company email."),
    COMPANY_EMAIL_EMPTY("Cannot add company with empty company email."),
    COMPANY_ID_NOT_EXIST("Sorry this company ID not exist."),
    COMPANY_ID_NOT_EXIST_DELETE("Cannot delete company with exiting non exist id."),
    COMPANY_ID_NOT_EXIST_UPDATE("Cannot update company with exiting non exist id."),
    COMPANY_NAME_EXIST("Cannot add company with exiting company name."),
    COMPANY_NAME_EMPTY("Cannot add company with empty company name."),
    COMPANY_WITHOUT_CATEGORIES_COUPONS("The company hasn't coupons in this category"),
    COMPANY_WITHOUT_COUPONS("The company hasn't  coupons "),

    COUPON_DOUBLE_PURCHASE("You already purchase this coupon."),
    COUPON_EXHAUSTED("The quantity of the  COUPON is exhausted."),
    COUPON_ID_NOT_EXIST("This coupon ID not exist."),
    COUPON_TIME_EXPIRED("The promotion time expired."),
    COUPON_WRONG_COMPANY_ID_ADD("Cannot add coupon with another company ID"),
    COUPON_WRONG_COMPANY_ID_UPDATE("Cannot update company ID"),
    COUPON_WRONG_TITLE("Coupons with the same title exist."),

    CUSTOMER_ACCESS_DENIED("CUSTOMER ACCESS DENIED"),
    CUSTOMER_EMAIL_EXIST("Cannot add customer with exiting customer email."),
    CUSTOMER_ID_NOT_EXIST("This customer ID not exist."),
    CUSTOMER_ID_NOT_EXIST_DELETE("Cannot delete customer with exiting non exist id."),
    CUSTOMER_ID_NOT_EXIST_UPDATE("Cannot update customer with exiting non exist id."),
    CUSTOMER_WITHOUT_COUPONS("Customer without coupons "),
    UPDATE_COMPANY_NAME("Cannot update company name."),
    UPDATE_CUSTOMER_EMAIL("Cannot update customer with exiting customer email."),
    ;

    private final String msg;

    ErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
