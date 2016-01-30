/**
 * Class: Vol_MainFragment
 * This class Vol_MainFragment can implement the operation of viewing the list of all the order
 * a user has recently. This class extends the super class Fragment, so it will override the method
 * defined in the super class Fragment.
 *
 * This Fragment works for the Vol_Activity together with Vol_OrderDetailsFragment and
 * the Vol_SellerOrderDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.vol;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.entities.order.OrderList;
import com.example.apple.buyoldbooksunit3.entities.user.Buyer;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;
import com.example.apple.buyoldbooksunit3.ui.buyer_home.Buyer_HomeActivity;
import com.example.apple.buyoldbooksunit3.ui.seller_home.Seller_Home_Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Vol_MainFragment extends Fragment {

    //private instance variable
    private Bundle bundlelast;
    private Bundle bundle;
    private String roll;
    private String account;
    private OrderList orderList;

    //constructor without any argument passed in
    public Vol_MainFragment() {

    }


    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing
     * the list of all the order a user has or just come back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the list_item1 for this fragment
        View view = inflater.inflate(R.layout.vol_main_fragment, container, false);
        bundlelast = getActivity().getIntent().getExtras();
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        ((Vol_Activity)getActivity()).setRoll(roll);
        ((Vol_Activity)getActivity()).setAccount(account);
        bundle = new Bundle();
        bundle.putString("roll",roll);
        bundle.putString("account",account);
        if(roll.equals("seller")){
            Seller seller = new Seller();
            orderList = seller.checkOrder(getActivity().getApplicationContext(), account, roll);
        }else{
            Buyer buyer = new Buyer();
            orderList = buyer.checkOrder(getActivity().getApplicationContext(), account, roll);
        }
        ListView myListView = (ListView)view.findViewById(R.id.volMyListView);
        List<String> list= new ArrayList<>();
        for(int i = 0; i < orderList.getOrderList().size(); i++){
            list.add(String.valueOf(orderList.getOrderNumber(i)));
        }

        //set the result list into the ListView control
        myListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item1, list));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int orderNumber = Integer.parseInt(((TextView) view).getText().toString());
                ((Vol_Activity)getActivity()).setOrderNumber(orderNumber);
                HashMap orderInfo = orderList.orderInfo(orderNumber,getActivity().getApplicationContext());
                ((Vol_Activity)getActivity()).setOrderSeller(orderInfo.get("orderSeller").toString());
                ((Vol_Activity)getActivity()).setOrderBuyer(orderInfo.get("orderBuyer").toString());
                ((Vol_Activity)getActivity()).setOrderBook(orderInfo.get("orderBook").toString());
                ((Vol_Activity)getActivity()).setOrderTotalPrice((float) orderInfo.get("orderTotalPrice"));
                ((Vol_Activity)getActivity()).setOrderDate((Date) orderInfo.get("orderDate"));
                ((Vol_Activity)getActivity()).setBookDepreciation(orderInfo.get("bookDepreciation").toString());
                ((Vol_Activity)getActivity()).setPaymentMethod(orderInfo.get("paymentMethod").toString());
                ((Vol_Activity)getActivity()).setExpressMethod(orderInfo.get("expressMethod").toString());
                String orderState = orderInfo.get("orderState").toString();
                Vol_Activity activity = (Vol_Activity) getActivity();
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (roll.equals("seller")&&(orderState.equals("wait"))) {
                    if (activity.getmVol_Seller_OrderDetails_Fragment() == null) {
                        activity.setmVol_Seller_OrderDetails_Fragment(new Vol_SellerOrderDetailsFragment());
                    }
                    ft.replace(R.id.id_content, activity.getmVol_Seller_OrderDetails_Fragment());
                } else {
                    if (activity.getmVol_Buyer_OrderDetails_Fragment() == null) {
                        activity.setmVol_Buyer_OrderDetails_Fragment(
                                new Vol_OrderDetailsFragment());
                    }
                    ft.replace(R.id.id_content, activity.getmVol_Buyer_OrderDetails_Fragment());
                }
                ft.commit();
            }
        });

        //listen on the "BACK" button
        Button volBackButton = (Button)view.findViewById(R.id.VOLBackButton);
        volBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (roll.equals("seller")) {
                    intent.setClass(getActivity(), Seller_Home_Activity.class);
                } else {
                    intent.setClass(getActivity(), Buyer_HomeActivity.class);
                }
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

}
