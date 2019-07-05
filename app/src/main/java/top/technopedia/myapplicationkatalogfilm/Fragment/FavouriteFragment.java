package top.technopedia.myapplicationkatalogfilm.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import top.technopedia.myapplicationkatalogfilm.Adapter.FavouriteAdapter;
import top.technopedia.myapplicationkatalogfilm.R;

import static top.technopedia.myapplicationkatalogfilm.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private Cursor list;
    private FavouriteAdapter adapter;
    private RecyclerView rvNotes;
    private ProgressBar progressBar;
    private Context context;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        context = view.getContext();

        rvNotes = view.findViewById(R.id.recycler_view);
        rvNotes.setLayoutManager(new LinearLayoutManager(context));
        rvNotes.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progress_bar);

        adapter = new FavouriteAdapter(getContext());
        adapter.setListMovies(list);
        rvNotes.setAdapter(adapter);

        new LoadMovieAsync().execute();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list = notes;
            adapter.setListMovies(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                showSnackbarMessage();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showSnackbarMessage(){
        Snackbar.make(rvNotes, R.string.nofav, Snackbar.LENGTH_SHORT).show();
    }

}
