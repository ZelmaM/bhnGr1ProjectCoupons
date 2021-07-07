package com.zm.dao;

import com.zm.beans.Company;

import java.sql.SQLException;
import java.util.List;

/*
Data Access Objects מחלקות לבצע פעולות CRUD
 על הטבלאות במסד הנתונים. מחלקות אלו לא מבצעות את הלוגיקה ((Create/Read/Update/Delete
 */
public interface CompanyDao {
    void addCompany(Company company) throws SQLException;
    void updateCompany(Company company) throws SQLException;
    void deleteCompany(int companyId) throws SQLException;

    boolean isCompanyExists(String email,String password) throws SQLException;
    int getCompanyID(String email,String password) throws SQLException;

    boolean isCompanyExists(int companyId) throws SQLException;

    Company getOneCompany(int companyId) throws SQLException;

    List<Company> getAllCompanies() throws SQLException;

    boolean isNameOrEmailExist(String name, String email) throws SQLException;
}

