package com.example.o2o.exceptions;

import java.io.Serializable;

public class LocalAuthOperationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 411052616077560937L;

    public LocalAuthOperationException(String msg){
        super(msg);
    }
}
