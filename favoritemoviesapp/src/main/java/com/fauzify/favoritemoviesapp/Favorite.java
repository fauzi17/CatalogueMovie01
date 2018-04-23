package com.fauzify.favoritemoviesapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.fauzify.favoritemoviesapp.DatabaseContract.getColumnInt;
import static com.fauzify.favoritemoviesapp.DatabaseContract.getColumnString;

public class Favorite implements Parcelable {

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
    private int id;
    private String title;
    private String Poster;
    private String date;
    private String vote;
    private String overview;

    public Favorite() {

    }

    public Favorite(int id, String title, String poster, String date, String vote, String overview) {
        this.id = id;
        this.title = title;
        this.Poster = poster;
        this.date = date;
        this.vote = vote;
        this.overview = overview;
    }

    public Favorite(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_TITLE);
        this.Poster = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_POSTER);
        this.date = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_DATE);
        this.vote = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_VOTE);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_OVERVIEW);
    }

    protected Favorite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.Poster = in.readString();
        this.date = in.readString();
        this.vote = in.readString();
        this.overview = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.Poster);
        dest.writeString(this.date);
        dest.writeString(this.vote);
        dest.writeString(this.overview);
    }
}
