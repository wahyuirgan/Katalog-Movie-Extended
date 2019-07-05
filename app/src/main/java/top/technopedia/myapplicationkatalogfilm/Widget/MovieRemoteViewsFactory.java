package top.technopedia.myapplicationkatalogfilm.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import top.technopedia.myapplicationkatalogfilm.DatabaseContract;
import top.technopedia.myapplicationkatalogfilm.Model.MovieList;
import top.technopedia.myapplicationkatalogfilm.R;

import java.util.concurrent.ExecutionException;

import static top.technopedia.myapplicationkatalogfilm.BuildConfig.url_image;


public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;

    int mAppWidgetId;

    private Cursor cursor;

    public MovieRemoteViewsFactory(Context applicationContext, Intent intent) {
        this.mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private MovieList getFav(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

      return new MovieList(cursor);
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                DatabaseContract.CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(position)){
            return null;
        }

        MovieList movieFavorite = getFav(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.movie_widget_item);

        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(url_image+movieFavorite.getBackdrop_path())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }

        Log.d("Widgetku",movieFavorite.getTitle());

        rv.setImageViewBitmap(R.id.img_widget,bmp);
        rv.setTextViewText(R.id.tv_movie_title, movieFavorite.getTitle());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
