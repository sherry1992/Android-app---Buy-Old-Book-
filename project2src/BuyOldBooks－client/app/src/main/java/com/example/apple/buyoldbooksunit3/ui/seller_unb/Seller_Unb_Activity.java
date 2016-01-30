/**
 * Class: Seller_Unb_Activity
 * The class Seller_Unb_Activity extends the super class Activity, so this class will override the
 * methods defined in the super class Activity. This class will implement the functionality of
 * uploading a new book by a Seller.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.seller_unb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.custom_exception.CustomException;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;
import com.example.apple.buyoldbooksunit3.ui.seller_home.Seller_Home_Activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Seller_Unb_Activity extends Activity {

    //private instance variable
    private Bundle bundlelast;
    private Bundle bundle;
    private String roll;
    private String account;
    private String bookName;
    private EditText booknameEditText;
    private EditText bookpriceEditText;
    private RadioGroup sellerULBgroup;
    private String depreciation;
    private EditText descriptionEditText;
    private EditText paymentEditText;
    private EditText expressEditText;
    private Button cameraButton;
    private ImageView bookImageView;
    private static final int NONE = 0;
    private static final int PHOTO_GRAPH = 1;
    private static final int PHOTO_ZOOM = 2;
    private static final int PHOTO_RESOULT = 3;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private boolean jumpflag;

    @Override
    /*
     * onCreate method:
     * This method is overriden the onCreate method defined in the super class Activity.
     * This method can listen on the different buttons to implement the functionality of uploading
     * a new book by a Seller.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_unb_activity);
        bundlelast = getIntent().getExtras();
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        bundle = new Bundle();
        bundle.putString("roll",roll);
        bundle.putString("account",account);
        cameraButton=(Button)findViewById(R.id.sellerUNBcameraButton);
        bookImageView=(ImageView)findViewById(R.id.sellerUNBImageView);
        booknameEditText=(EditText)findViewById(R.id.SellerUNBNameEditText);
        bookpriceEditText=(EditText)findViewById(R.id.SellerUNBPriceEditText);
        descriptionEditText=(EditText)findViewById(R.id.SellerUNBDescribeEditText);
        paymentEditText=(EditText)findViewById(R.id.SellerUNBPayMethodEditText);
        expressEditText=(EditText)findViewById(R.id.SellerUNBExpMethodEditText);

        //listen on the seller upload RadioGroup
        sellerULBgroup=(RadioGroup)findViewById(R.id.sellerUNBgroup);
        sellerULBgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //get gender
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton90per:
                        depreciation = "0.9";
                        break;
                    case R.id.radioButton70per:
                        depreciation = "0.7";
                        break;
                    case R.id.radioButton50per:
                        depreciation = "0.5";
                        break;
                    default:
                        break;
                }
            }

        });

        //listen on the "CAMERA" button
        cameraButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,Activity.DEFAULT_KEYS_DIALER);
            }
        });

        //listen on the "CANCEL" button
        Button sUNBCancelButton = (Button)findViewById(R.id.SellerUNBCancelButton);
        sUNBCancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Seller_Unb_Activity.this, Seller_Home_Activity.class);
                Seller_Unb_Activity.this.startActivity(intent);
            }
        });

        //listen on the "DONE" button
        Button sUNBDoneButton = (Button)findViewById(R.id.SellerUNBDoneButton);
        sUNBDoneButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpflag=true;
                bookName=booknameEditText.getText().toString();
                Drawable photo=bookImageView.getDrawable();
                String bookPrice=bookpriceEditText.getText().toString();
                String description=descriptionEditText.getText().toString();
                String payment=paymentEditText.getText().toString();
                String express=expressEditText.getText().toString();
                String emailAddress=account;
                Seller seller = new Seller();

                //handle the custom exception
                try{
                    if(bookName.equals("")||photo==null||bookPrice.equals("")||description.equals("")
                            ||payment.equals("")||
                            express.equals("")){
                        jumpflag = false;
                        CustomException e = new CustomException(1, "Missing Information!");
                        String str = "Error " + e.getErrorno() + ": " + e.getErrormsg().toString();
                        Timestamp t = new Timestamp(new Date().getTime());
                        writeToLog(str, t);
                        throw e;
                    }
                }catch(CustomException e){
                    e.fix(Seller_Unb_Activity.this, e.getErrorno(), e.getErrormsg());
                }
                if(jumpflag==true){
                    Bitmap photoBitmap=drawableToBitmap(photo);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] photoByte=baos.toByteArray();
                    seller.uploadBook(getApplicationContext(), emailAddress, photoByte, bookName, bookPrice,
                            depreciation, description, payment, express);


                    AlertDialog dialog = new AlertDialog.Builder(Seller_Unb_Activity.this).
                            setTitle("Done!").setMessage("You have uploaded a new Book successfully!").
                            create();
                    Window window = dialog.getWindow();
                    window.setGravity(Gravity.TOP);
                    dialog.show();
                    Timer timer=new Timer();
                    TimerTask task=new TimerTask(){
                        public void run(){
                            Intent intent = new Intent();
                            intent.setClass(Seller_Unb_Activity.this, Seller_Home_Activity.class);
                            intent.putExtras(bundle);
                            Seller_Unb_Activity.this.startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        }
                    };
                    timer.schedule(task, 2000);
                }
            }
        });
    }

    /*
     * onActivityResult method:
     * This method can return the result after finishing taking the picture.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();
            String fileName = "/sdcard/myImage/tmp.jpg";
            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (data!= null) {
                Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
                bookImageView.setImageBitmap(cameraBitmap);
            }
        }
        if(requestCode == PHOTO_ZOOM){
            startPhotoZoom(data.getData());
        }
    }

    /*
     * startPhotoZoom method:
     * This method can operate the photo just be taken.
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }

    @Override
    /*
     * onCreateOptionsMenu method:
     * This method is overridden the onCreateOptionsMenu method defined in the super class Activity.
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seller_unb, menu);
        return true;
    }

    @Override
    /*
     * onOptionsItemSelected method:
     * This method is overridden the onOptionsItemSelected method defined in the super class Activity.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * drawableToBitmap method:
     * This helper method can change the photo from the Drawable type to the Bitmap type
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /*
     * writeToLog:
     * This method is used to record the error message into ExceptionLog.txt file.
     */
    public void writeToLog(String str, Timestamp t) {
        String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        Date date = new Date(t.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        String time = sdf.format(date);
        String information = time + "---" + str;
        try {
            FileOutputStream fos = this.openFileOutput("exception_log.txt", Context.MODE_APPEND);
            fos.write(information.getBytes());
            fos.write("\n".getBytes());
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
