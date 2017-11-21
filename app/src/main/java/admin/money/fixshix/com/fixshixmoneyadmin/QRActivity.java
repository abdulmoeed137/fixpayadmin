package admin.money.fixshix.com.fixshixmoneyadmin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 7/20/2017.
 */

public class QRActivity extends AppCompatActivity {
    ImageView imageView;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        initialize();
        setUpComponent();
    }

    private void setUpComponent() {
        final String qr = getIntent().getExtras().getString("qr");
        final String qr2 = Constants.QR_KEY_RECEIVE
                +(Integer.parseInt(qr))*77868601;
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap = TextToImageEncode((qr2));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            progressBar.setVisibility(View.GONE);
                            imageView.setImageBitmap(bitmap);

                        }
                    });
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    private void initialize() {

        imageView = (ImageView)this.findViewById(R.id.img_qr);
        progressBar = (ProgressBar)this.findViewById(R.id.pbar);

    }


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}

