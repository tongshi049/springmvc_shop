package com.example.o2o.exceptions;

import java.io.Serializable;

public class ShopCategoryOperationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1894749570649005372L;
    public ShopCategoryOperationException(String msg){
        super(msg);
    }
}
