package android.netcom.ashu.materialtabs.callbacks;

import android.netcom.ashu.materialtabs.POJO.Movies;

import java.util.ArrayList;

/**
 * Created by KamaL on 19-09-2016.
 */
public interface UpcomingMoviesListener {
    public void upcomingMoviesLoaded(ArrayList<Movies> listMovies);
}
