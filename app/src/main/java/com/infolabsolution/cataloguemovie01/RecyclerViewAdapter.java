package com.infolabsolution.cataloguemovie01;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bajak on 12/02/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ListMovies> listMovies;
    private Context context;

    public RecyclerViewAdapter(List<ListMovies> listMovies, Context context) {
        this.listMovies = listMovies;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movies, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, final int position) {
        final ListMovies listMovies = this.listMovies.get(position);

        holder.tvHead.setText(listMovies.getTitle());
        holder.tvDesc.setText(DateTime.getLongDate(listMovies.getRelease()));

        Picasso.with(context)
                .load(listMovies.getImageUrl())
                .into(holder.ivPoster);

        holder.linearLayout.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Detail "+ listMovies.getTitle(),Toast.LENGTH_LONG).show();

                Intent moveIntent = new Intent(context, DetailActivity.class);

                moveIntent.putExtra(DetailActivity.TITLE, listMovies.getTitle());
                moveIntent.putExtra(DetailActivity.OVERVIEW, listMovies.getOverview());
                moveIntent.putExtra(DetailActivity.VOTE_AVG, listMovies.getVoteAvg());
                moveIntent.putExtra(DetailActivity.VOTE_COUNT, listMovies.getVoteCount());
                moveIntent.putExtra(DetailActivity.RELEASE, listMovies.getRelease());
                moveIntent.putExtra(DetailActivity.POSTER_PATH, listMovies.getImageUrl());

                context.startActivity(moveIntent);

            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvHead;
        public TextView tvDesc;
        public ImageView ivPoster;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            tvHead = (TextView) itemView.findViewById(R.id.tv_head);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_list);
        }
    }
}