package top.technopedia.favoritemovie;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import static top.technopedia.favoritemovie.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FavouriteAdapter movieAdapter;
    private final int LOAD_NOTES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.lv_notes);
        movieAdapter = new FavouriteAdapter(this, null, true);
        listView.setAdapter(movieAdapter);
        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_NOTES_ID);
    }

}
