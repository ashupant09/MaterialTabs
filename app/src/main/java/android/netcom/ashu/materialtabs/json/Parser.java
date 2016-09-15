package android.netcom.ashu.materialtabs.json;

import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.extras.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_CAST;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_ID;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_LINKS;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_REVIEWS;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_SELF;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_SIMILAR;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static android.netcom.ashu.materialtabs.extras.Keys.EndpointBoxOffice.KEY_TITLE;

/**
 * Created by KamaL on 30-08-2016.
 */
public class Parser {
    public static ArrayList<Movies> parseMoviesJSON(JSONObject response) {

        ArrayList<Movies> movieList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (response != null && response.length() > 0) {

            try {
                long id = -1;
                String title = Constants.NA;
                String releaseDate = Constants.NA;
                int audienceScore = -1;
                String synopsis = Constants.NA;
                String UrlThumbnail = Constants.NA;
                String urlSelf = Constants.NA;
                String urlCast = Constants.NA;
                String urlReview = Constants.NA;
                String urlSimilar = Constants.NA;

                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    JSONObject currentMovie = arrayMovies.getJSONObject(i);


                    if (Utils.contain(currentMovie, KEY_ID)) {
                        id = currentMovie.getLong(KEY_ID);
                    }

                    if (Utils.contain(currentMovie, KEY_TITLE)) {
                        title = currentMovie.getString(KEY_TITLE);
                    }

                    if (Utils.contain(currentMovie, KEY_RELEASE_DATES)) {
                        JSONObject objectReleaseDate = currentMovie.getJSONObject(KEY_RELEASE_DATES);

                        if (Utils.contain(objectReleaseDate, KEY_THEATER)) {
                            releaseDate = objectReleaseDate.getString(KEY_THEATER);
                        }
                    }

                    if (Utils.contain(currentMovie, KEY_RATINGS)) {
                        JSONObject objectRating = currentMovie.getJSONObject(KEY_RATINGS);

                        if (Utils.contain(objectRating, KEY_AUDIENCE_SCORE)) {

                            audienceScore = objectRating.getInt(KEY_AUDIENCE_SCORE);
                        }
                    }


                    if (Utils.contain(currentMovie, KEY_SYNOPSIS)) {
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }

                    if (Utils.contain(currentMovie, KEY_SYNOPSIS)) {
                        JSONObject objectPoster = currentMovie.getJSONObject(KEY_POSTERS);

                        if (Utils.contain(objectPoster, KEY_THUMBNAIL)) {
                            UrlThumbnail = objectPoster.getString(KEY_THUMBNAIL);
                        }
                    }

                    if(Utils.contain(currentMovie, KEY_LINKS)){
                        JSONObject linkObject = currentMovie.getJSONObject(KEY_LINKS);
                        if(Utils.contain(linkObject, KEY_SELF)){
                            urlSelf = linkObject.getString(KEY_SELF);
                        }
                        if(Utils.contain(linkObject, KEY_CAST)){

                            urlCast = linkObject.getString(KEY_CAST);
                        }
                        if(Utils.contain(linkObject, KEY_REVIEWS)){

                            urlReview = linkObject.getString(KEY_REVIEWS);
                        }
                        if(Utils.contain(linkObject, KEY_SIMILAR)){

                            urlSimilar = linkObject.getString(KEY_SIMILAR);
                        }
                    }


                    Movies movies = new Movies();
                    movies.setId(id);
                    movies.setTitle(title);
                    Date date = null;
                    try {
                        date = dateFormat.parse(releaseDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    movies.setReleaseDateTheater(date);
                    movies.setAudience_score(audienceScore);
                    movies.setSynopsis(synopsis);
                    movies.setThumbnail(UrlThumbnail);
                    movies.setUrlSelf(urlSelf);
                    movies.setUrlCast(urlCast);
                    movies.setUrlReviews(urlReview);
                    movies.setUrlSimilar(urlSimilar);

                    if (id != -1 && !title.equals(Constants.NA)) {
                        movieList.add(movies);
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movieList;
    }
}
