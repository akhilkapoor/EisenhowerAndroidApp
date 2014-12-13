/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.smith.kapoor.theeisenhowerwaytogtd.data.GTDTaskContract;

public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    // brings our database to an empty state
    public void deleteAllRecords() {
        mContext.getContentResolver().delete(
                GTDTaskContract.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                GTDTaskContract.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                GTDTaskContract.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                GTDTaskContract.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    // Since we want each test to start with a clean slate, run deleteAllRecords
    // in setUp (called by the test runner before each test).
    public void setUp() {
        deleteAllRecords();
    }

    public void testInsertReadProvider() {

        ContentValues testValues = TestDb.createTestTask();

        Uri locationUri = mContext.getContentResolver().insert(GTDTaskContract.CONTENT_URI, testValues);
        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                GTDTaskContract.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(cursor, testValues);

        // Now see if we can successfully query if we include the row id
        cursor = mContext.getContentResolver().query(
                GTDTaskContract.buildTaskUri(locationRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestDb.validateCursor(cursor, testValues);
    }

    public void testUpdateLocation() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestDb.createTestTask();

        Uri locationUri = mContext.getContentResolver().
                insert(GTDTaskContract.CONTENT_URI, values);
        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(GTDTaskContract._ID, locationRowId);
        updatedValues.put(GTDTaskContract.COLUMN_NAME, "Santa");

        int count = mContext.getContentResolver().update(
                GTDTaskContract.CONTENT_URI, updatedValues, GTDTaskContract._ID + "= ?",
                new String[]{Long.toString(locationRowId)});

        assertEquals(count, 1);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                GTDTaskContract.buildTaskUri(locationRowId),
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null // sort order
        );

        TestDb.validateCursor(cursor, updatedValues);
    }

    // Make sure we can still delete after adding/updating stuff
    public void testDeleteRecordsAtEnd() {
        deleteAllRecords();
    }

}
