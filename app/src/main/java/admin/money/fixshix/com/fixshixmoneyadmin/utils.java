package admin.money.fixshix.com.fixshixmoneyadmin;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
/**
 * Created by lenovo on 8/27/2017.
 */

public class utils {
    static public String URLwithParams (String url , HashMap<String,String> hashMap)
    {

        StringBuilder sb = new StringBuilder();
        for(HashMap.Entry<String, String> e : hashMap.entrySet()){
            if(sb.length() > 0){
                sb.append('&');
            }
            else
                sb.append('?');
            try {
                sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=').append(URLEncoder.encode(e.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        String s = url+(sb.toString());
        return s;
    }


    static  public Double SumwithPercent (String s)
    {

        Double percent = (Double)((Double.parseDouble(s.toString())*10)/100);
        percent = percent+ Double.parseDouble(s);
        return percent;
    }
    static  public Double double2decimal (Double d)
    {
        return Double.parseDouble(String.format("%.2f", d));
    }

    static public String id, name, contact,email;

    static public String scan_flow="";

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch ( Exception e)
        {

        }
    }

   }