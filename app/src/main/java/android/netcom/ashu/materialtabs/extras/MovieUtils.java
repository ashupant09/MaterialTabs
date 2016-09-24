package android.netcom.ashu.materialtabs.extras;

import android.netcom.ashu.materialtabs.App.MyApplication;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.json.EndPoints;
import android.netcom.ashu.materialtabs.json.Parser;
import android.netcom.ashu.materialtabs.json.Requestor;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KamaL on 30-08-2016.
 */
public class MovieUtils {

    public static ArrayList<Movies> loadBoxOfficeMovies(RequestQueue requestQueue){
        Log.w("URL", "box office url is ---> "+ EndPoints.getCurrentBoxOfficeRequestUrl(30, 1) );
        JSONObject response = Requestor.sendJsonRequest(requestQueue, EndPoints.getCurrentBoxOfficeRequestUrl(30, 1));
        ArrayList<Movies> movieList = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDataBase().insertMovies(movieList, true, 1);

        return movieList;
    }

    public static ArrayList<Movies> loadUpcomingMovies(RequestQueue requestQueue){
        Log.w("URL", "url is ---> "+ EndPoints.getCurrentBoxOfficeRequestUrl(30, 2) );
        JSONObject response = Requestor.sendJsonRequest(requestQueue, EndPoints.getCurrentBoxOfficeRequestUrl(30, 2));
        ArrayList<Movies> movieList = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDataBase().insertMovies(movieList, true, 2);
        return movieList;
    }
}
