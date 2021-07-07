/*
Creating by Zelma Milev
*/
package com.zm.exceptions;

import com.zm.beans.ErrorMsg;

public class CouponSystemException extends Exception {
    public CouponSystemException(String msg) {
        super(msg);
    }

    public CouponSystemException(ErrorMsg errorMsg) {
        super(errorMsg.getMsg());
    }
}
