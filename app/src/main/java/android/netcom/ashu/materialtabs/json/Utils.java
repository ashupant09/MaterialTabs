package android.netcom.ashu.materialtabs.json;

import org.json.JSONObject;

/**
 * Created by KamaL on 30-08-2016.
 */
public class Utils {

    public static boolean contain(JSONObject jsonObject, String key) {

        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }
}
