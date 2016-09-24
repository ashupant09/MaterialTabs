package android.netcom.ashu.materialtabs.fragments;


import android.netcom.ashu.materialtabs.App.MyApplication;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.adapter.AdaptorUpComing;
import android.netcom.ashu.materialtabs.callbacks.UpcomingMoviesListener;
import android.netcom.ashu.materialtabs.extras.MovieSorter;
import android.netcom.ashu.materialtabs.extras.SortListener;
import android.netcom.ashu.materialtabs.logging.L;
import android.netcom.ashu.materialtabs.task.TaskLoadMoviesUpcoming;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.netcom.ashu.materialtabs.R;

import java.util.ArrayList;


public class FragmentUpcoming extends Fragment implements SortListener, UpcomingMoviesListener{

    private RecyclerView listMovieUpcoming;
    private ArrayList<Movies> listUpComing = new ArrayList<>();
    private AdaptorUpComing adaptorUpComing;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES = "state_movies";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentUpcoming() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentUpcoming newInstance(String param1, String param2) {
        FragmentUpcoming fragment = new FragmentUpcoming();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, listUpComing);
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
        View view =  inflater.inflate(R.layout.fragment_fragment_upcoming, container, false);
        listMovieUpcoming = (RecyclerView) view.findViewById(R.id.listMoviesUpcoming);

        listMovieUpcoming.setLayoutManager(new LinearLayoutManager(getActivity()));
        adaptorUpComing = new AdaptorUpComing(getActivity());
        listMovieUpcoming.setAdapter(adaptorUpComing);

        if(savedInstanceState != null){
            L.t(getActivity(), "load from upcoming saved");
            listUpComing  = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        }else{
            listUpComing = MyApplication.getWritableDataBase().readMovies(2);
            if(listUpComing.isEmpty()){
                new TaskLoadMoviesUpcoming(this).execute();
                L.t(getActivity(), "load from upcoming task");
            }
        }
        adaptorUpComing.setMoviesList(listUpComing);
        return view;
    }

    @Override
    public void sortByName() {
        MovieSorter.getInstance().sortMovieByName(listUpComing);
        adaptorUpComing.notifyDataSetChanged();
    }

    @Override
    public void sortByDate() {

        MovieSorter.getInstance().sortMovieByDate(listUpComing);
        adaptorUpComing.notifyDataSetChanged();
    }

    @Override
    public void sortByRating() {

        MovieSorter.getInstance().sortMovieByRating(listUpComing);
        adaptorUpComing.notifyDataSetChanged();
    }

    @Override
    public void upcomingMoviesLoaded(ArrayList<Movies> listMovies) {
        L.t(getActivity(), "movielist Loaded");
        listUpComing = listMovies;
        adaptorUpComing.setMoviesList(listMovies);
    }
}
