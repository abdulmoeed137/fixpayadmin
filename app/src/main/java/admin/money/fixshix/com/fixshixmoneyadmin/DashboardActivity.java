package admin.money.fixshix.com.fixshixmoneyadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 7/20/2017.
 */

public class DashboardActivity extends AppCompatActivity {
    TextView merchant_name;
    RadioButton byID, byQR;
    EditText user_id, amount;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        initialize();
        setUpComponents();
    }

    private void setUpComponents() {
        merchant_name.setText(new SessionManager(DashboardActivity.this).getName());

        byID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id.setVisibility(View.VISIBLE);

            }
        });

        byQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.logs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,Schedule.class));
            }
        });
        findViewById(R.id.generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if ( byQR.isChecked())
               {
                   if (Validity.isAmountTrue(amount.getText().toString(),DashboardActivity.this))
                   {
                       final HashMap<String, String> hashMap = new HashMap<String, String>();

                       hashMap.put("merchant_id",new SessionManager(DashboardActivity.this).getId());
                       hashMap.put("amount", amount.getText().toString());

                       Executor executor = Executors.newSingleThreadExecutor();
                       executor.execute(new Runnable() {
                           public void run() {

                               JSONObject response = HttpRequest.SyncHttpRequest(DashboardActivity.this, Constants.make_transaction_qr, hashMap, progressBar);

                               if (response != null) {
                                   try {

                                       if (response.names().get(0).equals("success")) {

                                          String qr = response.getString("success");
                                           runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {

                                                   user_id.setText("");
                                                   amount.setText("");
                                               }
                                           });
                                           Intent i = new Intent(DashboardActivity.this,QRActivity.class);
                                           i.putExtra("qr",qr);
                                           startActivity(i);


                                       } else if (response.names().get(0).equals("failed")) {

                                           SnackBar.makeCustomErrorSnack(DashboardActivity.this, "Failed to Proceed");

                                       } else {

                                           SnackBar.makeCustomErrorSnack(DashboardActivity.this, "Server Maintenance is on Progress");

                                       }
                                   } catch (JSONException e) {

                                       SnackBar.makeCustomErrorSnack(DashboardActivity.this, "Server Maintenance is on Progress");

                                   }
                               }

                           }
                       });
                   }
               }

                if ( byID.isChecked())
                {
                    if (Validity.isAmountTrue(amount.getText().toString(),DashboardActivity.this)
                            &&
                            Validity.isIdTrue(user_id.getText().toString(),DashboardActivity.this))
                    {
                        final HashMap<String, String> hashMap = new HashMap<String, String>();

                        hashMap.put("merchant_id",new SessionManager(DashboardActivity.this).getId());
                        hashMap.put("amount", amount.getText().toString());
                        hashMap.put("user_id", user_id.getText().toString());

                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(new Runnable() {
                            public void run() {

                                JSONObject response = HttpRequest.SyncHttpRequest(DashboardActivity.this, Constants.make_transaction, hashMap, progressBar);

                                if (response != null) {
                                    try {

                                        if (response.names().get(0).equals("success")) {


                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    SnackBar.makeCustomSnack(DashboardActivity.this,"Transaction Successful");
                                                    user_id.setText("");
                                                    amount.setText("");
                                                }
                                            });


                                        } else if (response.names().get(0).equals("failed")) {

                                            SnackBar.makeCustomErrorSnack(DashboardActivity.this, response.getString("failed"));

                                        } else {

                                            SnackBar.makeCustomErrorSnack(DashboardActivity.this, "Server Maintenance is on Progress");

                                        }
                                    } catch (JSONException e) {

                                        SnackBar.makeCustomErrorSnack(DashboardActivity.this, "Server Maintenance is on Progress");

                                    }
                                }

                            }
                        });
                    }
                }
            }
        });

    }

    private void initialize() {
        merchant_name = (TextView)this.findViewById(R.id.merchant_name);

        byID = (RadioButton)this.findViewById(R.id.byid);
        byQR = (RadioButton)this.findViewById(R.id.byqr);

        user_id = (EditText)this.findViewById(R.id.user_id);
        amount = (EditText)this.findViewById(R.id.amount);

        progressBar = (ProgressBar)this.findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout:
                new AlertDialog.Builder(DashboardActivity.this)

                        .setTitle("Confirm")
                        .setMessage("Are You Sure You Want To Logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                new SessionManager(DashboardActivity.this).Logout();
                                finish();
                                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
