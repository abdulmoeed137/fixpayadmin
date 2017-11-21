package admin.money.fixshix.com.fixshixmoneyadmin;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.fabric.sdk.android.Fabric;

/**
 * Created by lenovo on 7/20/2017.
 */

public class LoginActivity extends AppCompatActivity {
    EditText seller_id , password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        if ( new SessionManager(LoginActivity.this).CheckIfSessionExist() )
        {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        }
        initialize();
        setUpComponents();

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_sellerI_id = seller_id. getText(). toString();
                String txt_password = password. getText() . toString();

                if (Validity.isIdTrue(txt_sellerI_id,LoginActivity.this) && Validity.isPasswordTrue(txt_password,LoginActivity.this)) {

                    final HashMap<String, String> hashMap = new HashMap<String, String>();

                    hashMap.put("merchant_id", txt_sellerI_id);
                    hashMap.put("password", txt_password);

                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(new Runnable() {
                        public void run() {

                            JSONObject response = HttpRequest.SyncHttpRequest(LoginActivity.this, Constants.login, hashMap, progressBar);

                            if (response != null) {
                                Log.d("response",response+"");
                                try {

                                    if (response.names().get(0).equals("success")) {

                                        JSONArray data = response.getJSONArray("success");
                                        JSONObject row = data.getJSONObject(0);

                                        String id = row.getString("merchant_id");
                                        String name = row.getString("name");
                                        String email = row.getString("email");
                                        String contact = row.getString("contact");

                                        new SessionManager(LoginActivity.this,id,name,email,contact);

                                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                        finish();

                                    } else if (response.names().get(0).equals("failed")) {

                                        SnackBar.makeCustomErrorSnack(LoginActivity.this, "Invalid ID or Password");

                                    } else {

                                        SnackBar.makeCustomErrorSnack(LoginActivity.this, "Server Maintenance is on Progress");

                                    }
                                } catch (JSONException e) {
                                    Log.d("exception in catch",e.getMessage());
                                    SnackBar.makeCustomErrorSnack(LoginActivity.this, "Server Maintenance is on Progress");

                                }
                            }

                        }
                    });
                }

            }
        });
    }

    private void setUpComponents() {
    }

    private void initialize() {
       seller_id = (EditText)this.findViewById(R.id.seller_id);
        password = (EditText)this.findViewById(R.id.password);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

}
