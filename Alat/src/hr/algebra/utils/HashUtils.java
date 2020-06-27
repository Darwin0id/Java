/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author darwin
 */
public class HashUtils {
  public static String sha512String(String input) throws NoSuchAlgorithmException 
    { 
        MessageDigest md = MessageDigest.getInstance("SHA-512"); 
        byte[] messageDigest = md.digest(input.getBytes()); 
        BigInteger no = new BigInteger(1, messageDigest); 

        String hashtext = no.toString(16); 

        while (hashtext.length() < 32) { 
            hashtext = "0" + hashtext; 
        } 

        return hashtext; 
    }
}
