package top.technopedia.myapplicationkatalogfilm.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Objects;


import top.technopedia.myapplicationkatalogfilm.Adapter.MyMovieAdapter;
import top.technopedia.myapplicationkatalogfilm.Loader.MyMovieAsyncTaskLoader;
import top.technopedia.myapplicationkatalogfilm.Model.MovieList;
import top.technopedia.myapplicationkatalogfilm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieList>> {

    private RecyclerView recyclerView;
    private MyMovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private String search_title;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MyMovieAdapter(Objects.requireNonNull(getActivity()));
        search_title = "&query=Captain America";
        getActivity().getSupportLoaderManager().initLoader(2, null, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).getSupportLoaderManager().restartLoader(2, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setMaxWidth(R.dimen.search_max_width);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search_title = "&query="+query;
                submit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void submit() {
        Objects.requireNonNull(getActivity()).getSupportLoaderManager().restartLoader(2, null, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieList>> onCreateLoader(int id, Bundle args) {
        return new MyMovieAsyncTaskLoader(getActivity(), search_title);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieList>> loader, ArrayList<MovieList> data) {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        movieAdapter.setMovieItems(data);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieList>> loader) {
        movieAdapter.clearMovie();
    }
}