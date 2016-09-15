package android.netcom.ashu.materialtabs.extras;

import android.netcom.ashu.materialtabs.POJO.Movies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by KamaL on 30-08-2016.
 */
public class MovieSorter {

    private static MovieSorter sInstance;
    private MovieSorter(){

    }

    public static MovieSorter getInstance(){
        if(sInstance == null){
            sInstance = new MovieSorter();
        }
        return sInstance;
    }

    public void sortMovieByName(ArrayList<Movies> movieList){

        Collections.sort(movieList, new Comparator<Movies>() {
            @Override
            public int compare(Movies lhs, Movies rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public void sortMovieByDate(ArrayList<Movies> moviesList){

        Collections.sort(moviesList, new Comparator<Movies>() {
            @Override
            public int compare(Movies lhs, Movies rhs) {
                return rhs.getReleaseDateTheater().compareTo(lhs.getReleaseDateTheater());
            }
        });
    }

    public void sortMovieByRating(ArrayList<Movies> moviesList){
        Collections.sort(moviesList, new Comparator<Movies>() {
            @Override
            public int compare(Movies lhs, Movies rhs) {

                int lhsRating = lhs.getAudience_score();
                int rhsRating = rhs.getAudience_score();

                if(lhsRating < rhsRating){
                    return 1;
                }else if(lhsRating > rhsRating){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
    }
}
