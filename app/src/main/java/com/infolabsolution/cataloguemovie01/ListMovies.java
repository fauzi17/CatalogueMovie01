package com.infolabsolution.cataloguemovie01;

import android.os.Parcel;
import android.os.Parcelable;

public class ListMovies implements Parcelable {


    public static final Parcelable.Creator<ListMovies> CREATOR = new Parcelable.Creator<ListMovies>() {
        @Override
        public ListMovies createFromParcel(Parcel source) {
            return new ListMovies(source);
        }

        @Override
        public ListMovies[] newArray(int size) {
            return new ListMovies[size];
        }
    };
    private String total_results;
    private String total_pages;
    private String title;
    private String voteAvg;
    private String voteCount;
    private String overview;
    private String release;
    private String imageUrl;

    ListMovies(String title, String voteAvg, String voteCount, String overview, String release, String imageUrl) {
        this.title = title;
        this.voteAvg = voteAvg;
        this.voteCount = voteCount;
        this.overview = overview;
        this.release = release;
        this.imageUrl = BuildConfig.BASE_URL_IMG + imageUrl;


    }

    ListMovies(String total_results, String total_pages) {
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    protected ListMovies(Parcel in) {
        this.total_results = in.readString();
        this.total_pages = in.readString();
        this.title = in.readString();
        this.voteAvg = in.readString();
        this.voteCount = in.readString();
        this.overview = in.readString();
        this.release = in.readString();
        this.imageUrl = in.readString();
    }

    String getTotal_results() {
        return total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public String getTitle() {
        return title;
    }

    String getVoteAvg() {
        return voteAvg;
    }

    String getVoteCount() {
        return voteCount;
    }

    String getOverview() {
        return overview;
    }

    String getRelease() {
        return release;
    }

    String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.total_results);
        dest.writeString(this.total_pages);
        dest.writeString(this.title);
        dest.writeString(this.voteAvg);
        dest.writeString(this.voteCount);
        dest.writeString(this.overview);
        dest.writeString(this.release);
        dest.writeString(this.imageUrl);
    }
}