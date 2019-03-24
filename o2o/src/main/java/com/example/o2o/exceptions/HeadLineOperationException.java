package com.example.o2o.exceptions;



import java.io.Serializable;

public class HeadLineOperationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 8479106701419277183L;

    public HeadLineOperationException(String msg){
        super(msg);
    }
}
