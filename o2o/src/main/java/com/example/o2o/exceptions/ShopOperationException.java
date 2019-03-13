package com.example.o2o.exceptions;

import java.io.Serializable;

public class ShopOperationException extends RuntimeException implements Serializable {

    /**
     *
     * @param msg
     */
    private static final long serialVersionUID = 33485890156854492L;

    public ShopOperationException (String msg) {
        super(msg);
    }

}
