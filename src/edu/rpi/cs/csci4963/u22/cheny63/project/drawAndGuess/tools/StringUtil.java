package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
* String Utilities
* @author Kevin Xia
* @version 1.0
*/
public class StringUtil {
    /**
     * Validate the address name
     * @param input address
     * @return true if input is valid address name,
     *          false otherwise
     */
    public static boolean validAddress(String input){
        return Pattern.matches("^[\\/a-zA-Z0-9._-]+", input);
    }

    /**
     * Validate the integer input
     * @param input integer
     * @param min minimum for integer
     * @param max maximum for integer
     * @return true if input is valid integer,
     *          false otherwise
     */
    public static boolean validInteger(String input, int min, int max){
        if(Pattern.matches("^[0-9]+", input) && input.length() < 10){
            int num = Integer.parseInt(input);
            return min <= num && num <= max;
        }
        return false;
    }

    public static String getInetAddress(){
        String address;
        try{
            address = InetAddress.getLocalHost().toString().split("/")[1];
        }catch(UnknownHostException e){
            address = "";
        }
        return address;
    }
}
