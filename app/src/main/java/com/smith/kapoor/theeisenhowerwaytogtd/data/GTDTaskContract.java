/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.CompletedEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.PriorityEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.objects.TaskDO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GTDTaskContract implements BaseColumns {

    private static String LOG_TAG = GTDTaskContract.class.getSimpleName();

    public static final String CONTENT_AUTHORITY = "com.smith.kapoor";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TASK = "task";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TASK;
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK).build();
    // Table name
    public static final String TABLE_NAME = "task";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMPLETED = "completed";

    public static String createSQL () {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_PRIORITY + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_COMPLETED + " TEXT NOT NULL " +
                " );";
    }

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    public static Uri buildTaskUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static String getDbDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static Date getDateFromDb(String dateText) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dbDateFormat.parse(dateText);
        } catch (ParseException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     *
     * @param taskDO - The data object containing the values we want in our ContentValue map
     * @return - A content value map containing the data from the DO
     */
    private static ContentValues convertToContentValues(TaskDO taskDO) {
        ContentValues values = new ContentValues();
        if(taskDO.getId() != -1) {
            values.put(_ID, taskDO.getId());
        }
        values.put(COLUMN_NAME, taskDO.getName());
        values.put(COLUMN_DESCRIPTION, taskDO.getDescription());
        values.put(COLUMN_PRIORITY, taskDO.getPriorityEnum().toString());
        values.put(COLUMN_DATE, getDbDateString(taskDO.getCompletetionDate()));
        values.put(COLUMN_COMPLETED, taskDO.getCompletedEnum().toString());

        return values;
    } // end convertToContentValues


    public static TaskDO populateTask(Cursor cursor) {

        TaskDO taskDO = new TaskDO();

        int index = cursor.getColumnIndex(_ID);
        taskDO.setId(cursor.getInt(index));

        index = cursor.getColumnIndex(COLUMN_NAME);
        taskDO.setName(cursor.getString(index));

        index = cursor.getColumnIndex(COLUMN_DESCRIPTION);
        taskDO.setDescription(cursor.getString(index));

        index = cursor.getColumnIndex(COLUMN_PRIORITY);
        taskDO.setPriorityEnum(PriorityEnum.valueOf(cursor.getString(index)));

        index = cursor.getColumnIndex(COLUMN_DATE);
        taskDO.setCompletetionDate(getDateFromDb(cursor.getString(index)));

        index = cursor.getColumnIndex(COLUMN_COMPLETED);
        taskDO.setCompletedEnum(CompletedEnum.valueOf(cursor.getString(index)));

        return taskDO;
    } // end populateTask

    /**
     *
     * @param context -
     * @param taskDO -
     * @return -
     */
    public static int updateTask(Context context, TaskDO taskDO) {
        return context.getContentResolver().update(buildTaskUri(taskDO.getId()), convertToContentValues(taskDO), null, null);
    }

    /**
     *
     * @param context -
     * @param taskDO -
     * @return -
     */
    public static TaskDO addTask(Context context, TaskDO taskDO) {
        Uri uri = context.getContentResolver().insert(CONTENT_URI, convertToContentValues(taskDO));
        String taskIdString = uri.getLastPathSegment();
        taskDO.setId(Integer.valueOf(taskIdString));
        return taskDO;
    }

    /**
     *
     * @param context -
     * @param taskDO -
     * @return -
     */
    public static int removeTask(Context context, TaskDO taskDO) {
        return context.getContentResolver().delete(buildTaskUri(taskDO.getId()), null, null);
    }

}
