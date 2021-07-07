/*
Creating by Zelma Milev
*/
package com.zm.utils;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.text.Format;

public class PrintUtils {
    private static int numHeading1 = 0;
    private static int numHeading2 = 0;

    public static void PrintTestOneHeading(String content) {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("-------------Test : %d  Content : %s--------------------%n", ++numHeading1, content);
        System.out.println("----------------------------------------------------------------------------------------");
        numHeading2 = 0;
    }

    public static void PrintTestTwoHeading(String content) {
        System.out.printf("-------------Test : %d . %d Content : %s--------------------%n", numHeading1, ++numHeading2, content);
        System.out.println("--------------------------");
    }
}
