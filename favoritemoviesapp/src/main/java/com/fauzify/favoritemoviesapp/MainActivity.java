package com.fauzify.favoritemoviesapp;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fauzify.favoritemoviesapp.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private Cursor list;
    private FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        list = getContentResolver().query(DatabaseContract.CONTENT_URI, null, null, null, null);

        favoriteAdapter.setListFavorites(list);
        recyclerView.setAdapter(favoriteAdapter);
        new LoadNoteAsync().execute();
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
