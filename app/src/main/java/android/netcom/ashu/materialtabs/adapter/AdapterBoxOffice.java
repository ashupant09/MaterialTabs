package android.netcom.ashu.materialtabs.adapter;

import android.content.Context;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.R;
import android.netcom.ashu.materialtabs.anim.AnimationUtils;
import android.netcom.ashu.materialtabs.extras.Constants;
import android.netcom.ashu.materialtabs.network.VolleySingleton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KamaL on 29-08-2016.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.BoxOfficeHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Movies> listMovies = new ArrayList<>();
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private int currentPosition = 0;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public AdapterBoxOffice(Context context){
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setMoviesList(ArrayList<Movies> moviesList){
        this.listMovies = moviesList;
        notifyItemRangeChanged(0, listMovies.size());
    }

    @Override
    public BoxOfficeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.custom_movie_box_office, parent, false);
        BoxOfficeHolder boxOfficeHolder = new BoxOfficeHolder(view);
        return boxOfficeHolder;
    }

    @Override
    public void onBindViewHolder(final BoxOfficeHolder holder, int position) {

        boolean isDown = false;
        if(position > currentPosition){
            isDown = true;
        }

        currentPosition = position;
        Movies movies = listMovies.get(position);
        holder.movieTitle.setText(movies.getTitle());

        Date movieReleaseDate = movies.getReleaseDateTheater();
        if(movieReleaseDate != null){

            String formatDate = dateFormat.format(movieReleaseDate);
            holder.movieReleaseDate.setText(formatDate);
        }else{
            holder.movieReleaseDate.setText(Constants.NA);
        }

        int audienceScore = movies.getAudience_score();
        if(audienceScore == -1){

            holder.movieAudienceScore.setRating(0.0f);
            holder.movieAudienceScore.setAlpha(0.5f);
        }else {
            holder.movieAudienceScore.setRating(movies.getAudience_score() / 20.0f);
            holder.movieAudienceScore.setAlpha(1.0f);
        }
        String imageUrl = movies.getThumbnail();
        loadImage(imageUrl, holder);

        AnimationUtils.animate(holder, isDown);
    }

    public void loadImage(String urlThumbnail, final BoxOfficeHolder boxOfficeHolder){
        if(urlThumbnail != null){
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                    boxOfficeHolder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class BoxOfficeHolder extends RecyclerView.ViewHolder{

        ImageView movieThumbnail;
        TextView movieTitle;
        TextView movieReleaseDate;
        RatingBar movieAudienceScore;
        public BoxOfficeHolder(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);

        }
    }
}
