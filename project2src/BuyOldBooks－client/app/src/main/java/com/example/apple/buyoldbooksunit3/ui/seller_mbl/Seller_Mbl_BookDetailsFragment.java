/**
 * Class: Seller_Mbl_BookDetailsFragment
 * This class Seller_Mbl_BookDetailsFragment can implement the operation of viewing the detailed
 * information of a book which belongs to a particular Seller. This class extends the super class
 * Fragment, so it will override the method defined in the super class Fragment.
 *
 * This Fragment works for the Seller_Mbl_Activity together with Seller_Mbl_MainFragment and
 * the Seller_Mbl_UpdateInfoFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.seller_mbl;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;

import java.util.Timer;
import java.util.TimerTask;

public class Seller_Mbl_BookDetailsFragment extends Fragment {

    //private instance variable
    private String roll;
    private String account;
    private String bookName;
    private Seller_Mbl_Activity activity;
    private TextView sellerMBLBookDNameTextView;
    private TextView sellerMBLBookDDesContentTextView;
    private ImageView imageView;

    //constructor without any argument passed in
    public Seller_Mbl_BookDetailsFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing
     * the detailed information of a book which belongs to a particular Seller or just come back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the list_item1 for this fragment
        View view = inflater.inflate(R.layout.seller_mbl_bookdetails_fragment, container, false);
        sellerMBLBookDNameTextView = (TextView)view.findViewById(R.id.SellerMBLBookDNameTextView);
        sellerMBLBookDDesContentTextView = (TextView)view.findViewById(R.id.SellerMBLBookDDesContentTextView);
        roll = ((Seller_Mbl_Activity)getActivity()).getRoll();
        account = ((Seller_Mbl_Activity)getActivity()).getAccount();
        activity = (Seller_Mbl_Activity)getActivity();
        bookName = activity.getBookName();
        Seller seller = new Seller();
        imageView = (ImageView)view.findViewById(R.id.SellerMBLBookDImageView);

        //read the book cover from the database in the server side.
        seller.readPicture(getActivity().getApplicationContext(),bookName,account,imageView);
        sellerMBLBookDNameTextView.setText(String.valueOf(bookName));
        sellerMBLBookDDesContentTextView.setText(String.valueOf(activity.getBookDescription()));

        //listen on the "UPDATE INFO" button
        Button sMBLBookDUpdateInfoButton = (Button) view.findViewById(R.id.SellerMBLBookDUpdateInfoButton);
        sMBLBookDUpdateInfoButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = activity.getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if(activity.getmSellerMbl_UpdateInfo_Fragment()==null){
                    activity.setmSellerMbl_UpdateInfo_Fragment(new Seller_Mbl_UpdateInfoFragment());
                }
                ft.replace(R.id.id_content, activity.getmSellerMbl_UpdateInfo_Fragment());
                ft.commit();
            }
        });

        //listen on the "BACK" button
        Button sMBLBookDBackButton = (Button) view.findViewById(R.id.SellerMBLBookDBackButton);
        sMBLBookDBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = activity.getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if(activity.getmSellerMbl_Main_Fragment()==null){
                    activity.setmSellerMbl_Main_Fragment(new Seller_Mbl_MainFragment());
                }
                ft.replace(R.id.id_content, activity.getmSellerMbl_Main_Fragment());
                ft.commit();
            }
        });

        //listen on the "DELETE" button
        Button sMBLBookDDeleteButton = (Button) view.findViewById(R.id.SellerMBLBookDDeleteButton);
        sMBLBookDDeleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete from db
                Seller seller=new Seller();
                seller.deleteBook(getActivity().getApplicationContext(),account,bookName);
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Done!")
                        .setMessage("You have delete the used Book successfully!").create();
                Window window = dialog.getWindow();
                window.setGravity(Gravity.TOP);
                dialog.show();
                Timer timer=new Timer();
                TimerTask task=new TimerTask(){
                    public void run(){
                        FragmentManager fm = getActivity().getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        if(activity.getmSellerMbl_Main_Fragment()==null){
                            activity.setmSellerMbl_Main_Fragment(new Seller_Mbl_MainFragment());
                        }
                        ft.replace(R.id.id_content, activity.getmSellerMbl_Main_Fragment());
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
