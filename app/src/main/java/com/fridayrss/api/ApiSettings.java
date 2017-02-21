package com.fridayrss.api;

/**
 * Created by voltazor on 26/04/16.
 */
public class ApiSettings {

    public static final String SCHEME = "https://";

    public static final String HOSTNAME = "fridayrss.herokuapp.com";

    public static final String SERVER = SCHEME + HOSTNAME;

    public static final String API = "/api/test";

    public static final String FRIDAY_ID = "friday_id";

    public static final String LAST_FRIDAY_PHOTOS = API;

    public static final String FRIDAYS_LIST = API + "/fridays";

    public static final String PHOTOS_BY_FRIDAY_ID = API + "/friday/{" + FRIDAY_ID + "}";

}
