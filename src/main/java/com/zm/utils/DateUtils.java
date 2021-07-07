/*
Creating by Zelma Milev
*/
package com.zm.utils;

import java.sql.Date;
import java.time.LocalDate;
//import java.util.Date;

public class DateUtils {
    public static Date convertToDataViaData(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

}
