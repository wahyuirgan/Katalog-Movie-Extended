package top.technopedia.myapplicationkatalogfilm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import top.technopedia.myapplicationkatalogfilm.DetailMovieActivity;
import top.technopedia.myapplicationkatalogfilm.Model.MovieList;
import top.technopedia.myapplicationkatalogfilm.R;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static top.technopedia.myapplicationkatalogfilm.BuildConfig.url_image;

public class NowUpAdapter extends RecyclerView.Adapter<NowUpAdapter.ViewHolder> {

    private ArrayList<MovieList> mMovieItem = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public NowUpAdapter(final Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieList> items) {
        mMovieItem = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mMovieItem == null) return 0;
        return mMovieItem.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.now_playing_models, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.judul.setText(mMovieItem.get(position).getTitle());
        holder.sinopsis.setText(mMovieItem.get(position).getOverview());
        holder.judul.setText(mMovieItem.get(position).getTitle());

        Picasso.with(context).load(url_image + mMovieItem.get(position)
                .getPoster_path()).placeholder(context.getResources()
                .getDrawable(R.drawable.ic_launcher_background))
                .error(context.getResources().getDrawable(R.drawable.ic_photo_black_24dp)).into(holder.poster);

        String retrieveDate = mMovieItem.get(position).getRelease_date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = date_format.parse(retrieveDate);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMM yyyy");
            String release_date = new_date_format.format(date);
            holder.tglrilis.setText(release_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.bagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (context.getString(R.string.bagi)) + " " + mMovieItem
                        .get(position)
                        .getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.suka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (context.getString(R.string.suka)) + " " + mMovieItem
                        .get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);


                intent.putExtra(DetailMovieActivity.EXTRA_TITLE, mMovieItem.get(position).getTitle());
                intent.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, mMovieItem.get(position).getOverview());
                intent.putExtra(DetailMovieActivity.EXTRA_POSTER_PATH, mMovieItem.get(position).getPoster_path());
                intent.putExtra(DetailMovieActivity.EXTRA_RATE, mMovieItem.get(position).getVote_average());
                intent.putExtra(DetailMovieActivity.EXTRA_ID, mMovieItem.get(position).getId());
                intent.putExtra(DetailMovieActivity.EXTRA_RELEASE_DATE, mMovieItem.get(position).getRelease_date());
                intent.putExtra(DetailMovieActivity.EXTRA_ORIGINAL_LANGUAGE, mMovieItem.get(position).getOriginal_language());
                intent.putExtra(DetailMovieActivity.EXTRA_BACKDROP_PATH, mMovieItem.get(position).getBackdrop_path());


                context.startActivity(intent);

            }
        });

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView judul;
        TextView sinopsis;
        TextView tglrilis;
        Button suka;
        Button bagi;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView =  itemView.findViewById(R.id.card_view);
            poster =  itemView.findViewById(R.id.poster);
            judul =  itemView.findViewById(R.id.judul);
            sinopsis =  itemView.findViewById(R.id.deskrip);
            tglrilis =  itemView.findViewById(R.id.tglrilis);
            suka =  itemView.findViewById(R.id.suka);
            bagi =  itemView.findViewById(R.id.bagi);
        }

    }
}