/**
 * Class: Seller_Mbl_MainFragment
 * This class Seller_Mbl_MainFragment can implement the operation of viewing the list of book a seller
 * has. This class extends the super class Fragment, so it will override the method defined in the super
 * class Fragment.
 *
 * This Fragment works for the Seller_Mbl_Activity together with Seller_Mbl_BookDetailsFragment and
 * the Seller_Mbl_UpdateInfoFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.seller_mbl;

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
import com.example.apple.buyoldbooksunit3.entities.book.BookList;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;
import com.example.apple.buyoldbooksunit3.ui.seller_home.Seller_Home_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Seller_Mbl_MainFragment extends Fragment {

    //private instance variable
    private Bundle bundlelast;
    private Bundle bundle;
    private String roll;
    private String account;
    private BookList bookList;

    //constructor without any argument passed in
    public Seller_Mbl_MainFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing the
     * list of books a Seller has or just come back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the list_item1 for this fragment
        View view = inflater.inflate(R.layout.seller_mbl_main_fragment, container, false);
        bundlelast = getActivity().getIntent().getExtras();
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        ((Seller_Mbl_Activity)getActivity()).setRoll(roll);
        ((Seller_Mbl_Activity)getActivity()).setAccount(account);
        bundle = new Bundle();
        bundle.putString("roll",roll);
        bundle.putString("account",account);
        Seller seller = new Seller();
        bookList = seller.readBookList(getActivity().getApplicationContext(),account);
        ListView myListView = (ListView)view.findViewById(R.id.MyListView);
        List<String> list= new ArrayList<>();
        for(int i = 0; i < bookList.getBookList().size(); i++){
            list.add(bookList.getBookName(i)+":"+bookList.getBookSeller(i));
        }

        //set the result list into the ListView control
        myListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item1, list));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String bookSelected = (String) ((TextView) view).getText();
                String bookname=bookSelected.split(":")[0];
                String bookseller=bookSelected.split(":")[1];
                ((Seller_Mbl_Activity)getActivity()).setBookName(bookname);
                HashMap bookInfo = bookList.bookInfo(bookname,bookseller);
                ((Seller_Mbl_Activity)getActivity()).setBookPrice(bookInfo.get("bookPrice").toString());
                ((Seller_Mbl_Activity)getActivity()).setBookDepreciation(bookInfo.get("bookDepreciation").toString());
                ((Seller_Mbl_Activity)getActivity()).setBookDescription(bookInfo.get("bookDescription").toString());
                ((Seller_Mbl_Activity)getActivity()).setPayment(bookInfo.get("payment").toString());
                ((Seller_Mbl_Activity)getActivity()).setExpress(bookInfo.get("express").toString());
                ((Seller_Mbl_Activity)getActivity()).setSellerName(bookInfo.get("sellerName").toString());
                Seller_Mbl_Activity activity = (Seller_Mbl_Activity) getActivity();
                FragmentManager fm = activity.getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (activity.getmSellerMbl_BookDetails_Fragment() == null) {
                    activity.setmSellerMbl_BookDetails_Fragment(new Seller_Mbl_BookDetailsFragment());
                }
                ft.replace(R.id.id_content, activity.getmSellerMbl_BookDetails_Fragment());
                ft.commit();
            }
        });

        //listen on the "BACK" button
        Button sellMblBackButton = (Button)view.findViewById(R.id.sellMblBackButton);
        sellMblBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seller_Mbl_Activity activity = (Seller_Mbl_Activity) getActivity();
                Intent intent = new Intent();
                intent.setClass(activity, Seller_Home_Activity.class);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        return view;
    }

}
