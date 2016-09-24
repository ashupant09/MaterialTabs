package android.netcom.ashu.materialtabs.services;

import android.netcom.ashu.materialtabs.App.MyApplication;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.callbacks.BoxOfficeMoviesLoadedListener;
import android.netcom.ashu.materialtabs.callbacks.UpcomingMoviesListener;
import android.netcom.ashu.materialtabs.extras.Constants;
import android.netcom.ashu.materialtabs.logging.L;
import android.netcom.ashu.materialtabs.network.VolleySingleton;
import android.netcom.ashu.materialtabs.task.TaskLoadMoviesBoxOffice;
import android.os.AsyncTask;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;


/**
 * Created by KamaL on 30-08-2016.
 */
public class MyService extends JobService implements BoxOfficeMoviesLoadedListener{

    private JobParameters jobParameters;
    @Override
    public boolean onStartJob(JobParameters params) {
        L.m(this, "job started");
        this.jobParameters = params;
        new TaskLoadMoviesBoxOffice(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }

    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<Movies> listMovies) {
        L.p("onBoxOfficeMovie Loaded");
        jobFinished(jobParameters, false);
    }

}
