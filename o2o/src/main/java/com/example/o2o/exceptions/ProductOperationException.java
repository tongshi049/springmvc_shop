package com.example.o2o.exceptions;

import java.io.Serializable;

public class ProductOperationException extends RuntimeException implements Serializable {

    public static final long serialVersionUID = -6028476996256067615L;

    public ProductOperationException (String msg) {
        super(msg);
    }
}
