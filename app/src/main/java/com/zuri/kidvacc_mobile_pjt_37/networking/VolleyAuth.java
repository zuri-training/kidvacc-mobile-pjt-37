package com.zuri.kidvacc_mobile_pjt_37.networking;

public class VolleyAuth {
    private static final String ROOT_DATA_URL = "https://kidvacc.herokuapp.com";
    private static final String ROOT_AUTH_URL = ROOT_DATA_URL+ "/api/child/rest-auth/";
    private static final String ROOT_PAYMENT_URL = ROOT_DATA_URL+ "/payments/";

    public static final String URL_REGISTER = ROOT_AUTH_URL + "registration/";
    public static final String URL_LOGIN= ROOT_AUTH_URL + "login/";

    public static final String URL_CHILD_LIST= ROOT_DATA_URL + "/api/child/child";
    public static final String URL_PARENT_LIST= ROOT_DATA_URL + "/api/child/parent";

    public static final String URL_APPOINTMENT= ROOT_DATA_URL + "/api/child/appointment";
    public static final String URL_HOSPITAL_TYPE= ROOT_DATA_URL + "/api/child/hospital_type";

    public static final String URL_PAYMENTS= ROOT_PAYMENT_URL + "my-payments/";
    public static final String URL_MAKE_PAYMENT= ROOT_PAYMENT_URL + "make-payment/";

    public static String TOKEN= "0";
}
