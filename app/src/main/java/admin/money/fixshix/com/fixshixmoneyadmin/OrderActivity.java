package admin.money.fixshix.com.fixshixmoneyadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity
{
    ProgressBar progressBar;
    Button AddMenu;
    Button OurMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initialize();
        setUpComponents();
    }

    private void setUpComponents() {

        AddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {

                startActivity(new Intent(OrderActivity.this, AddMenuActivity.class));


            }
        });

        OurMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OrderActivity.this, OurMenuActivity.class));
            }
        });

    }

    private void initialize()
    {


        progressBar = (ProgressBar)this.findViewById(R.id.progressBar);

        AddMenu = (Button) this.findViewById(R.id.add_menu);
        OurMenu = (Button) this.findViewById(R.id.our_menu);

    }
}