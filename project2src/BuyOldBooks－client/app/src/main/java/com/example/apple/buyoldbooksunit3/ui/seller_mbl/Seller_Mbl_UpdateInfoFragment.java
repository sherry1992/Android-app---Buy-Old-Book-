/**
 * Class: Seller_Mbl_UpdateInfoFragment
 * This class Seller_Mbl_UpdateInfoFragment can implement the operation of updating the detailed
 * information of a book which belongs to a particular Seller. This class extends the super class
 * Fragment, so it will override the method defined in the super class Fragment.
 *
 * This Fragment works for the Seller_Mbl_Activity together with Seller_Mbl_MainFragment and
 * the Seller_Mbl_BookDetailsFragment.
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
import android.widget.EditText;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;

import java.util.Timer;
import java.util.TimerTask;

public class Seller_Mbl_UpdateInfoFragment extends Fragment {

    //private instance variable
    private String roll;
    private String account;
    private Seller_Mbl_Activity activity;
    private String bookName;
    private EditText NewPriceEditText;
    private EditText NewDescriptionEditText;

    //constructor without any argument passed in
    public Seller_Mbl_UpdateInfoFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of updating
     * the detailed information of a book which belongs to a particular Seller or just come back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the list_item1 for this fragment
        View view = inflater.inflate(R.layout.seller_mbl_updateinfo_fragment, container, false);
        roll = ((Seller_Mbl_Activity)getActivity()).getRoll();
        account =((Seller_Mbl_Activity)getActivity()).getAccount();
        activity = (Seller_Mbl_Activity)getActivity();
        NewPriceEditText=(EditText)view.findViewById(R.id.SellerMBLUpdateInfoNewPriceEditText);
        NewDescriptionEditText=(EditText)view.findViewById(R.id.SellerMBLUpdateInfoNewDesEditText);
        bookName=((Seller_Mbl_Activity)getActivity()).getBookName();

        //listen on the "CANCEL" button
        Button sMBLUpdateINfoCancelButton = (Button)view.findViewById(R.id.SellerMBLUpdateINfoCancelButton);
        sMBLUpdateINfoCancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if(activity.getmSellerMbl_BookDetails_Fragment()==null){
                    activity.setmSellerMbl_BookDetails_Fragment(new Seller_Mbl_BookDetailsFragment());
                }
                ft.replace(R.id.id_content, activity.getmSellerMbl_BookDetails_Fragment());
                ft.commit();
            }
        });

        //listen on the "UPLOAD" button
        Button sMBLUpdateINfoUploadButton = (Button)view.findViewById(R.id.SellerMBLUpdateINfoUploadButton);
        sMBLUpdateINfoUploadButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update into database
                String newprice=NewPriceEditText.getText().toString();
                String newdescription=NewDescriptionEditText.getText().toString();
                Seller seller=new Seller();
                if(!newprice.equals("") || !newdescription.equals("")){
                    seller.updateBook(getActivity().getApplicationContext(),account,bookName,newprice,newdescription);
                }
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Done!")
                        .setMessage("You have updated the Book information successfully!").create();
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
