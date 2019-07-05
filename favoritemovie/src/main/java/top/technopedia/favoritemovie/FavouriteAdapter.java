package top.technopedia.favoritemovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static top.technopedia.favoritemovie.DatabaseContract.MovieColumns.OVERVIEW;
import static top.technopedia.favoritemovie.DatabaseContract.MovieColumns.RELEASE_DATE;
import static top.technopedia.favoritemovie.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static top.technopedia.favoritemovie.DatabaseContract.getColumnString;


public class FavouriteAdapter extends CursorAdapter {

    public FavouriteAdapter(Context context, Cursor c, boolean autoRequery){
        super(context, c, autoRequery);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_content_provider, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        if (cursor != null){
            TextView judul = view.findViewById(R.id.judul);
            TextView desc = view.findViewById(R.id.deskrip);
            TextView rilis = view.findViewById(R.id.tglrilis);

            String foundDate = getColumnString(cursor,RELEASE_DATE);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatOfDate = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = formatOfDate.parse(foundDate);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormatDate =  new SimpleDateFormat("EEEE, dd MMM yyyy");
                String releaseDate = newFormatDate.format(date);
                rilis.setText(releaseDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            judul.setText(getColumnString(cursor,TITLE_MOVIE));
            desc.setText(getColumnString(cursor,OVERVIEW));

        }

    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

}
