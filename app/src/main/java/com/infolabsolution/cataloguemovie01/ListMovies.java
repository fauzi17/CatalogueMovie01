package com.infolabsolution.cataloguemovie01;

public class ListMovies {


    private static final String BASE_URL_IMG = "http://image.tmdb.org/t/p/w342";
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
        this.imageUrl = BASE_URL_IMG + imageUrl;


    }

    ListMovies(String total_results, String total_pages) {
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public static String getBaseUrlImg() {
        return BASE_URL_IMG;
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

}
