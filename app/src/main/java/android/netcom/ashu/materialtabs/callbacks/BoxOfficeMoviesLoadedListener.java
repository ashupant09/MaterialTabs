package android.netcom.ashu.materialtabs.callbacks;

import android.netcom.ashu.materialtabs.POJO.Movies;

import java.util.ArrayList;

/**
 * Created by KamaL on 30-08-2016.
 */
public interface BoxOfficeMoviesLoadedListener {
    public void onBoxOfficeMoviesLoaded(ArrayList<Movies> listMovies);
}
