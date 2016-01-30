/**
 * Class: Buyer_Cart_MainFragment
 * This class Buyer_Cart_MainFragment can implement the operation of viewing the cart book list for
 * a Buyer. This class extends the super class Fragment, so it will override the method defined in
 * the super class Fragment.
 *
 * This Fragment works for the Buyer_Cart_Activity together with the Buyer_Cart_CreateOrderFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_cart;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.entities.book.BookList;
import com.example.apple.buyoldbooksunit3.entities.user.Buyer;
import com.example.apple.buyoldbooksunit3.ui.buyer_home.Buyer_HomeActivity;

import java.util.ArrayList;

public class Buyer_Cart_MainFragment extends Fragment {

    //private instance variable
    private Bundle bundlelast;
    private Bundle bundle;
    private String roll;
    private String account;
    private Button buyButton;
    private Button cartbackButton;
    private ListView cartListView;
    private Button cartclearButton;
    private Buyer buyer;

    //constructor without an argument passed in
    public Buyer_Cart_MainFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing
     * the cart book list.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.buyer_cart_main_fragment, container, false);
        cartListView=(ListView)v.findViewById(R.id.cartlistView);
        cartclearButton=(Button)v.findViewById(R.id.cartClearButton);
        bundlelast = getActivity().getIntent().getExtras();
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        ((Buyer_Cart_Activity)getActivity()).setRoll(roll);
        ((Buyer_Cart_Activity)getActivity()).setAccount(account);
        buyer=new Buyer();
        String cart=buyer.searchCart(getActivity().getApplicationContext(),account);
        ((Buyer_Cart_Activity)getActivity()).setCart(cart);
        ArrayList bookNo=new ArrayList();
        for(int i=0;i<cart.split(":").length;i++){
            bookNo.add(cart.split(":")[i]);
        }
        if(!cart.equals("")) {
            BookList buyerCart = buyer.getCartList(getActivity().getApplicationContext(), bookNo);
            ((Buyer_Cart_Activity) getActivity()).setCartList(buyerCart);
            ArrayList list = new ArrayList();
            for (int i = 0; i < buyerCart.getBookList().size(); i++) {
                list.add(buyerCart.getBookName(i) + "    " + buyerCart.getBookPrice(i));
            }
            cartListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item1, list));
        }
        bundle = new Bundle();
        bundle.putString("roll",roll);
        bundle.putString("account",account);
        buyButton=(Button)v.findViewById(R.id.cartBuyButton);
        cartbackButton=(Button)v.findViewById(R.id.cartBackButton);

        //listen on "CLEAR" button
        cartclearButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                buyer.clearCart(getActivity().getApplicationContext(), account);
                Intent intent =new Intent();
                intent.setClass(getActivity(), Buyer_HomeActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        //listen on "BUY" button
        buyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Buyer_Cart_Activity activity = (Buyer_Cart_Activity) getActivity();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (activity.getmBuyer_Create_Order_Fragment() == null) {
                    activity.setmBuyer_Create_Order_Fragment(new Buyer_Cart_CreateOrderFragment());
                }
                ft.replace(R.id.id_content, activity.getmBuyer_Create_Order_Fragment());
                ft.commit();

            }
        });

        //listen on "BACK" button
        cartbackButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(getActivity(), Buyer_HomeActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        });
        return v;
    }
}
