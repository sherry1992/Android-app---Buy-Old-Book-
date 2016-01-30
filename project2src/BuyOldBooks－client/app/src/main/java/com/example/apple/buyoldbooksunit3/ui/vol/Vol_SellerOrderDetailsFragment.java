/**
 * Class: Vol_SellerOrderDetailsFragment
 * This class Vol_SellerOrderDetailsFragment can implement the operation of viewing the detailed
 * information of an untreated order a user has. This class extends the super class Fragment, so it
 * will override the method defined in the super class Fragment.
 *
 * This Fragment works for the Vol_Activity together with Vol_MainFragment and the
 * Vol_OrderDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.vol;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.entities.user.Administrator;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Vol_SellerOrderDetailsFragment extends Fragment {

    //private instance variable
    private String roll;
    private String account;
    private Vol_Activity activity;
    private TextView sellerVOLOrderDBookNameTextView1;
    private TextView sellerVOLOrderDPriceTextView1;
    private TextView sellerVOLOrderDSellerNameTextView1;
    private TextView sellerVOLOrderDBuyerNameTextView1;
    private TextView sellerVOLOrderDDescribeTextView1;
    private TextView sellerVOLOrderDPaymentTextView1;
    private TextView sellerVOLOrderDExpressTextView1;
    private RadioButton sellerAcceptRadioButton0;
    private RadioButton sellerAcceptRadioButton1;
    private int orderNumber;
    private String orderSeller;
    private String orderBuyer;
    private float orderTotalPrice;
    private String orderBook;
    private Date orderDate;
    private String bookDepreciation;
    private String paymentMethod;
    private String expressMethod;

    //constructor without any argument passed in
    public Vol_SellerOrderDetailsFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing the
     * detailed information of an untreated order a user has or just come back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the list_item1 for this fragment
        View view = inflater.inflate(R.layout.vol_sellerorderdetails_fragment, container, false);
        sellerVOLOrderDBookNameTextView1 = (TextView)view.findViewById(R.id.SellerVOLOrderDBookNameTextView);
        sellerVOLOrderDPriceTextView1 = (TextView)view.findViewById(R.id.SellerVOLOrderDPriceTextView1);
        sellerVOLOrderDSellerNameTextView1 = (TextView)view.findViewById(R.id.SellerVOLOrderDSellerNameTextView1);
        sellerVOLOrderDBuyerNameTextView1 = (TextView)view.findViewById(R.id.SellerVOLOrderDBuyerNameTextView1);
        sellerVOLOrderDDescribeTextView1 = (TextView)view.findViewById(R.id.SellerVOLOrderDDescribeTextView1);
        sellerVOLOrderDPaymentTextView1 = (TextView)view.findViewById(R.id.SellerVOLOrderDPaymentTextView1);
        sellerVOLOrderDExpressTextView1 = (TextView)view.findViewById(R.id.SellerVOLOrderDExpressTextView1);
        sellerAcceptRadioButton0 = (RadioButton)view.findViewById(R.id.sellerAcceptRadioButton0);
        sellerAcceptRadioButton1 = (RadioButton)view.findViewById(R.id.sellerAcceptRadioButton1);
        orderNumber = ((Vol_Activity)getActivity()).getOrderNumber();
        orderSeller = ((Vol_Activity)getActivity()).getOrderSeller();
        orderBuyer = ((Vol_Activity)getActivity()).getOrderBuyer();
        orderBook = ((Vol_Activity)getActivity()).getOrderBook();
        orderTotalPrice = ((Vol_Activity)getActivity()).getOrderTotalPrice();
        orderDate = ((Vol_Activity)getActivity()).getOrderDate();
        paymentMethod = ((Vol_Activity)getActivity()).getPaymentMethod();
        expressMethod = ((Vol_Activity)getActivity()).getExpressMethod();
        bookDepreciation = ((Vol_Activity)getActivity()).getBookDepreciation();
        sellerVOLOrderDBookNameTextView1.setText(orderBook);
        sellerVOLOrderDSellerNameTextView1.setText(orderSeller);
        sellerVOLOrderDBuyerNameTextView1.setText(orderBuyer);
        sellerVOLOrderDPriceTextView1.setText(String.valueOf(orderTotalPrice));
        sellerVOLOrderDDescribeTextView1.setText(bookDepreciation);
        sellerVOLOrderDPaymentTextView1.setText(paymentMethod);
        sellerVOLOrderDExpressTextView1.setText(expressMethod);
        activity = (Vol_Activity)getActivity();
        roll = ((Vol_Activity)getActivity()).getRoll();
        account = ((Vol_Activity)getActivity()).getAccount();

        //listen on the "BACK" button
        Button sVOLOrderDBackButton = (Button)view.findViewById(R.id.sellerVOLOrderDBackButton);
        sVOLOrderDBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if(activity.getmVol_Main_Fragment()==null){
                    activity.setmVol_Main_Fragment(new Vol_MainFragment());
                }
                ft.replace(R.id.id_content, activity.getmVol_Main_Fragment());
                ft.commit();
            }
        });

        //listen on the "DONE" button
        Button sVOLOrderDDoneButton = (Button)view.findViewById(R.id.sellerVOLOrderDDoneButton);
        sVOLOrderDDoneButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sellerAcceptRadioButton0.isChecked()){
                    Seller seller = new Seller();
                    seller.confirmOrder(getActivity().getApplicationContext(),"accept",orderNumber);
                }else{
                    Seller seller = new Seller();
                    seller.confirmOrder(getActivity().getApplicationContext(),"reject",orderNumber);
                }
                Administrator administrator = new Administrator();
                administrator.createConfirmNotice(getActivity().getApplicationContext(),orderSeller,orderBuyer);
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Done!")
                        .setMessage("You have confirm this Order successfully!").create();
                Window window = dialog.getWindow();
                window.setGravity(Gravity.TOP);
                dialog.show();
                Timer timer=new Timer();
                TimerTask task=new TimerTask(){
                    public void run(){
                        FragmentManager fm = getActivity().getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        if(activity.getmVol_Main_Fragment()==null){
                            activity.setmVol_Main_Fragment(new Vol_MainFragment());
                        }
                        ft.replace(R.id.id_content, activity.getmVol_Main_Fragment());
                        ft.commit();
                        getActivity().overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                    }
                };
                timer.schedule(task, 2000);
            }
        });
        return view;
    }

}
