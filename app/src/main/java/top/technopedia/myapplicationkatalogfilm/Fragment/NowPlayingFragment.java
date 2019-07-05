package top.technopedia.myapplicationkatalogfilm.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.technopedia.myapplicationkatalogfilm.Loader.NowPlayAsyncTaskLoader;
import top.technopedia.myapplicationkatalogfilm.Model.MovieList;
import top.technopedia.myapplicationkatalogfilm.Adapter.NowUpAdapter;
import top.technopedia.myapplicationkatalogfilm.R;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieList>> {

    NowUpAdapter adapter;
    Context context;
    RecyclerView mRecyclerView;
    ArrayList<MovieList> nowPlayingData;


    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        context = view.getContext();

        mRecyclerView = view.findViewById(R.id.rv_now_playing);

        adapter = new NowUpAdapter(Objects.requireNonNull(getActivity()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        return view;

    }


    @NonNull
    @Override
    public Loader<ArrayList<MovieList>> onCreateLoader(int id, Bundle args) {
        return new NowPlayAsyncTaskLoader(getContext(), nowPlayingData);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieList>> loader, ArrayList<MovieList> nowPlayingData) {
        adapter.setData(nowPlayingData);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieList>> loader) {
        adapter.setData(null);

    }
}
