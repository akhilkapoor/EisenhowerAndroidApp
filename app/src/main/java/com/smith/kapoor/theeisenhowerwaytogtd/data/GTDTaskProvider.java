/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class GTDTaskProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int TASK = 100;
    private static final int TASK_WITH_ID = 101;
    private GTDDbHelper gtdDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = GTDTaskContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, GTDTaskContract.PATH_TASK + "/*", TASK_WITH_ID);
        matcher.addURI(authority, GTDTaskContract.PATH_TASK, TASK);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        gtdDbHelper = new GTDDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case TASK: {
                retCursor = gtdDbHelper.getReadableDatabase().query(
                        GTDTaskContract.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TASK_WITH_ID: {
                retCursor = gtdDbHelper.getReadableDatabase().query(
                        GTDTaskContract.TABLE_NAME,
                        projection,
                        GTDTaskContract._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case TASK:
                return GTDTaskContract.CONTENT_ITEM_TYPE;
            case TASK_WITH_ID:
                return GTDTaskContract.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = gtdDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TASK: {
                long _id = db.insert(GTDTaskContract.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = GTDTaskContract.buildTaskUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = gtdDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case TASK_WITH_ID:
                String rowId = uri.getLastPathSegment();
                rowsDeleted = db.delete(GTDTaskContract.TABLE_NAME,  "_id = ?", new String[] {rowId});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = gtdDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case TASK_WITH_ID:
                int rowId = values.getAsInteger(GTDTaskContract._ID);
                values.remove(GTDTaskContract._ID);
                rowsUpdated = db.update(GTDTaskContract.TABLE_NAME, values, "_id = ? ", new String[] {rowId+""});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

}
