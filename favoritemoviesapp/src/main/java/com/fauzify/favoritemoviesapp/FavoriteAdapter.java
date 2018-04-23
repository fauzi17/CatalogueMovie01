package com.fauzify.favoritemoviesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fauzify.favoritemoviesapp.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewholder> {
    Context context;
    private Cursor listFavorites;
    private Activity activity;


    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public Cursor getListFavorites() {
        return listFavorites;
    }

    public void setListFavorites(Cursor listFavorites) {
        this.listFavorites = listFavorites;

    }

    @Override
    public FavoriteViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movies, parent, false);
        return new FavoriteViewholder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewholder holder, int position) {
        final Favorite favorite = getItem(position);
        holder.tvHead.setText(favorite.getTitle());
        holder.tvDate.setText(favorite.getDate());
        holder.tvOverview.setText(favorite.getOverview());

        Picasso.with(context)
                .load(favorite.getPoster())
                .into(holder.ivPoster);
        holder.cvMovies.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailActivity.class);
//                intent.putExtra(DetailActivity.EXTRA_POSITION, position);
//                intent.putExtra(DetailActivity.EXTRA_FAVORITE, getListFavorites().get(position));
                Uri uri = Uri.parse(CONTENT_URI + "/" + favorite.getId());
                intent.setData(uri);
                activity.startActivityForResult(intent, DetailActivity.REQUEST_UPDATE);
                //activity.finish();
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (listFavorites == null) return 0;
        return listFavorites.getCount();
    }

    private Favorite getItem(int position) {
        if (!listFavorites.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(listFavorites);
    }

    public class FavoriteViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_small)
        TextView tvHead;
        @BindView(R.id.tv_overview_small)
        TextView tvOverview;
        @BindView(R.id.tv_release_date_small)
        TextView tvDate;
        @BindView(R.id.iv_poster)
        ImageView ivPoster;
        @BindView(R.id.linear_layout_list)
        LinearLayout linearLayout;
        @BindView(R.id.cv_movies)
        CardView cvMovies;

        public FavoriteViewholder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

