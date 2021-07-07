package com.zm.dao;

import com.zm.beans.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    void addCategory(Category category) throws SQLException;

}
