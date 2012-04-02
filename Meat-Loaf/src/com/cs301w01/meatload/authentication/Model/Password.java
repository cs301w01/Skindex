package com.cs301w01.meatload.authentication.Model;

/**
 * Created with IntelliJ IDEA.
 * User: Derek
 * Date: 4/1/12
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Password {

    private String salt;
    private String pw;

    public Password(String s, String password) {

        pw = password;
        salt = s;

    }

    public String getPw() {
        return pw;
    }

    public String getSalt() {
        return salt;
    }
}
