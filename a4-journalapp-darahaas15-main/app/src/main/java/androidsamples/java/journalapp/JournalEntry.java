package androidsamples.java.journalapp;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    public UUID mUid;

    @ColumnInfo(name = "title")
    public String mTitle;

    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter.class)
    public String mDate;

    @ColumnInfo(name = "start_time")
    @TypeConverters(TimeConverter.class)
    public String mStartTime;

    @ColumnInfo(name = "end_time")
    @TypeConverters(TimeConverter.class)
    public String mEndTime;

    public JournalEntry(@NonNull String mTitle, String mStartTime, String mEndTime, String mDate) {
        mUid = UUID.randomUUID();
        this.mEndTime = mEndTime;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mStartTime = mStartTime;
    }

    public String title() {
        return mTitle;
    }

    public String date() {
        return mDate;
    }

    public String start() {
        return mStartTime;
    }

    public String end() {
        return mEndTime;
    }

    public UUID getUid() {
        return mUid;
    }

    public void setTitle(String s) {
        mTitle = s;
    }

    public void setDate(String x) {
        mDate = x;
    }

    public void setStartTime(String x) {
        mStartTime = x;
    }

    public void setEndTime(String x) {
        mEndTime = x;
    }

    public void setUid(UUID x) {
        mUid = x;
    }
}
