/*
Creating by Zelma Milev
*/
package com.zm.dbdao;

import com.zm.beans.Company;
import com.zm.dao.CompanyDao;
import com.zm.utils.DBUtils;
import com.zm.utils.ResultSetUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
מחלקת מכילה כל הפונקציות CRUD לטיפול בטבלת החברות- companies
*/
public class CompanyDBDAO implements CompanyDao {
    private static final String QUERY_INSERT = "INSERT INTO `bhn_gr1_zm_pr1`.`companies` (`name`, `email`,`password`) VALUES ( ?, ?,?);\n";
    private static final String QUERY_UPDATE = "UPDATE `bhn_gr1_zm_pr1`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `bhn_gr1_zm_pr1`.`companies` WHERE (`id` = ?);";
    private static final String QUERY_GET_ONE = "SELECT * FROM `bhn_gr1_zm_pr1`.`companies` WHERE (`id` = ?);";
    private static final String QUERY_GET_ID_PER_EMAIL_PASS = "SELECT (`id`) FROM `bhn_gr1_zm_pr1`.`companies` WHERE (`email` = ? AND `password` = ?);";
    private static final String QUERY_GET_COUNT_NAME_OR_EMAIL = "SELECT COUNT(*) FROM `bhn_gr1_zm_pr1`.`companies` WHERE (`name` = ? OR`email` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `bhn_gr1_zm_pr1`.`companies`";

    @Override
    public void addCompany(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        DBUtils.runStatement(QUERY_INSERT, map);
    }

    @Override
    public void updateCompany(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        map.put(4, company.getId());
        DBUtils.runStatement(QUERY_UPDATE, map);
    }

    @Override
    public void deleteCompany(int companyId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        DBUtils.runStatement(QUERY_DELETE, map);
    }

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        return ResultSetUtils.isFound(DBUtils.runStatementWithResultSet(QUERY_GET_ID_PER_EMAIL_PASS, map));
    }

    @Override
    public int getCompanyID(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        return ResultSetUtils.getOneInt(DBUtils.runStatementWithResultSet(QUERY_GET_ID_PER_EMAIL_PASS, map));
    }

    @Override
    public boolean isCompanyExists(int companyId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        return ResultSetUtils.isFound(DBUtils.runStatementWithResultSet(QUERY_GET_ONE, map));
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ONE, map);
        resultSet.next();
        return ResultSetUtils.getCompany(resultSet);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        List<Company> companyList = new ArrayList<>();
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL);
        while (resultSet.next()) {
            companyList.add(ResultSetUtils.getCompany(resultSet));
        }
        return companyList;
    }

    @Override
    public boolean isNameOrEmailExist(String name, String email) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, name);
        map.put(2, email);
        return ResultSetUtils.isCountGtZero(DBUtils.runStatementWithResultSet(QUERY_GET_COUNT_NAME_OR_EMAIL, map));
    }
}
