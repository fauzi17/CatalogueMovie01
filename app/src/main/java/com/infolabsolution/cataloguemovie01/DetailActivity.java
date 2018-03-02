package com.infolabsolution.cataloguemovie01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    public static String LISTMOVIES = "Movies";

    public static String TITLE = "IDS";
    public static String VOTE_AVG = "9.9";
    public static String VOTE_COUNT = "9999";
    public static String OVERVIEW = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
            " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ";
    public static String RELEASE = "2018-01-02";
    public static String POSTER_PATH = "/infolabsolution.jgp";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.tv_vote_avg)
    TextView tvVoteAvg;
    @BindView(R.id.tv_vote_count)
    TextView tvVoteCount;
    @BindView(R.id.tv_release_date)
    TextView tvrelease;
    @BindView(R.id.iv_poster_detail)
    ImageView ivPoster;
    @BindView(R.id.iv_poster_big)
    ImageView ivPoster_Big;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        ListMovies mListMovies = getIntent().getParcelableExtra(LISTMOVIES);

        String title = getIntent().getStringExtra(TITLE);
        String overview = getIntent().getStringExtra(OVERVIEW);
        String voteAvg = getIntent().getStringExtra(VOTE_AVG);
        String voteCount = getIntent().getStringExtra(VOTE_COUNT);
        String releaseDate = getIntent().getStringExtra(RELEASE);
        String poster = getIntent().getStringExtra(POSTER_PATH);


        tvTitle.setText(mListMovies.getTitle());
        tvOverview.setText(mListMovies.getOverview());
        tvVoteAvg.setText(mListMovies.getVoteAvg());
        tvVoteCount.setText(mListMovies.getVoteCount());
        tvrelease.setText(DateTime.getDate(mListMovies.getRelease()));

        Picasso.with(this).load(mListMovies.getImageUrl()).into(ivPoster);
        Picasso.with(this).load(mListMovies.getImageUrl()).into(ivPoster_Big);


    }
}
