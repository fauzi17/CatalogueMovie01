package com.infolabsolution.cataloguemovie01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    public static String TITLE = "IDS";
    public static String VOTE_AVG = "9.9";
    public static String VOTE_COUNT = "9999";
    public static String OVERVIEW = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
            " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ";
    public static String RELEASE = "2018-01-02";
    public static String POSTER_PATH = "/infolabsolution.jgp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvOverview = (TextView) findViewById(R.id.tv_overview);
        TextView tvVoteAvg = (TextView) findViewById(R.id.tv_vote_avg);
        TextView tvVoteCount = (TextView) findViewById(R.id.tv_vote_count);
        TextView tvrelease = (TextView) findViewById(R.id.tv_release_date);
        ImageView ivPoster = (ImageView) findViewById(R.id.iv_poster_detail);

        String title = getIntent().getStringExtra(TITLE);
        String overview = getIntent().getStringExtra(OVERVIEW);
        String voteAvg = getIntent().getStringExtra(VOTE_AVG);
        String voteCount = getIntent().getStringExtra(VOTE_COUNT);
        String releaseDate = getIntent().getStringExtra(RELEASE);
        String poster = getIntent().getStringExtra(POSTER_PATH);


        tvTitle.setText(title);
        tvOverview.setText(overview);
        tvVoteAvg.setText(voteAvg);
        tvVoteCount.setText(voteCount);
        tvrelease.setText(DateTime.getLongDate(releaseDate));

        Picasso.with(this).load(poster).into(ivPoster);


    }
}
