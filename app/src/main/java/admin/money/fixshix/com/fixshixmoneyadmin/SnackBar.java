package admin.money.fixshix.com.fixshixmoneyadmin;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by lenovo on 8/26/2017.
 */

public class SnackBar {
    public static void makeCustomErrorSnack(Context c , String msg){
        View v = ((Activity)c).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(v, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(c, android.R.color.holo_red_dark));
        snackbar.show();

    }
    public static void makeCustomSnack(Context c , String msg){
        View v = ((Activity)c).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(v, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(c, R.color.mainColor));
        snackbar.show();
    }

}
