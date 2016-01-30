/**
 * Class: Buyer_Search_BookDetailsFragment
 * This class Buyer_Search_BookDetailsFragment can implement the operation of viewing the detailed
 * information of the book just be searched. This class extends the super class Fragment, so it will
 * override the method defined in the super class Fragment.
 *
 * This Fragment works for the Buyer_Search_Activity together with Buyer_Search_MainFragment and
 * the Buyer_Search_ResultFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_search;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.entities.book.BookList;
import com.example.apple.buyoldbooksunit3.entities.user.Buyer;
import com.example.apple.buyoldbooksunit3.ui.buyer_cart.Buyer_Cart_Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Buyer_Search_BookDetailsFragment extends Fragment {

    //private instance variable
    private Bundle bundle;
    private String roll;
    private String account;
    private String bookSelected;
    private String bookName;
    private String bookPrice;
    private String bookDepreciation;
    private String sellerName;
    private String bookDescription;
    private String payment;
    private String express;
    private Button detailbackButton;
    private Button cart1Button;
    private Button addToCartButton;
    private TextView paymentTextView;
    private TextView expressTextView;
    private TextView priceTextView;
    private TextView depreciationTextView;
    private TextView nameTextView;
    private TextView sellerTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    private Buyer buyer;
    private String cart;
    private int bookId;

    //constructor without any argument passed in
    public Buyer_Search_BookDetailsFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing the
     * detailed information of a book just found and add the book into Buyer's cart or just come
     * back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.buyer_search_bookdetails_fragment, container, false);
        priceTextView=(TextView)v.findViewById(R.id.searchDetailPriceTextView);
        imageView = (ImageView)v.findViewById(R.id.searchDetailImageView);
        depreciationTextView=(TextView)v.findViewById(R.id.searchDetailDepreTextView);
        nameTextView=(TextView)v.findViewById(R.id.searchDetailBNameTextView);
        sellerTextView=(TextView)v.findViewById(R.id.searchDetailSellerTextView);
        descriptionTextView=(TextView)v.findViewById(R.id.searchDetailDescTextView1);
        paymentTextView=(TextView)v.findViewById(R.id.searchDetailPayMethodTextView1);
        expressTextView=(TextView)v.findViewById(R.id.searchDetailExpressTextView1);
        roll = ((Buyer_Search_Activity)getActivity()).getRoll();
        account = ((Buyer_Search_Activity)getActivity()).getAccount();
        buyer=new Buyer();
        bookSelected=((Buyer_Search_Activity)getActivity()).getBookSelected();
        bookName=bookSelected.split(":")[0];
        bookPrice=((Buyer_Search_Activity) getActivity()).getBookPrice();
        bookDepreciation=((Buyer_Search_Activity)getActivity()).getBookDepreciation();
        sellerName=bookSelected.split(":")[1];
        bookDescription=((Buyer_Search_Activity)getActivity()).getBookDescription();
        payment=((Buyer_Search_Activity)getActivity()).getPayment();
        express=((Buyer_Search_Activity)getActivity()).getExpress();
        nameTextView.setText(String.valueOf(bookName));
        priceTextView.setText(String.valueOf(bookPrice));
        sellerTextView.setText(String.valueOf(sellerName));
        depreciationTextView.setText(String.valueOf(bookDepreciation));
        descriptionTextView.setText(String.valueOf(bookDescription));
        paymentTextView.setText(payment);
        expressTextView.setText(express);
        bundle = new Bundle();
        bundle.putString("roll", roll);
        bundle.putString("account",account);
        String sellerAccount = buyer.findAccount(getActivity().getApplicationContext(),sellerName);
        buyer.readPicture(getActivity().getApplicationContext(), bookName, sellerAccount, imageView);
        detailbackButton=(Button)v.findViewById(R.id.searchDetailBackButton);
        cart1Button=(Button)v.findViewById(R.id.cart1button);

        //listen on the "BACK" button
        detailbackButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Buyer_Search_Activity activity = (Buyer_Search_Activity) getActivity();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (activity.getmBuyer_Search_Result_Fragment() == null) {
                    activity.setmBuyer_Search_Result_Fragment(new Buyer_Search_ResultFragment());
                }
                ft.replace(R.id.id_content, activity.getmBuyer_Search_Result_Fragment());
                ft.commit();
            }
        });

        //listen on the "CART" button
        cart1Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Buyer_Cart_Activity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        //listen on the "ADD TO CART" button
        addToCartButton = (Button)v.findViewById(R.id.addToCartButton);
        addToCartButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                buyer.updateCart(getActivity().getApplicationContext(),account,bookName,sellerName);
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).
                        setTitle("Done!").setMessage("Add this book to Cart successfully!")
                        .create();
                Window window = dialog.getWindow();
                window.setGravity(Gravity.TOP);
                dialog.show();
                Timer timer=new Timer();
                TimerTask task=new TimerTask(){
                    public void run(){
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), Buyer_Cart_Activity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                        getActivity().overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                    }
                };
                timer.schedule(task, 2000);

            }
        });
        return v;
    }

}
