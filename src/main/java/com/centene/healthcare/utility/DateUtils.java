package com.centene.healthcare.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * This method uses to format the date as per our requirement
     * @return
     */
    public static Date getFormattedDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * This method uses to format the birthdate as per our requirement
     * @return
     */
    public static Date getBirthDate(Date birthDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(new java.util.Date(birthDate.getTime()));
        try {
            return dateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * This method uses to convert the String to birthdate as per our requirement
     * @param birthDate
     * @return
     */
    public static Date convertStringToDate(String birthDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
             return dateFormat.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * This method uses to convert the String to birthdate as per our requirement
     * @param birthDate
     * @return
     */
    public static String convertStringToDate(Date birthDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dob = dateFormat.format(birthDate);
        return dob;
    }
}
