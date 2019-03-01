package com.example.harry.customandroid.tabs.develop.calendar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;
import com.google.ical.values.RRule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by panqiang at 19-1-18
 * http://www.zoftino.com/how-to-read-and-write-calendar-data-in-android
 */
@SuppressLint("MissingPermission")
public class CalendarActivity extends BaseActivity {

    private String accountName = "pq_ACCOUNT_NAME";
    private String accountType = CalendarContract.ACCOUNT_TYPE_LOCAL;
    private String name = "pq_NAME";
    private String calendarDisplayName = "pq_CALENDAR_DISPLAY_NAME";
    private int calendarColor = 232323;
    private int calendarAccessLevel = CalendarContract.Calendars.CAL_ACCESS_OWNER;
    private String ownerAccount = "pq_OWNER_ACCOUNT";

    private ContentResolver contentResolver;

    @BindView(R.id.bt_add_calendar)
    Button btAddCalendar;

    public static final int MY_CAL_WRITE_REQ = 0x1;
    public static final int MY_CAL_REQ = 0x2;

    private int calendarId = -1;
    private int lastEventId = -1;

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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, MY_CAL_REQ);
        }

        contentResolver = getContentResolver();
        if (contentResolver == null) throw new RuntimeException("ContentResolver null");

        initCalendar();
    }

    private void initCalendar() {
        calendarId = queryCalendar();

        if (calendarId < 0) {
            createCalendar();
        }
    }

    @OnClick(R.id.bt_add_calendar)
    public void createCalendar() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarContract.Calendars.ACCOUNT_NAME, accountName);
        contentValues.put(CalendarContract.Calendars.ACCOUNT_TYPE, accountType);
        contentValues.put(CalendarContract.Calendars.NAME, name);
        contentValues.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, calendarDisplayName);
        contentValues.put(CalendarContract.Calendars.CALENDAR_COLOR, calendarColor);
        contentValues.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        contentValues.put(CalendarContract.Calendars.OWNER_ACCOUNT, ownerAccount);

        contentValues.put(CalendarContract.Calendars.ALLOWED_REMINDERS, "METHOD_ALERT, METHOD_EMAIL, METHOD_ALARM");
        contentValues.put(CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES, "TYPE_OPTIONAL, TYPE_REQUIRED, TYPE_RESOURCE");
        contentValues.put(CalendarContract.Calendars.ALLOWED_AVAILABILITY, "AVAILABILITY_BUSY, AVAILABILITY_FREE, AVAILABILITY_TENTATIVE");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_CAL_WRITE_REQ);
        }

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        uri = uri.buildUpon().appendQueryParameter(android.provider.CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, accountType).build();
        Uri u = getContentResolver().insert(uri, contentValues);
        calendarId = Integer.parseInt(u.getLastPathSegment());
    }

    @OnClick(R.id.bt_query_calendar)
    public void getDataFromCalendarTable() {
        queryCalendar();
    }

    public int queryCalendar() {
        String[] mProjection = {CalendarContract.Calendars._ID};

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {accountName, accountType, ownerAccount};

        Cursor cur = contentResolver.query(uri, mProjection, selection, selectionArgs, null);


        if (cur != null && cur.getCount() > 0) {
            cur.moveToFirst();
            int id = cur.getInt(cur.getColumnIndex(CalendarContract.Calendars._ID));
            cur.close();

            return id;
        }

        return -1;
    }

    @OnClick(R.id.bt_delete_calendar)
    public void deleteCalendar() {
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {accountName, accountType, ownerAccount};
        contentResolver.delete(CalendarContract.Calendars.CONTENT_URI, selection, selectionArgs);
    }

    @OnClick(R.id.bt_add_event)
    public void addEvent() {
        // 月份从 0 开始

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH),
                beginTime.get(Calendar.DAY_OF_MONTH), beginTime.get(Calendar.HOUR_OF_DAY), beginTime.get(Calendar.MINUTE) + 16);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH),
                endTime.get(Calendar.DAY_OF_MONTH), endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE) + 18);
        long endMillis = endTime.getTimeInMillis();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "pq_事件标题");
        values.put(CalendarContract.Events.DESCRIPTION, "pq_事件描述");
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        // 重复
        String rrule = "FREQ=DAILY;INTERVAL=1;COUNT=3;UNTIL=20200109";
        values.put(CalendarContract.Events.RRULE, rrule);

        try {
            Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
            lastEventId = Integer.parseInt(uri.getLastPathSegment());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.bt_update_event)
    public void updateEvent() {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, "pq_更新事件标题");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, lastEventId);
        int rows = contentResolver.update(updateUri, values, null, null);
    }

    @OnClick(R.id.bt_delete_event)
    public void deleteEvent() {
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, lastEventId);
        getContentResolver().delete(deleteUri, null, null);
    }

    @OnClick(R.id.bt_add_reminder)
    public void addReminder() {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 15);
        values.put(CalendarContract.Reminders.EVENT_ID, lastEventId);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, values);
    }

    @OnClick(R.id.bt_update_reminder)
    public void updateReminder() {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 0);
        values.put(CalendarContract.Reminders.EVENT_ID, lastEventId);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = ContentUris.withAppendedId(CalendarContract.Reminders.CONTENT_URI, lastEventId);
        contentResolver.update(uri, values, null, null);
    }

    @OnClick(R.id.bt_delete_reminder)
    public void deleteReminder() {
        Uri uri = ContentUris.withAppendedId(CalendarContract.Reminders.CONTENT_URI, lastEventId);
        contentResolver.delete(uri, null, null);
    }

    public static final String[] INSTANCE_PROJECTION = new String[] {
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.END,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.START_DAY,
            CalendarContract.Instances.START_MINUTE,
            CalendarContract.Instances.END_DAY,
            CalendarContract.Instances.END_MINUTE,
    };

    @OnClick(R.id.bt_query_instance)
    public void queryInstance() {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH),
                beginTime.get(Calendar.DAY_OF_MONTH), beginTime.get(Calendar.HOUR_OF_DAY), beginTime.get(Calendar.MINUTE));
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH),
                endTime.get(Calendar.DAY_OF_MONTH), endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE));
        endTime.add(Calendar.DAY_OF_MONTH, 4);
        long endMillis = endTime.getTimeInMillis();

        // 要在 Instances 表中查询的事件 ID
        String selection = CalendarContract.Instances.EVENT_ID + " = ?";
        String[] selectionArgs = new String[] {lastEventId + ""};

        // 根据日期范围构造查询
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // 提交查询
        Cursor cur = contentResolver.query(builder.build(),
                INSTANCE_PROJECTION,
                selection,
                selectionArgs,
                null);

        while (cur.moveToNext()) {
            long eventID = cur.getLong(0);
            String title = cur.getString(3);
            long beginVal = cur.getLong(1);
            int startDay = cur.getInt(4);
            int startMinute = cur.getInt(5);

            // 利用这些数据完成一些操作
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(beginVal);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String beginDate = formatter.format(calendar.getTime());


        }
    }
}
