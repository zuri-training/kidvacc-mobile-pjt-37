package com.zuri.kidvacc_mobile_pjt_37.networking;

public class VolleyAuth {
    private static final String ROOT_AUTH_URL = "https://kidvacc.herokuapp.com/api/child/rest-auth/";
    public static final String URL_REGISTER = ROOT_AUTH_URL + "registration/";
    public static final String URL_LOGIN= ROOT_AUTH_URL + "login/";

    public static String TOKEN= "0";
}
