package androidsamples.java.journalapp;

import android.annotation.SuppressLint;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat1 = new SimpleDateFormat("E,MMM dd,yyyy");

    @TypeConverter
    public static Date toDate(String s) throws ParseException {
        return dateFormat1.parse(s);
    }

    @TypeConverter
    public static String toString(Date d) {
        return dateFormat1.format(d);
    }
}
