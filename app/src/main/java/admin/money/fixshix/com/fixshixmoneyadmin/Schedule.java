package admin.money.fixshix.com.fixshixmoneyadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import admin.money.fixshix.com.fixshixmoneyadmin.adapter.ScheduleAdapter;
import admin.money.fixshix.com.fixshixmoneyadmin.model.ScheduleModel;

/**
 * Created by lenovo on 9/18/2017.
 */

public class Schedule extends AppCompatActivity {
    ListView mListView;
    ArrayList<ScheduleModel> list = new ArrayList<>();
    ProgressBar progressBar; TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initialize();
        setUpComponent();
    }

    private void setUpComponent() {

        final HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("id", new SessionManager(Schedule.this).getId());
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {

                JSONObject response = HttpRequest.SyncHttpRequest(Schedule.this, Constants.merchant_history, hashMap, progressBar);

                if (response != null) {
                    Log.d("response",response+"");
                    try {

                        if (response.names().get(0).equals("success")) {

                            final JSONArray data = response.getJSONArray("success");

                            Double t_amount = 0.00;
                            for (int i = 0 ; i<data.length(); i ++)
                            {
                                String status = ""; String method="";
                                JSONObject row = data.getJSONObject(i);
                                Double amount2 = (Double.parseDouble(row.getString("amount")));
                                t_amount = t_amount+(amount2);
                                if (Double.parseDouble(row.getString("amount")) > 0)
                                {
                                    status = "Sent";
                                }
                                else {
                                    if (row.getString("transaction_type").equals("1"))
                                    {
                                        amount2= (amount2*9)/10;
                                    }

                                    status="Received";}

                                if (row.getString("qr_code").equals("") || row.getString("qr_code").equals("0"))
                                {
                                    method="via ID";
                                }
                                else
                                    method="via QR";

                                list.add(new ScheduleModel(
                                        row.getString("transaction_id"),
                                        amount2.toString(),
                                              status,
                                        row.getString("time"),
                                       method,
                                        row.getString("user_id")
                                ));
                            }
                            final Double[] finalT_amount = {t_amount};
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mListView.setAdapter(new ScheduleAdapter(Schedule.this,list));
                                    finalT_amount[0] = utils.double2decimal(finalT_amount[0]);
                                    total.setText("Total amount in Market: " + finalT_amount[0]);
                                }
                            });
                        } else if (response.names().get(0).equals("failed")) {

                            SnackBar.makeCustomErrorSnack(Schedule.this, response.getString("failed"));

                        } else {

                            SnackBar.makeCustomErrorSnack(Schedule.this, "Server Maintenance is on Progress");

                        }
                    } catch (JSONException e) {
                        Log.d("exception in catch",e.getMessage());
                        SnackBar.makeCustomErrorSnack(Schedule.this, "Server Maintenance is on Progress");

                    }
                }

            }
        });


    }

    private void initialize() {
        mListView = (ListView)this.findViewById(R.id.listView);
        progressBar = (ProgressBar)this.findViewById(R.id.pbar);
        total = (TextView)this.findViewById(R.id.total);
    }
}
