/*
Creating by Zelma Milev
*/
package com.zm.dbdao;

import com.zm.beans.Category;
import com.zm.dao.CategoryDao;
import com.zm.utils.DBUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/*
מחלקת מכילה רק הפונקצית INSERT להעברת קטגוריות לDB
*/
public class CategoryDBDAO implements CategoryDao {
    private static final String QUERY_INSERT = "INSERT INTO `bhn_gr1_zm_pr1`.`categories` (`name`) VALUES ( ?);\n";


    @Override
    public void addCategory(Category category) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, category.name());
        DBUtils.runStatement(QUERY_INSERT, map);
    }


}
