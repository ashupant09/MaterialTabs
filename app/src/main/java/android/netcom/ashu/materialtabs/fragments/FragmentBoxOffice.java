package android.netcom.ashu.materialtabs.fragments;


import android.netcom.ashu.materialtabs.App.MyApplication;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.adapter.AdapterBoxOffice;
import android.netcom.ashu.materialtabs.callbacks.BoxOfficeMoviesLoadedListener;
import android.netcom.ashu.materialtabs.extras.MovieSorter;
import android.netcom.ashu.materialtabs.extras.SortListener;
import android.netcom.ashu.materialtabs.logging.L;
import android.netcom.ashu.materialtabs.task.TaskLoadMoviesBoxOffice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.netcom.ashu.materialtabs.R;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SortListener, BoxOfficeMoviesLoadedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES = "state_movies";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Movies> moviesList = new ArrayList<>();
    private RecyclerView listMovieHits;
    private TextView textVolleyError;
    private AdapterBoxOffice adapterBoxOffice;


    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, moviesList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_box_office, container, false);
        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        if (savedInstanceState != null) {
            moviesList = savedInstanceState.getParcelableArrayList(STATE_MOVIES);

        } else {
            moviesList = MyApplication.getWritableDataBase().readMovies(1);
            if(moviesList.isEmpty()){
                new TaskLoadMoviesBoxOffice(this).execute();
            }
        }
        adapterBoxOffice.setMoviesList(moviesList);
        return view;
    }

    private void handleVolleyError(VolleyError error) {
        textVolleyError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NetworkError) {

            textVolleyError.setText(R.string.error_timeout);
        } else if (error instanceof NetworkError) {

            textVolleyError.setText(R.string.error_network);
        } else if (error instanceof ParseError) {

            textVolleyError.setText(R.string.error_parser);
        } else if (error instanceof AuthFailureError) {

            textVolleyError.setText(R.string.error_auth_failure);
        } else if (error instanceof ServerError) {

            textVolleyError.setText(R.string.error_auth_failure);
        }
    }

    @Override
    public void sortByName() {

        MovieSorter.getInstance().sortMovieByName(moviesList);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void sortByDate() {

        MovieSorter.getInstance().sortMovieByDate(moviesList);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void sortByRating() {

        MovieSorter.getInstance().sortMovieByRating(moviesList);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<Movies> listMovies) {
        L.t(getActivity(), "load boxoffice from fragment");
        moviesList = listMovies;
        adapterBoxOffice.setMoviesList(listMovies);
    }
}
