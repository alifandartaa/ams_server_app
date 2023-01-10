/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.services;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/**
 *
 * @author bintang mada
 */
@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return patternMatches(s, regexPattern);
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}