package android.netcom.ashu.materialtabs.json;

import android.netcom.ashu.materialtabs.logging.L;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by KamaL on 30-08-2016.
 */
public class Requestor {

    public static JSONObject sendJsonRequest(RequestQueue requestQueue, String url) {

        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                requestFuture, requestFuture
        );

        requestQueue.add(jsonObjectRequest);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            L.p(e+"");
        } catch (ExecutionException e) {
            L.p(e+"");
        } catch (TimeoutException e) {
            L.p(e+"");
        }

        return response;
    }
}
