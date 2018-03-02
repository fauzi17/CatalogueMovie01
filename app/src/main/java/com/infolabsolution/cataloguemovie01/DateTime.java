package com.infolabsolution.cataloguemovie01;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    private static String formatDate(String date, String format) {
        String result = "date";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateDate = dateFormat.parse(date);
            DateFormat newFormat = new SimpleDateFormat(format);
            result = newFormat.format(dateDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getDate(String date) {

        return formatDate(date, "EEEE, MMM d, yyyy");
    }
}
