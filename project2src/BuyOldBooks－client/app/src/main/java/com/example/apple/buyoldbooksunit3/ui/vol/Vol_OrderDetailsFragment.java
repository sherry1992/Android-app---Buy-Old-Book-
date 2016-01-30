/**
 * Class: Vol_MainFragment
 * This class Vol_MainFragment can implement the operation of viewing the detailed information of a
 * treated order a user has. This class extends the super class Fragment, so it will override the
 * method defined in the super class Fragment.
 *
 * This Fragment works for the Vol_Activity together with Vol_MainFragment and the
 * Vol_SellerOrderDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.vol;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apple.buyoldbooksunit3.R;
import java.util.Date;

public class Vol_OrderDetailsFragment extends Fragment {

    //private instance variable
    private String roll;
    private String account;
    private int orderNumber;
    private String orderSeller;
    private String orderBuyer;
    private float orderTotalPrice;
    private String orderBook;
    private Date orderDate;
    private String bookDepreciation;
    private String paymentMethod;
    private String expressMethod;
    private TextView buyerVOLOrderDBookNameTextView1;
    private TextView buyerVOLOrderDPriceTextView1;
    private TextView buyerVOLOrderDSellerNameTextView1;
    private TextView buyerVOLOrderDBuyerNameTextView1;
    private TextView buyerVOLOrderDDescribeTextView1;
    private TextView buyerVOLOrderDPaymentTextView1;
    private TextView buyerVOLOrderDExpressTextView1;
    private TextView buyerVOLOrderDDateTextView1;

    //constructor without any argument passed in
    public Vol_OrderDetailsFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing the
     * detailed information of a treated order a user has or just come back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.vol_orderdetails_fragment, container, false);
        buyerVOLOrderDBookNameTextView1 = (TextView)view.findViewById(R.id.BuyerVOLOrderDBookNameTextView);
        buyerVOLOrderDPriceTextView1 = (TextView)view.findViewById(R.id.BuyerVOLOrderDPriceTextView1);
        buyerVOLOrderDSellerNameTextView1 = (TextView)view.findViewById(R.id.BuyerVOLOrderDSellerNameTextView1);
        buyerVOLOrderDBuyerNameTextView1 = (TextView)view.findViewById(R.id.BuyerVOLOrderDBuyerNameTextView1);
        buyerVOLOrderDDescribeTextView1 = (TextView)view.findViewById(R.id.BuyerVOLOrderDDescribeTextView1);
        buyerVOLOrderDPaymentTextView1 = (TextView)view.findViewById(R.id.BuyerVOLOrderDPaymentTextView1);
        buyerVOLOrderDExpressTextView1 = (TextView)view.findViewById(R.id.BuyerVOLOrderDExpressTextView1);
        buyerVOLOrderDDateTextView1 = (TextView)view.findViewById(R.id.BuyerVOLOrderDDateTextView1);
        orderNumber = ((Vol_Activity)getActivity()).getOrderNumber();
        orderSeller = ((Vol_Activity)getActivity()).getOrderSeller();
        orderBuyer = ((Vol_Activity)getActivity()).getOrderBuyer();
        orderBook = ((Vol_Activity)getActivity()).getOrderBook();
        orderTotalPrice = ((Vol_Activity)getActivity()).getOrderTotalPrice();
        orderDate = ((Vol_Activity)getActivity()).getOrderDate();
        paymentMethod = ((Vol_Activity)getActivity()).getPaymentMethod();
        expressMethod = ((Vol_Activity)getActivity()).getExpressMethod();
        bookDepreciation = ((Vol_Activity)getActivity()).getBookDepreciation();
        buyerVOLOrderDBookNameTextView1.setText(orderBook);
        buyerVOLOrderDSellerNameTextView1.setText(orderSeller);
        buyerVOLOrderDBuyerNameTextView1.setText(orderBuyer);
        buyerVOLOrderDPriceTextView1.setText(String.valueOf(orderTotalPrice));
        buyerVOLOrderDDescribeTextView1.setText(bookDepreciation);
        buyerVOLOrderDPaymentTextView1.setText(paymentMethod);
        buyerVOLOrderDExpressTextView1.setText(expressMethod);
        buyerVOLOrderDDateTextView1.setText(orderDate.toString());
        roll = ((Vol_Activity)getActivity()).getRoll();
        account = ((Vol_Activity)getActivity()).getAccount();

        //listen on the "BACK" button
        Button buyerVOLOrderDBackButton = (Button)view.findViewById(R.id.buyerVOLOrderDBackButton);
        buyerVOLOrderDBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vol_Activity activity = (Vol_Activity)getActivity();
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if(activity.getmVol_Main_Fragment()==null){
                    activity.setmVol_Main_Fragment(new Vol_MainFragment());
                }
                ft.replace(R.id.id_content, activity.getmVol_Main_Fragment());
                ft.commit();
            }
        });
        return view;
    }

}
