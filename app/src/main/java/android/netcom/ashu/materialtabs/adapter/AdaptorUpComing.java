package android.netcom.ashu.materialtabs.adapter;

import android.content.Context;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.R;
import android.netcom.ashu.materialtabs.anim.AnimationUtils;
import android.netcom.ashu.materialtabs.extras.Constants;
import android.netcom.ashu.materialtabs.logging.L;
import android.netcom.ashu.materialtabs.network.VolleySingleton;
import android.support.v7.widget.RecyclerView;
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
 * Created by KamaL on 20-09-2016.
 */
public class AdaptorUpComing extends RecyclerView.Adapter<AdaptorUpComing.UpComingHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Movies> listMovies = new ArrayList<>();
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private int currentPosition = 0;
    private Context context;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public AdaptorUpComing(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setMoviesList(ArrayList<Movies> moviesList){
        listMovies = moviesList;
        notifyItemRangeChanged(0, moviesList.size());
    }
    @Override
    public UpComingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.upcoming_movie_row, parent, false);
        UpComingHolder upComingHolder = new UpComingHolder(view);
        return upComingHolder;
    }

    @Override
    public void onBindViewHolder(UpComingHolder holder, int position) {

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
        loadImage(holder,imageUrl);

        AnimationUtils.animate(holder, isDown);
    }

    public void loadImage(final UpComingHolder upComingHolder, String thumbnailURL){

        imageLoader.get(thumbnailURL, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                upComingHolder.movieThumbnail.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    @Override
    public int getItemCount() {
        L.t(context, "size is "+ listMovies.size());
        return listMovies.size();
    }

    public class UpComingHolder extends RecyclerView.ViewHolder{

        ImageView movieThumbnail;
        TextView movieTitle;
        TextView movieReleaseDate;
        RatingBar movieAudienceScore;

        public UpComingHolder(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.upMovieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.upMovieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.upMovieRelease);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.upMovieScore);

        }
    }
}
