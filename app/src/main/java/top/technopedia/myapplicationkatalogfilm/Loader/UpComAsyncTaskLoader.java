package top.technopedia.myapplicationkatalogfilm.Loader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import top.technopedia.myapplicationkatalogfilm.Model.MovieList;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static top.technopedia.myapplicationkatalogfilm.BuildConfig.API_KEY;

public class UpComAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieList>> {

    @Nullable
    private ArrayList<MovieList> mData;
    private boolean mHasResult = false;


    public UpComAsyncTaskLoader(final Context context, ArrayList<MovieList> mData) {
        super(context);
        onForceLoad();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieList> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResource(mData);
            mData = null;
            mHasResult = false;
        }
    }


    private void onReleaseResource(ArrayList<MovieList> mData) {
    }


    @Override
    public ArrayList<MovieList> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieList> movie_items = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject film = list.getJSONObject(i);
                        MovieList movieItems = new MovieList(film);
                        movie_items.add(movieItems);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movie_items;
    }
}

