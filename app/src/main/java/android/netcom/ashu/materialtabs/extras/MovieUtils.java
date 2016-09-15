package android.netcom.ashu.materialtabs.extras;

import android.netcom.ashu.materialtabs.App.MyApplication;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.json.EndPoints;
import android.netcom.ashu.materialtabs.json.Parser;
import android.netcom.ashu.materialtabs.json.Requestor;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KamaL on 30-08-2016.
 */
public class MovieUtils {

    public static ArrayList<Movies> loadBoxOfficeMovies(RequestQueue requestQueue){
        JSONObject response = Requestor.sendJsonRequest(requestQueue, EndPoints.getCurrentBoxOfficeRequestUrl(30));
        ArrayList<Movies> movieList = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDataBase().insertMovies(movieList, true);

        return movieList;
    }
}
