package com.example;

import org.json.simple.JSONObject;

/**
 * Created by mike on 10/31/16.
 */
public class UtilConstants {

    /*2XX*/
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NON_AUTHORITATIVE_INFO = 203;
    public static final int NO_CONTENT = 204;
    public static final int IM_USED = 226;

    /*3XX*/
    public static final int MULTIPLE_CHOISED = 300;
    public static final int MOVED_PERMANENTLY = 301;
    public static final int FOUND = 302;
    public static final int NOT_MODIFIED = 304;
    public static final int PERMANENT_REDIRECT = 308;

    /*4XX*/
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    /*The request was a valid request, but the server is refusing to respond to it.
     *The user might be logged in but does not have the necessary permissions for the resource.*/
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    /*A request method is not supported for the requested resource; for example,
     a GET request on a form which requires data to be presented via POST,
      or a PUT request on a read-only resource.*/
    public static final int NOT_ACCEPTABLE = 406;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int RANGE_IS_NOT_SATISFIABLE = 416;

    /*5XX*/
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    public static final int BAD_GATEWAY = 502;
    public static final int HTTP_VERION_NOT_SUPPORTED = 505;
    public static final int INSUFFICINT_STORAGE = 507;
    public static final int LOOP_DETECTED = 508;
    public static final int NETWORK_AUTHENTIFICATION_REQIRED = 511;

    //App Error codes
    public static final int INVALID_CONTENT = 1001;


    public static JSONObject getResponseStatus(int code) {
        JSONObject response = new JSONObject();
        response.put("status", code);
        return response;
    }
}
