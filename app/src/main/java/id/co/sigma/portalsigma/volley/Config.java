package id.co.sigma.portalsigma.volley;

/**
 * Created by Aries Satriana on 26/09/2016.
 */
public class Config {
    //URL to our login.php file
//    public static final String LOGIN_URL = "http://portaldev.telkomsigma.co.id/index.php/service/testpost";
    public static final String BASE_URL="http://portaldev.telkomsigma.co.id/index.php/service/";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "usermail";
    public static final String KEY_PASSWORD = "userpass";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "OK";
    public static final String REQUEST_SUCCESS="OK";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    // Form $POST u/ Whats on your mind
    public static final String KEY_CAPTION="caption";
    public static final String KEY_CONTENT="content";
    public static final String KEY_STATUS="status";
    public static final String KEY_CATEGORY="category";
    public static final String KEY_INPUTDATE="input_date";
    public static final String KEY_SUBMIT_BY="submit_by";
    public static final String KEY_FILE_NAME="file[]";

}
