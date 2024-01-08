package com.msr.rentalagency.system.exception;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String objectName, Integer id)
    {
        super("Could not find "+objectName+ " with id:"+id+" :)" );
    }
}
