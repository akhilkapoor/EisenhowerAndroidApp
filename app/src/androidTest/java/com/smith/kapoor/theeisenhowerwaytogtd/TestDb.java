/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.smith.kapoor.theeisenhowerwaytogtd.data.GTDDbHelper;
import com.smith.kapoor.theeisenhowerwaytogtd.data.GTDTaskContract;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.CompletedEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.PriorityEnum;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    static ContentValues createTestTask() {
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(GTDTaskContract.COLUMN_NAME, "Name");
        weatherValues.put(GTDTaskContract.COLUMN_DESCRIPTION, "This is the task.");
        weatherValues.put(GTDTaskContract.COLUMN_DATE, GTDTaskContract.getDbDateString(new Date()));
        weatherValues.put(GTDTaskContract.COLUMN_PRIORITY, PriorityEnum.NOT_IMPORTANT_NOT_URGENT.toString());
        weatherValues.put(GTDTaskContract.COLUMN_COMPLETED, CompletedEnum.INCOMPLETE.toString());

        return weatherValues;
    }

    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
        valueCursor.close();
    }

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(GTDDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new GTDDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() {

        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        GTDDbHelper dbHelper = new GTDDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = createTestTask();

        long locationRowId;
        locationRowId = db.insert(GTDTaskContract.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                GTDTaskContract.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(cursor, testValues);

        dbHelper.close();
    }
}
