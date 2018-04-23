package com.infolabsolution.cataloguemovie01.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_DATE;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_OVERVIEW;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_POSTER;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_TITLE;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_VOTE;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.TABLE_NAME;

/**
 * Created by bajak on 15/03/2018.
 */

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    //    public Cursor queryProvider() {
//        return database.query(
//                DATABASE_TABLE,
//                null,
//                null,
//                null,
//                null,
//                null,
//                _ID + " DESC"
//        );
//    }
//
//    public Cursor queryByIdProvider(String id) {
//        return database.query(
//                DATABASE_TABLE,
//                null,
//                _ID + " = ?",
//                new String[]{id},
//                null,
//                null,
//                null
//        );
//    }
//
//    public long insertProvider(ContentValues values) {
//        return database.insert(
//                DATABASE_TABLE,
//                null,
//                values
//        );
//    }
//
//    public int updateProvider(String id, ContentValues values) {
//        return database.update(
//                DATABASE_TABLE,
//                values,
//                _ID + " = ?",
//                new String[]{id}
//        );
//    }
//
//    public int deleteProvider(String id) {
//        return database.delete(
//                DATABASE_TABLE,
//                _ID + " = ?",
//                new String[]{id}
//        );
//    }
//
    public ArrayList<Favorite> query() {
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        Favorite favorite;

        if (cursor.getCount() > 0) {
            do {

                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                favorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER)));
                favorite.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                favorite.setVote(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VOTE)));
                favorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)));


                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Favorite favorite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, favorite.getTitle());
        contentValues.put(COLUMN_POSTER, favorite.getPoster());
        contentValues.put(COLUMN_DATE, favorite.getDate());
        contentValues.put(COLUMN_VOTE, favorite.getVote());
        contentValues.put(COLUMN_OVERVIEW, favorite.getOverview());

        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public int update(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_TITLE, favorite.getTitle());
        args.put(COLUMN_POSTER, favorite.getPoster());
        args.put(COLUMN_DATE, favorite.getDate());
        args.put(COLUMN_VOTE, favorite.getVote());
        args.put(COLUMN_OVERVIEW, favorite.getOverview());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + favorite.getId() + "'", null);
    }

    public int delete(String id) {
        return database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

}
