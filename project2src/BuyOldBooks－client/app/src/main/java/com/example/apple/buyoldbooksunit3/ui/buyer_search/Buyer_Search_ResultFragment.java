/**
 * Class: Buyer_Search_ResultFragment
 * This class Buyer_Search_ResultFragment can implement the operation of viewing the list of available
 * books topic on the words a Buyer just put in. This class extends the super class Fragment, so it
 * will override the method defined in the super class Fragment.
 *
 * This Fragment works for the Buyer_Search_Activity together with Buyer_Search_MainFragment and
 * the Buyer_Search_BookDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_search;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.apple.buyoldbooksunit3.entities.user.Buyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Buyer_Search_ResultFragment extends Fragment {

    //private instance variable
    private String roll;
    private String account;
    private Button search1backButton;
    private String keyword;
    private String sortmethod;
    private String bookSelected;
    private Bundle bundle;
    private BookList searchedBooks;

    //constructor without any argument passed in
    public Buyer_Search_ResultFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing the
     * list of available books topic on the words a Buyer just put in.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.buyer_search_result_fragment, container, false);
        roll = ((Buyer_Search_Activity)getActivity()).getRoll();
        account = ((Buyer_Search_Activity)getActivity()).getAccount();
        search1backButton=(Button)v.findViewById(R.id.searchResultBackButton);
        keyword=((Buyer_Search_Activity)getActivity()).getKeyword();
        sortmethod=((Buyer_Search_Activity)getActivity()).getSortmethod();
        bundle = new Bundle();
        bundle.putString("roll",roll);
        bundle.putString("account",account);
        Buyer buyer=new Buyer();
        searchedBooks=buyer.searchBook(getActivity().getApplicationContext(),keyword,sortmethod);
        List<String> list= new ArrayList<>();
        for(int i = 0; i < searchedBooks.getBookList().size(); i++){
            list.add(searchedBooks.getBookName(i)+":"+searchedBooks.getBookSeller(i));
        }
        ListView myListView = (ListView)v.findViewById(R.id.avlbookListView);
        myListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item1, list));

        //add the available book list into the ListView control
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookSelected = (String) ((TextView) view).getText();
                ((Buyer_Search_Activity)getActivity()).setBookSelected(bookSelected);
                bundle.putString("bookSelected",bookSelected);
                String bookname=bookSelected.split(":")[0];
                String bookseller=bookSelected.split(":")[1];
                HashMap bookInfo=searchedBooks.bookInfo(bookname,bookseller);
                System.out.println("(((((((("+bookInfo.size());
                ((Buyer_Search_Activity)getActivity()).setBookPrice(bookInfo.get("bookPrice").
                        toString());
                ((Buyer_Search_Activity)getActivity()).setBookDepreciation(bookInfo.
                        get("bookDepreciation").toString());
                ((Buyer_Search_Activity)getActivity()).setBookDescription(bookInfo.
                        get("bookDescription").toString());
                ((Buyer_Search_Activity)getActivity()).setPayment(bookInfo.get("payment").toString());
                ((Buyer_Search_Activity)getActivity()).setExpress(bookInfo.get("express").toString());
                Buyer_Search_Activity activity = (Buyer_Search_Activity) getActivity();
                FragmentManager fm = activity.getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (activity.getmBuyer_Book_Details_Fragment()== null) {
                    activity.setmBuyer_Book_Details_Fragment(new Buyer_Search_BookDetailsFragment());
                }
                ft.replace(R.id.id_content, activity.getmBuyer_Book_Details_Fragment());
                ft.commit();
            }
        });

        //listen on the "BACK" button
        search1backButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Buyer_Search_Activity activity=(Buyer_Search_Activity)getActivity();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (activity.getmBuyer_Search_Main_Fragment()== null) {
                    activity.setmBuyer_Search_Main_Fragment(new Buyer_Search_MainFragment());
                }
                ft.replace(R.id.id_content, activity.getmBuyer_Search_Main_Fragment());
                ft.commit();
            }
        });
        return v;
    }

}
