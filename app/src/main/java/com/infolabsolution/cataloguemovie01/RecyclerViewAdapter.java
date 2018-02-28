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

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ListMovies> listMovies;
    private Context context;


    RecyclerViewAdapter(List<ListMovies> listMovies, Context context) {
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

        holder.tvDesc.setText(listMovies.getOverview() + "  " + Locale.getDefault().getDisplayLanguage());
        holder.tvDesc2.setText(DateTime.getLongDate(listMovies.getRelease()));

        Picasso.with(context)
                .load(listMovies.getImageUrl())
                .into(holder.ivPoster);

        holder.linearLayout.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                Intent moveIntent = new Intent(context, DetailActivity.class);
                moveIntent.putExtra(DetailActivity.LISTMOVIES, listMovies);
                context.startActivity(moveIntent);

            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_small)
        TextView tvHead;
        @BindView(R.id.tv_overview_small)
        TextView tvDesc;
        @BindView(R.id.tv_release_date_small)
        TextView tvDesc2;
        @BindView(R.id.iv_poster)
        ImageView ivPoster;
        @BindView(R.id.linear_layout_list)
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
