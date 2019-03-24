package com.example.o2o.exceptions;

import java.io.Serializable;

/**
 *
 */
public class AreaOperationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 9172168248291541926L;

    public AreaOperationException(String msg){
        super(msg);
    }
}
