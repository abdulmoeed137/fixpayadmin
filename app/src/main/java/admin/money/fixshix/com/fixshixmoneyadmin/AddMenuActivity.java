package admin.money.fixshix.com.fixshixmoneyadmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddMenuActivity extends AppCompatActivity
{
    ProgressBar ProgressBar;
    EditText MenuName, MenuDesc, MenuPrice, CashBack;
    Button Submit;
    ImageButton Image;
    Context context;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    Uri filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        initialize();
        setUpComponents();
    }

    private void setUpComponents()
    {


//////////////////////////////////// Image  //////////////////////////////////

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UploadImage();
            }
        });

//////////////////////////////////// Image  //////////////////////////////////







   Submit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v)
       {





           final String _mname = MenuName.getText().toString();
           final String _mdesc = MenuDesc.getText().toString();
           final String _mprice = MenuPrice.getText().toString();
           final String _cashback = CashBack.getText().toString();


           if (Validity.isNameTrue(_mname,context) &&  Validity.isDescTrue(_mdesc,context)  &&
                   Validity.isAmountTrue(_mprice,context) &&
                   Validity.isAmountTrue(_cashback,context))
           {

               final HashMap<String, String> hashMap = new HashMap<String, String>();

              String image = getStringImage(bitmap);

               hashMap.put("merchantid", new SessionManager(AddMenuActivity.this).getId());
               hashMap.put("menuname", _mname);
               hashMap.put("menudesc", _mdesc);
               hashMap.put("menuprice", _mprice);
               hashMap.put("cashback", _cashback);

               hashMap.put("image", image);

               Executor executor = Executors.newSingleThreadExecutor();
               executor.execute(new Runnable() {
                   public void run() {

                       JSONObject response = HttpRequest.SyncHttpRequest(AddMenuActivity.this, Constants.add_menu, hashMap, ProgressBar);

                       if (response != null) {
                           Log.d("response",response+"");
                           try {

                               if (response.names().get(0).equals("success"))
                               {
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {

                                         Toast.makeText(AddMenuActivity.this, "Menu Added Succesfully", Toast.LENGTH_SHORT).show();
                                         startActivity(new Intent(AddMenuActivity.this, DashboardActivity.class));
                                     }
                                 });

                               }
                               else
                                   {

                                   SnackBar.makeCustomErrorSnack(AddMenuActivity.this, "Server Maintenance is on Progress");

                               }
                           } catch (JSONException e) {
                               Log.d("exception in catch",e.getMessage());
                               SnackBar.makeCustomErrorSnack(AddMenuActivity.this, "Server Maintenance is on Progress");

                           }
                       }

                   }
               });
           }

       }
   });
    }

    private void initialize()
    {


        ProgressBar = (ProgressBar)this.findViewById(R.id.progressBar);

        MenuName = (EditText) this.findViewById(R.id.menu_name);
        MenuDesc = (EditText) this.findViewById(R.id.menu_desc);
        MenuPrice = (EditText) this.findViewById(R.id.menu_price);
        CashBack = (EditText) this.findViewById(R.id.cash_back);


        Image = (ImageButton) this.findViewById(R.id.menu_image);

        Submit = (Button) this.findViewById(R.id.submitButton);

        this.context = this;


    }

    //////////////////////////////////// Image  //////////////////////////////////

   public void UploadImage()
    {


        showFileChooser();

    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                Image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    //////////////////////////////////// Image  //////////////////////////////////
}

