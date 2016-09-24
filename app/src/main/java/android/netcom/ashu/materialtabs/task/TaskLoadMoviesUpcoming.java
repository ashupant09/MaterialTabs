package android.netcom.ashu.materialtabs.task;

import android.content.Context;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.callbacks.UpcomingMoviesListener;
import android.netcom.ashu.materialtabs.extras.MovieUtils;
import android.netcom.ashu.materialtabs.network.VolleySingleton;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

/**
 * Created by KamaL on 19-09-2016.
 */
public class TaskLoadMoviesUpcoming extends AsyncTask<Void, Void, ArrayList<Movies>> {

    private UpcomingMoviesListener upcomingMoviesListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMoviesUpcoming(UpcomingMoviesListener upcomingMoviesListener){
        this.upcomingMoviesListener = upcomingMoviesListener;
        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getRequestQueue();


    }
    @Override
    protected ArrayList<Movies> doInBackground(Void... params) {
       ArrayList<Movies> upcomingList = MovieUtils.loadUpcomingMovies(requestQueue);
        return upcomingList;
    }

    @Override
    protected void onPostExecute(ArrayList<Movies> movies) {
        super.onPostExecute(movies);
        if(upcomingMoviesListener != null){
            upcomingMoviesListener.upcomingMoviesLoaded(movies);
        }
    }
}
