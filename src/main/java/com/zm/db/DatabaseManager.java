/*
Creating by Zelma Milev
*/
package com.zm.db;

import com.zm.beans.Category;
import com.zm.dao.CategoryDao;
import com.zm.dbdao.CategoryDBDAO;
import com.zm.utils.DBUtils;

import java.sql.SQLException;
/*
   הגדרות סכמה וטבלאות של הפרויקט
 */
public class DatabaseManager {

    private static CategoryDao categoryDao = new CategoryDBDAO();

    private static final String CREATE_SCHEMA = "CREATE SCHEMA `bhn_gr1_zm_pr1`";
    private static final String DROP_SCHEMA = "DROP SCHEMA `bhn_gr1_zm_pr1`";
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `bhn_gr1_zm_pr1`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `bhn_gr1_zm_pr1`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `bhn_gr1_zm_pr1`.`coupons` (\n" +
            " `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(45) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` LONGTEXT NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `id_idx1` (`category_id` ASC) VISIBLE,\n" +
            "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `bhn_gr1_zm_pr1`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "       FOREIGN KEY (`category_id`)\n" +
            "    REFERENCES `bhn_gr1_zm_pr1`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `bhn_gr1_zm_pr1`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));";

    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS
            = "CREATE TABLE `bhn_gr1_zm_pr1`.`customers_vs_coupons` (\n" +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`customer_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `bhn_gr1_zm_pr1`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `bhn_gr1_zm_pr1`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";

//
//    private static final String DROP_TABLE_COMPANIES = "DROP TABLE `bhn_gr1_zm_pr1`.`companies` ";
//    private static final String DROP_TABLE_CATEGORIES = "DROP TABLE `bhn_gr1_zm_pr1`.`categories` ";
//    private static final String DROP_TABLE_COUPONS = "DROP TABLE `bhn_gr1_zm_pr1`.`coupons` ";
//    private static final String DROP_TABLE_CUSTOMERS = "DROP TABLE `bhn_gr1_zm_pr1`.`customers` ";
//    private static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE `bhn_gr1_zm_pr1`.`customers_vs_coupons` ";


    public static void dropAndCreate() throws SQLException {
        DBUtils.runStatement(DROP_SCHEMA);
        DBUtils.runStatement(CREATE_SCHEMA);
        DBUtils.runStatement(CREATE_TABLE_COMPANIES);
        DBUtils.runStatement(CREATE_TABLE_CATEGORIES);
        DBUtils.runStatement(CREATE_TABLE_CUSTOMERS);
        DBUtils.runStatement(CREATE_TABLE_COUPONS);
        DBUtils.runStatement(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
        for (Category c : Category.values()) {
            categoryDao.addCategory(c);
        }
    }



}
