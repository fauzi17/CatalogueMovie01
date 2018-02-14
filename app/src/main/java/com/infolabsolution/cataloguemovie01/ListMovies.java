package com.infolabsolution.cataloguemovie01;

/**
 * Created by bajak on 12/02/2018.
 */

public class ListMovies {

    private String title;
    private String voteAvg;
    private String voteCount;
    private String overview;
    private String release;
    private String imageUrl;
    private static final String BASE_URL_IMG = "http://image.tmdb.org/t/p/w154";

    public ListMovies(String title, String voteAvg, String voteCount, String overview, String release, String imageUrl) {
        this.title = title;
        this.voteAvg = voteAvg;
        this.voteCount = voteCount;
        this.overview = overview;
        this.release = release;
        this.imageUrl = BASE_URL_IMG + imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease() {
        return release;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static String getBaseUrlImg() {
        return BASE_URL_IMG;
    }

}
