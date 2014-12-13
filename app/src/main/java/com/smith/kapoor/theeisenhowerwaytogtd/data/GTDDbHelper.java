/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GTDDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "gtd.db";
    private static final int DATABASE_VERSION = 1;

    public GTDDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(GTDTaskContract.createSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Nothing to do here for now
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GTDTaskContract.TABLE_NAME);
    }
}
