package android.netcom.ashu.materialtabs.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by KamaL on 29-08-2016.
 */
public class L {

    public static void m(Context context, String message){

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void t(Context context, String message){

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void p(String message){
        Log.e("ASHU", message);
    }

}
