package android.netcom.ashu.materialtabs.json;

import android.netcom.ashu.materialtabs.App.MyApplication;

import static android.netcom.ashu.materialtabs.extras.UrlEndpoints.URL_BOX_OFFICE;
import static android.netcom.ashu.materialtabs.extras.UrlEndpoints.URL_UPCOMING;
import static android.netcom.ashu.materialtabs.extras.UrlEndpoints.URL_CHAR_AMEPERSAND;
import static android.netcom.ashu.materialtabs.extras.UrlEndpoints.URL_CHAR_QUESTION;
import static android.netcom.ashu.materialtabs.extras.UrlEndpoints.URL_PARAM_API_KEY;
import static android.netcom.ashu.materialtabs.extras.UrlEndpoints.URL_PARAM_LIMIT;
import static android.netcom.ashu.materialtabs.extras.UrlEndpoints.URL_PAGE_LIMIT;
/**
 * Created by KamaL on 30-08-2016.
 */
public class EndPoints {

    public static String getCurrentBoxOfficeRequestUrl(int limit, int moviesType) {

        String targetURL = null;
        if (moviesType == 1) {
            targetURL =  URL_BOX_OFFICE +
                    URL_CHAR_QUESTION +
                    URL_PARAM_API_KEY +
                    MyApplication.API_KEY_ROTTEN_TOMATOES +
                    URL_CHAR_AMEPERSAND +
                    URL_PARAM_LIMIT +
                    limit;
        }else{
            targetURL =  URL_UPCOMING +
                    URL_CHAR_QUESTION +
                    URL_PARAM_API_KEY +
                    MyApplication.API_KEY_ROTTEN_TOMATOES +
                    URL_CHAR_AMEPERSAND +
                    URL_PAGE_LIMIT +
                    limit;
        }

        return targetURL;
    }
}
