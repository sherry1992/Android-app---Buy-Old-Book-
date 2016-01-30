/**
 * Class: Buyer_Cart_CreateOrderFragment
 * This class Buyer_Cart_CreateOrderFragment can implement the operation of placing a new order for
 * a Buyer. This class extends the super class Fragment, so it will override the method defined in
 * the super class Fragment.
 *
 * This Fragment works for the Buyer_Cart_Activity together with the Buyer_Cart_MainFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_cart;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.custom_exception.CustomException;
import com.example.apple.buyoldbooksunit3.entities.book.BookList;
import com.example.apple.buyoldbooksunit3.entities.user.Administrator;
import com.example.apple.buyoldbooksunit3.entities.user.Buyer;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;
import com.example.apple.buyoldbooksunit3.ui.buyer_home.Buyer_HomeActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Buyer_Cart_CreateOrderFragment extends Fragment {

    //private instance variable
    private Bundle bundle;
    private String roll;
    private String account;
    private Button ordercancelButton;
    private Button placeorderButton;
    private Button getlocationButton;
    private BookList cartList;
    private String cart;
    private String [] bookId;
    private Buyer buyer;
    private Seller seller;
    private EditText locationEditText;
    private double lat ;
    private double lng ;
    private boolean jumpflag;

    //constructor without any argument passed in
    public Buyer_Cart_CreateOrderFragment() {

    }

    //setter method for the private instance variable
    public void setLng(double lng) {
        this.lng = lng;
    }

    //setter method for the private instance variable
    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of placing
     * a new order for a Buyer.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.buyer_cart_createorder_fragment, container, false);
        roll = ((Buyer_Cart_Activity)getActivity()).getRoll();
        account = ((Buyer_Cart_Activity)getActivity()).getAccount();
        cartList=((Buyer_Cart_Activity)getActivity()).getCartList();
        cart=((Buyer_Cart_Activity)getActivity()).getCart();
        bookId=cart.split(":");
        buyer=new Buyer();
        seller=new Seller();
        bundle = new Bundle();
        bundle.putString("roll",roll);
        bundle.putString("account",account);
        ordercancelButton=(Button)v.findViewById(R.id.ordercancelbutton);
        placeorderButton=(Button)v.findViewById(R.id.placeorderbutton);
        getlocationButton=(Button)v.findViewById(R.id.locationbutton);
        locationEditText=(EditText)v.findViewById(R.id.locationeditText);
        Button testButton = (Button)v.findViewById(R.id.TESTBUTTON);

        //listen on the "ADD" button to add location in to TextView
        testButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                synchronized (getActivity().getApplicationContext()){
                    GetLocationName thr = new GetLocationName(getActivity().getApplicationContext());
                    thr.start();
                    try {
                        getActivity().getApplicationContext().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    locationEditText.setText(thr.getAddress());
                }

            }

        });

        //listen on the "GET LOCATION" button, to get the location of a Buyer
        getlocationButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                synchronized (getActivity().getApplicationContext()) {
                    LocationManager locMgr = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                    LocationListener locListener = new LocationListener() {
                        public void onLocationChanged(Location location) {

                            if (location != null) {
                                setLat(location.getLatitude());
                                setLng(location.getLongitude());

                            }
                        }

                        //Overrides the method of  LocationManager
                        public void onProviderDisabled(String provider) {
                        }

                        //Overrides the method of  LocationManager
                        public void onProviderEnabled(String provider) {
                        }

                        //Overrides the method of  LocationManager
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                    };
                    locMgr.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0,
                            locListener
                    );

                }
            }
        });

        //listen on the "CANCEL" button
        ordercancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Buyer_Cart_Activity activity=(Buyer_Cart_Activity)getActivity();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (activity.getmBuyer_Cart_Main_Fragment()== null) {
                    activity.setmBuyer_Cart_Main_Fragment(new Buyer_Cart_MainFragment());
                }
                ft.replace(R.id.id_content, activity.getmBuyer_Cart_Main_Fragment());
                ft.commit();

            }
        });

        //listen on the "PLACE ORDER" button
        placeorderButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                jumpflag=true;
                try{
                    if(cart.equals("")){
                        jumpflag = false;
                        CustomException e = new CustomException(5, "Your cart is empty !");
                        String str = "Error " + e.getErrorno() + ": " + e.getErrormsg().toString();
                        Timestamp t = new Timestamp(new Date().getTime());
                        writeToLog(str, t);
                        throw e;
                    }
                }catch (CustomException e){
                    e.fix(getActivity(), e.getErrorno(), e.getErrormsg());
                }
                if(jumpflag==true){
                    for(int i=0;i<bookId.length;i++){
                        String seller_id=seller.findId(getActivity().getApplicationContext(),cartList.getBookSeller(i));
                        String book_id=bookId[i];
                        String purchase_price=String.valueOf(cartList.getBookPrice(i));
                        Date now=new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String purchase_date=format.format(now);
                        String purchase_state="wait";
                        buyer.createOrder(getActivity().getApplicationContext(),account,seller_id,book_id,purchase_price,purchase_date,purchase_state);
                        Administrator administrator = new Administrator();
                        administrator.createOrderNotice(getActivity().getApplicationContext(),cartList.getBookSeller(i),account);
                        buyer.clearCart(getActivity().getApplicationContext(), account);
                    }
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Done!").setMessage("You have placed your order successfully!").
                                    create();
                    Window window = dialog.getWindow();
                    window.setGravity(Gravity.TOP);
                    dialog.show();
                    Timer timer=new Timer();
                    TimerTask task=new TimerTask(){
                        public void run(){
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), Buyer_HomeActivity.class);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        }
                    };
                    timer.schedule(task, 2000);
                }
            }
        });
        return v;
    }


    /**
     * Inner class: GetLocationName
     * This class GetLocationName is an inner class of the class Buyer_Cart_CreateOrderFragment.
     * This class extends the super class Thread, so it will override the method defined in the
     * super class Thread. This class can get the String address from the latitude.
     */
    class GetLocationName extends Thread {

        //private instance variable
        private String address;
        private Context context;

        //constructor with an argument passed in
        public GetLocationName(Context context){
            this.context = context;
        }

        //getter method for the private instance variable
        public String getAddress() {
            return address;
        }

        /*
         * run method:
         * This method override the run method defined in the super class Thread. This method can
         * get the String address from the float latitude.
         */
        public void run() {
            synchronized (context){
                HttpGet request = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat
                        + "," + lng + "&sensor=true");
                HttpResponse response;
                try {
                    response = new DefaultHttpClient().execute(request);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        String rpstr = EntityUtils.toString(response.getEntity());
                        JSONObject result = new JSONObject(rpstr);
                        Log.d("MainActivity-JSONObject", result.toString());
                        JSONArray ja = (JSONArray) result.get("results");
                        Log.d("MainActivity-JSONArray", "" + ja.length());
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject js = ja.getJSONObject(i);
                            Log.d("MainActivity-js", js.toString());
                            JSONArray jaa = (JSONArray) js.get("types");
                            String ss = jaa.getString(0);
                            if ("street_address".equalsIgnoreCase(ss)) {
                                address = js.getString("formatted_address");
                                Log.d("MainActivity-address", address);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.i("Exception", e.toString());
                }
                context.notifyAll();
            }
        }


    }

    /*
     * writeToLog method:
     * This method is used to record the error message into ExceptionLog.txt file.
     */
    public void writeToLog(String str, Timestamp t) {
        String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        Date date = new Date(t.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        String time = sdf.format(date);
        String information = time + "---" + str;
        try {
            FileOutputStream fos =getActivity().openFileOutput("exception_log.txt", Context.MODE_APPEND);
            fos.write(information.getBytes());
            fos.write("\n".getBytes());
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
