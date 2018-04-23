package com.fauzify.favoritemoviesapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by bajak on 16/03/2018.
 */

public class DatabaseContract {

    public static final String AUTHORITY = "com.infolabsolution.cataloguemovie01";
    public static String TABLE_NAME = "favorite";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static final class FavoriteColumns implements BaseColumns {
        public static String COLUMN_TITLE = "title";
        public static String COLUMN_POSTER = "poster";
        public static String COLUMN_DATE = "date";
        public static String COLUMN_VOTE = "vote";
        public static String COLUMN_OVERVIEW = "overview";
    }
}
