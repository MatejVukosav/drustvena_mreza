package com.example.vuki.drustvena_mreza_faks.helpers;

/**
 * Created by Vuki on 3.11.2015..
 */
public class ValidationHelpers {

    public static int MIN_PASSWORD_LENGTH=3;

    public static boolean checkEmail(String email) {
        return true;//email.contains("@");
    }

    public static boolean checkPassword(String password) {
        return true;//password.length()>= MIN_PASSWORD_LENGTH;
    }

}
