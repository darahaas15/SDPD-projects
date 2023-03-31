package androidsamples.java.journalapp;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {
    public static DateFormat df = new SimpleDateFormat("HH:mm");

    @TypeConverter
    public static Date toTime(String s) throws ParseException {
        Date d = df.parse(s);
        return d;
    }

    @TypeConverter
    public static String toString(Date d) {
        String s = df.format(d);
        return s;
    }
}
