package com.infolabsolution.cataloguemovie01;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.infolabsolution.cataloguemovie01.database.DatabaseContract;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.infolabsolution.cataloguemovie01.database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity
        implements MaterialSearchBar.OnSearchActionListener {

    @BindView(R.id.tv_total_movies)
    TextView tvTotalMovies;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.search_bar)
    MaterialSearchBar searchBar;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private Cursor list;
    //private FavoriteHelper favoriteHelper;
    //private LinkedList<Favorite> list;


    private String query = "code";
    private RecyclerView.Adapter adapter;
    private FavoriteAdapter favoriteAdapter;
    private List<ListMovies> listMovies;
    private int currentPage = 1;
    private int totalPages = 1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_nav1:
                    onClickHomeNav();
                    return true;

                case R.id.bottom_nav2:
                    onClickUpcomingNav();
                    return true;

                case R.id.bottom_nav3:
                    onClickNowplayingNav();
                    return true;

                case R.id.bottom_nav4:
                    onFavoriteNav();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //ActionBar actionBar = getActionBar();
        onClickHomeNav();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickHomeNav() {
        onCreateVariable();
        setTitle(getString(R.string.title_home));
        searchBar.setVisibility(View.VISIBLE);

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(query = text.toString(), true, null, false);
                loadRecyclerViewData(BuildConfig.BASE_URL, BuildConfig.DISCOVER, BuildConfig.SEARCH, BuildConfig.API_KEY);

            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

        if (listMovies.isEmpty()) {
            loadRecyclerViewData(BuildConfig.BASE_URL, BuildConfig.DISCOVER, BuildConfig.SEARCH, BuildConfig.API_KEY);
        }


    }

    public void onClickUpcomingNav() {
        onCreateVariable();

        searchBar.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        loadRecyclerViewData(BuildConfig.BASE_URL, BuildConfig.UPCOMING, BuildConfig.UPCOMING, BuildConfig.API_KEY);
        setTitle(getString(R.string.title_upcoming));
    }

    public void onClickNowplayingNav() {
        onCreateVariable();

        searchBar.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        loadRecyclerViewData(BuildConfig.BASE_URL, BuildConfig.NOW_PLAYING, BuildConfig.NOW_PLAYING, BuildConfig.API_KEY);
        setTitle(getString(R.string.title_nowplaying));
    }

    public void onFavoriteNav() {
        onCreateVariable();
        searchBar.setVisibility(View.GONE);
        setTitle(getString(R.string.title_favorite));

        //favoriteHelper = new FavoriteHelper(this);
        //favoriteHelper.open();
        //list = new LinkedList<>();

        list = getContentResolver().query(DatabaseContract.CONTENT_URI, null, null, null, null);

        favoriteAdapter = new FavoriteAdapter(this);
        favoriteAdapter.setListFavorites(list);

        recyclerView.setAdapter(favoriteAdapter);

        new LoadNoteAsync().execute();


    }

    private void loadRecyclerViewDatFavorite() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_data));
    }

    void onCreateVariable() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listMovies = new ArrayList<>();

        searchBar.setHint(getString(R.string.hint_seacrh_movies));
        searchBar.setCardViewElevation(10);
    }

    private void loadRecyclerViewData(String baseUrl, String emptyUrl, String searchUrl, String APIKey) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_data));
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        String url;

        if (listMovies.isEmpty()) {
            url = baseUrl + emptyUrl + APIKey;
            Log.d("isEmpty ", url);
        } else {
            listMovies.clear();
            url = baseUrl + searchUrl + APIKey + "&language=en-US&query=" + query;
            Log.d("query ", url);
        }

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                progressDialog.dismiss();

                try {
                    ListMovies item_result = new ListMovies(
                            response.getString("total_results"),
                            response.getString("total_pages")
                    );

                    JSONArray array = response.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        ListMovies item = new ListMovies(
                                o.getString("title"),
                                o.getString("vote_average"),
                                o.getString("vote_count"),
                                o.getString("overview"),
                                o.getString("release_date"),
                                o.getString("poster_path")
                        );
                        listMovies.add(item);
                    }

                    if (listMovies.isEmpty()) {
                        setEmptySearch();

                    } else {

                        adapter = new RecyclerViewAdapter(listMovies, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("onFailure AsycHttp ", Integer.toString(statusCode));
            }
        });

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    private void setEmptySearch() {
        tvTotalMovies.setText(R.string.no_movies_found);
        recyclerView.clearOnChildAttachStateChangeListeners();
    }


    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

//            if (list.size() > 0){
//                list.clear();
//            }
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorites) {
            super.onPostExecute(favorites);
            progressBar.setVisibility(View.GONE);

            list = favorites;
            favoriteAdapter.setListFavorites(list);
            favoriteAdapter.notifyDataSetChanged();

            if (list.getCount() == 0) {
                Toast.makeText(MainActivity.this, "Tidak ada data saat ini", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
