package com.infolabsolution.cataloguemovie01;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "d6d19834a416851ac3aac202b804dfd6";
    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private MaterialSearchBar searchBar;
    private List<String> suggestList = new ArrayList<>();

    private List<ListMovies> listMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if(!enabled){
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                startSearch(text.toString(), true, null, true);

            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

        loadRecyclerViewData();

    }


    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        String queryMovie = "captain";
        String url = BASE_URL + "search/movie?api_key=" + API_KEY + "&language=en-US&query=" + queryMovie;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                progressDialog.dismiss();

                Toast.makeText(MainActivity.this, "onSuccess Call", Toast.LENGTH_SHORT).show();
                try {
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

                    adapter = new RecyclerViewAdapter(listMovies, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
