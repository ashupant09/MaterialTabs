package android.netcom.ashu.materialtabs.task;

import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.callbacks.BoxOfficeMoviesLoadedListener;
import android.netcom.ashu.materialtabs.extras.MovieUtils;
import android.netcom.ashu.materialtabs.network.VolleySingleton;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

/**
 * Created by KamaL on 30-08-2016.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void, Void, ArrayList<Movies>> {

    private BoxOfficeMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener myComponent){
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }
    @Override
    protected ArrayList<Movies> doInBackground(Void... params) {
        ArrayList<Movies> movieList = MovieUtils.loadBoxOfficeMovies(requestQueue);
        return movieList;
    }

    @Override
    protected void onPostExecute(ArrayList<Movies> movies) {
        super.onPostExecute(movies);
        if(myComponent != null){
            myComponent.onBoxOfficeMoviesLoaded(movies);
        }
    }
}
