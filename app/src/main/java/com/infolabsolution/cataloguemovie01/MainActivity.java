package com.infolabsolution.cataloguemovie01;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements MaterialSearchBar.OnSearchActionListener {
    private static final String API_KEY = "d6d19834a416851ac3aac202b804dfd6";
    private static final String BASE_URL = "http://api.themoviedb.org/3";

    private String query = "code";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private TextView tvTotalMovies;

    private SwipeRefreshLayout swipe_refresh;

    private MaterialSearchBar searchBar;
    private List<String> suggestList = new ArrayList<>();

    private List<ListMovies> listMovies;

    private String movie_title = "";
    private int currentPage = 1;
    private int totalPages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalMovies = findViewById(R.id.tv_total_movies);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listMovies = new ArrayList<>();

        searchBar = findViewById(R.id.search_bar);
        searchBar.setHint("Search movies here...");
        searchBar.setCardViewElevation(10);
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
                loadRecyclerViewData();

            }

            @Override
            public void onButtonClicked(int buttonCode) {


            }
        });

        if (listMovies.isEmpty()) {
            loadRecyclerViewData();
        }

    }


    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        String url;

        if (listMovies.isEmpty()) {
            url = BASE_URL + "/discover/movie?api_key=" + API_KEY;

        } else {
            listMovies.clear();
            url = BASE_URL + "/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;
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

                        setTotalMovies(item_result);

                        adapter = new RecyclerViewAdapter(listMovies, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void setTotalMovies(ListMovies list) {
        String totalMovies = list.getTotal_results();
        tvTotalMovies.setText(totalMovies + " movies found. ");
    }

    private void setEmptySearch() {
        tvTotalMovies.setText("No movies found.");
        recyclerView.clearOnChildAttachStateChangeListeners();

    }
//
//    private void loadData(final String movie_title) {
//        getSupportActionBar().setSubtitle("");
//
//        if (movie_title.isEmpty()) apiCall = apiClient.getService().getPopularMovie(currentPage);
//        else apiCall = apiClient.getService().getSearchMovie(currentPage, movie_title);
//
//        apiCall.enqueue(new Callback<SearchModel>() {
//            @Override
//            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
//                if (response.isSuccessful()) {
//                    totalPages = response.body().getTotalPages();
//                    List<ResultsItem> items = response.body().getResults();
//                    showResults(response.body().getTotalResults());
//
//                    if (currentPage > 1) adapter.updateData(items);
//                    else adapter.replaceAll(items);
//
//                    stopRefrehing();
//                } else loadFailed();
//            }
//
//            @Override
//            public void onFailure(Call<SearchModel> call, Throwable t) {
//                loadFailed();
//            }
//        });
//    }

//    private void loadFailed() {
//        stopRefrehing();
//        Toast.makeText(MainActivity.this, "Failed to load data.\nPlease check your Internet connections!", Toast.LENGTH_SHORT).show();
//    }
//
//
//    private void startRefreshing() {
//        if (swipe_refresh.isRefreshing()) return;
//        swipe_refresh.setRefreshing(true);
//
////        loadData(movie_title);
//    }
//
//
//    private void stopRefrehing() {
//        if (swipe_refresh.isRefreshing()) {
//            swipe_refresh.setRefreshing(false);
//        }
//    }
//
//
//    @Override
//    public void onRefresh() {
//        currentPage = 1;
//        totalPages = 1;
//
//        stopRefrehing();
//        startRefreshing();
//
//    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
//        movie_title = String.valueOf(text);
//        onRefresh();
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }


}
