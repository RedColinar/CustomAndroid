package com.example.harry.customandroid.tabs.develop.calendar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by panqiang at 19-1-18
 * http://www.zoftino.com/how-to-read-and-write-calendar-data-in-android
 */
public class CalendarActivity extends BaseActivity {

    @BindView(R.id.bt_add_calendar)
    Button btAddCalendar;

    public static final int MY_CAL_WRITE_REQ = 0x1;
    public static final int MY_CAL_REQ = 0x2;

    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    @Override
    public int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    public int getTitleId() {
        return R.string.calendar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // @OnClick(R.id.bt_add_calendar)
    public void click(View v) {
        createCalendar();
    }

    @OnClick(R.id.bt_add_calendar)
    public void createCalendar() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarContract.Calendars.ACCOUNT_NAME, "cal@zoftino.com");
        contentValues.put(CalendarContract.Calendars.ACCOUNT_TYPE, "cal.zoftino.com");
        contentValues.put(CalendarContract.Calendars.NAME, "zoftino calendar");
        contentValues.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Zoftino.com Calendar");
        contentValues.put(CalendarContract.Calendars.CALENDAR_COLOR, "232323");
        contentValues.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        contentValues.put(CalendarContract.Calendars.OWNER_ACCOUNT, "cal@zoftino.com");
        contentValues.put(CalendarContract.Calendars.ALLOWED_REMINDERS, "METHOD_ALERT, METHOD_EMAIL, METHOD_ALARM");
        contentValues.put(CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES, "TYPE_OPTIONAL, TYPE_REQUIRED, TYPE_RESOURCE");
        contentValues.put(CalendarContract.Calendars.ALLOWED_AVAILABILITY, "AVAILABILITY_BUSY, AVAILABILITY_FREE, AVAILABILITY_TENTATIVE");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_CAL_WRITE_REQ);
        }

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        uri = uri.buildUpon().appendQueryParameter(android.provider.CalendarContract.CALLER_IS_SYNCADAPTER,"true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "cal@zoftino.com")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "cal.zoftino.com").build();
        getContentResolver().insert(uri, contentValues);
    }

    @OnClick(R.id.bt_query_calendar)
    public void getDataFromCalendarTable() {
        Cursor cur;
        ContentResolver cr = getContentResolver();

        String[] mProjection =
                {
                        CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.CALENDAR_LOCATION,
                        CalendarContract.Calendars.CALENDAR_TIME_ZONE
                };

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {
                        "cal@zoftino.com",
                        "cal.zoftino.com",
                        "cal@zoftino.com"
                };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, MY_CAL_REQ);
        }

        if (cr == null) throw new RuntimeException("ContentResolver null");
        cur = cr.query(uri, mProjection, selection, selectionArgs, null);
        while (cur.moveToNext()) {
            String displayName = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME));
            String accountName = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME));

            Log.d(TAG, displayName);
            Log.d(TAG, accountName);
        }

    }
}
