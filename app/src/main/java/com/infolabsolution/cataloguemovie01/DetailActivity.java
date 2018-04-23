package com.infolabsolution.cataloguemovie01;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.infolabsolution.cataloguemovie01.database.Favorite;
import com.infolabsolution.cataloguemovie01.database.FavoriteHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.CONTENT_URI;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_DATE;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_OVERVIEW;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_POSTER;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_TITLE;
import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.FavoriteColumns.COLUMN_VOTE;

public class DetailActivity extends AppCompatActivity {
    public static String LISTMOVIES = "Movies";


    public static String EXTRA_FAVORITE = "extra_favorite";
    public static String EXTRA_POSITION = "extra_position";
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    ListMovies mListMovies;
    String posterPath = "/infolabsolution.jpg";
    @BindView(R.id.tb_favorite)
    ToggleButton tbFavorite;
    String title, overview, releaseDate, poster, voteAvg, voteCount;

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
    private Favorite favorite;
    //private int position;
    private FavoriteHelper favoriteHelper;
    private Boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mListMovies = getIntent().getParcelableExtra(LISTMOVIES);
        //favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);

        ButterKnife.bind(this);

        tbFavorite.setTextOff("Added to Favorite");
        tbFavorite.setTextOn("Remove from Favorite");

        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) favorite = new Favorite(cursor);
                cursor.close();
            }
        }

        if (favorite == null) Log.d("favorite", "is Null");

        if (mListMovies != null) {
            tbFavorite.setChecked(false);
            isOfffavorite();
            tvTitle.setText(mListMovies.getTitle());
            tvOverview.setText(mListMovies.getOverview());
            tvVoteAvg.setText(mListMovies.getVoteAvg());
            tvVoteCount.setText(mListMovies.getVoteCount());
            tvrelease.setText(DateTime.getDate(mListMovies.getRelease()));
            posterPath = mListMovies.getImageUrl();

            Picasso.with(this).load(mListMovies.getImageUrl()).into(ivPoster);
            Picasso.with(this).load(mListMovies.getImageUrl()).into(ivPoster_Big);
        } else {
            tbFavorite.setChecked(true);
            isOnFavorite();
            tvTitle.setText(favorite.getTitle());
            tvOverview.setText(favorite.getOverview());
            tvVoteAvg.setText(favorite.getVote());
            tvVoteCount.setText(favorite.getVote());
            tvrelease.setText(DateTime.getDate(favorite.getDate()));

            posterPath = favorite.getPoster();

            Picasso.with(this).load(favorite.getPoster()).into(ivPoster);
            Picasso.with(this).load(favorite.getPoster()).into(ivPoster_Big);
        }


//        favorite = getIntent().getParcelableExtra(EXTRA_FAVORITE);
//
//        if (favorite != null){
//            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
//        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null) {
            favoriteHelper.close();
        }
    }


    public void onToggleClicked(View view) {
        boolean on = tbFavorite.isChecked();
        if (on) {
            isOnFavorite();
            addFavorite();
            Log.d("tambah", "tb is checked");
        } else {
            isOfffavorite();
            removeFavorite();
            Log.d("hapus", "tb is unchecked");
        }

    }

    private void isOnFavorite() {

        tbFavorite.setBackgroundColor(getResources().getColor(R.color.colorAccent));

    }

    private void isOfffavorite() {
        tbFavorite.setBackgroundColor(getResources().getColor(R.color.colorGray));
    }

    private void addFavorite() {
        isFavorite = true;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, tvTitle.getText().toString().trim());
        contentValues.put(COLUMN_POSTER, posterPath);
        contentValues.put(COLUMN_DATE, tvrelease.getText().toString().trim());
        contentValues.put(COLUMN_VOTE, tvVoteAvg.getText().toString().trim());
        contentValues.put(COLUMN_OVERVIEW, tvOverview.getText().toString().trim());

        Favorite newfavorite = new Favorite();
        newfavorite.setTitle(tvTitle.getText().toString().trim());
        newfavorite.setPoster(posterPath);
        newfavorite.setDate(tvrelease.getText().toString().trim());
        newfavorite.setVote(tvVoteAvg.getText().toString().trim());
        newfavorite.setOverview(tvOverview.getText().toString().trim());

        //favoriteHelper.insert(newfavorite);

        getContentResolver().insert(CONTENT_URI, contentValues);
        setResult(RESULT_ADD);

//        ContentValues contentValues = new ContentValues();
//        contentValues.put(FavoriteFields.COLUMN_TITLE, title);
//        contentValues.put(FavoriteFields.COLUMN_DATE, releaseDate);
//        contentValues.put(FavoriteFields.COLUMN_POSTER, poster);
//        contentValues.put(FavoriteFields.COLUMN_VOTE, voteAvg);
//        contentValues.put(FavoriteFields.COLUMN_OVERVIEW, overview);
//
//
//        getContentResolver().insert(CONTENT_URI, contentValues);
        Toast.makeText(this, "ADDED TO FAVORITE LIST", Toast.LENGTH_SHORT).show();
    }

    private void removeFavorite() {

        isFavorite = false;
        getContentResolver().delete(getIntent().getData(), null, null);
        setResult(RESULT_DELETE, null);
//        favoriteHelper.delete(favorite.getTitle());
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_POSITION, position);
//        setResult(RESULT_DELETE, intent);
//        getContentResolver().delete(
//                Uri.parse(CONTENT_URI + "/" + title),
//                null,
//                null
//        );
        Toast.makeText(this, "REMOVED FROM FAVORITE LIST", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
