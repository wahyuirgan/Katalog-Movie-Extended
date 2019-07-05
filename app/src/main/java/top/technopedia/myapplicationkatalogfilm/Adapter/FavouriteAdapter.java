package top.technopedia.myapplicationkatalogfilm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.technopedia.myapplicationkatalogfilm.DetailMovieActivity;
import top.technopedia.myapplicationkatalogfilm.Model.MovieList;
import top.technopedia.myapplicationkatalogfilm.R;

import static top.technopedia.myapplicationkatalogfilm.BuildConfig.url_image;
import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.CONTENT_URI;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MovieViewHolder> {

    private Cursor listMovies;
    private Context context;

    public FavouriteAdapter(Context context){
        this.context = context;
    }

    public void setListMovies(Cursor listMovies){
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_models, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final MovieList movieItem = getItem(position);
        holder.judul.setText(movieItem.getTitle());
        holder.sinopsis.setText(movieItem.getOverview());
        holder.judul.setText(movieItem.getTitle());

        Picasso.with(context).load(url_image + movieItem
                .getPoster_path()).placeholder(context.getResources()
                .getDrawable(R.drawable.ic_launcher_background))
                .error(context.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).into(holder.poster);

        String retrieveDate = movieItem.getRelease_date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = date_format.parse(retrieveDate);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMM yyyy");
            String release_date = new_date_format.format(date);
            holder.tglrilis.setText(release_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);

                intent.putExtra(DetailMovieActivity.EXTRA_TITLE, movieItem.getTitle());
                intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, movieItem.getOverview());
                intent.putExtra(DetailMovieActivity.EXTRA_POSTER_PATH, movieItem.getPoster_path());
                intent.putExtra(DetailMovieActivity.EXTRA_RATE, movieItem.getVote_average());
                intent.putExtra(DetailMovieActivity.EXTRA_ID, movieItem.getId());
                intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE, movieItem.getRelease_date());
                intent.putExtra(DetailMovieActivity.EXTRA_ORIGINAL_LANGUAGE, movieItem.getOriginal_language());
                intent.putExtra(DetailMovieActivity.EXTRA_BACKDROP_PATH, movieItem.getBackdrop_path());
                intent.putExtra(DetailMovieActivity.EXTRA_FAVORITE, 1);

                intent.putExtra("from", "FavouriteAdapter");
                intent.putExtra("item", movieItem);
                Uri uri = Uri.parse(CONTENT_URI+"/"+movieItem.getId());
                intent.setData(uri);
                context.startActivity(intent);


                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (listMovies == null){
            return 0;
        }
        return listMovies.getCount();
    }

    private MovieList getItem(int position){
        if (!listMovies.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new MovieList(listMovies);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView judul;
        TextView sinopsis;
        TextView tglrilis;
        CardView cardView;

        MovieViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            poster =  itemView.findViewById(R.id.poster);
            judul =  itemView.findViewById(R.id.judul);
            sinopsis =  itemView.findViewById(R.id.deskrip);
            tglrilis =  itemView.findViewById(R.id.tglrilis);
        }

    }
}
