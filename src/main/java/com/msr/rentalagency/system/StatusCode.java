package com.msr.rentalagency.system;

public class StatusCode {
    public static final int SUCCESS = 200;//success
    public static final int INVALID_ARGUMENTS = 400;//BAD REQUEST, e,g, bad parameter
    public static final int UNAUTHORIZED = 401;//user or password incorrect
    public static final int FORBIDEN = 403; //no permission
    public static final int NOT_FOUND = 404; //not found
    public static final int INTERNAL_SERVER_ERROR = 500; //server internal error

}
