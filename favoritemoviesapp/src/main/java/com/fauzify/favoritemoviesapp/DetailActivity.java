package com.fauzify.favoritemoviesapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    public static String LISTMOVIES = "Movies";


    public static String EXTRA_FAVORITE = "extra_favorite";
    public static String EXTRA_POSITION = "extra_position";
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;
    String posterPath = "/infolabsolution.jpg";
    //private int position;
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
    String title, overview, releaseDate, poster, voteAvg, voteCount;
    private Favorite favorite;
    private Boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ButterKnife.bind(this);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) favorite = new Favorite(cursor);
                cursor.close();
            }
        }
        tvTitle.setText(favorite.getTitle());
        tvOverview.setText(favorite.getOverview());
        tvVoteAvg.setText(favorite.getVote());
        tvVoteCount.setText(favorite.getVote());
        tvrelease.setText(favorite.getDate());

        posterPath = favorite.getPoster();

        Picasso.with(this).load(favorite.getPoster()).into(ivPoster);
        Picasso.with(this).load(favorite.getPoster()).into(ivPoster_Big);


//        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);
//
//        if (favorite != null){
//            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
//        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
