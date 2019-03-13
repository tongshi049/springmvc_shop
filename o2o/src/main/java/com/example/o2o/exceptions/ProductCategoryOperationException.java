package com.example.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = 7776101317302641261L;

    public ProductCategoryOperationException (String msg) {
        super(msg);
    }

}
