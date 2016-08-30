package com.mozio.todd;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rathakit.nop on 8/30/16 AD.
 */
public class Util {

    /**
     * Converts the date object to string in order to display on the UI.
     * @param date
     * @return the display string
     */
    public static String getDisplayBirthday(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        return format.format(date);
    }
}
