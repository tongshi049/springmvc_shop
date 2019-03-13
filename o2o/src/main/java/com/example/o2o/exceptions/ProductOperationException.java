package com.example.o2o.exceptions;

public class ProductOperationException extends RuntimeException {

    public static final long serialVersionUID = -6028476996256067615L;

    public ProductOperationException (String msg) {
        super(msg);
    }
}
