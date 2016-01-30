/**
 * Class: Buyer_Search_MainFragment
 * This class Buyer_Search_MainFragment can implement the operation of searching books topic on a
 * word. This class extends the super class Fragment, so it will override the method defined in
 * the super class Fragment.
 *
 * This Fragment works for the Buyer_Search_Activity together with the Buyer_Search_ResultFragment
 * and Buyer_Search_BookDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_search;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.custom_exception.CustomException;
import com.example.apple.buyoldbooksunit3.ui.buyer_home.Buyer_HomeActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Buyer_Search_MainFragment extends Fragment {

    //private instance variable
    private Bundle bundlelast;
    private Bundle bundle;
    private String roll;
    private String account;
    private EditText keywordEditText;
    private RadioGroup searchmethod;
    private Button searchButton;
    private Button searchbackButton;
    private String sortMethod;
    private Button voicesearchButton;
    private String keyword;
    private boolean jumpflag;

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of searching
     * a book topic on a particular word.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.buyer_search_main_fragment, container, false);
        bundlelast = getActivity().getIntent().getExtras();
        jumpflag=true;
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        keywordEditText=(EditText)v.findViewById(R.id.keywordeditText);
        if(bundlelast.getString("key")!=null){
            keyword=bundlelast.getString("key");
            keywordEditText.setText(String.valueOf(keyword));
        }
        ((Buyer_Search_Activity)getActivity()).setRoll(roll);
        ((Buyer_Search_Activity)getActivity()).setAccount(account);
        bundle = new Bundle();
        bundle.putString("roll", roll);
        bundle.putString("account", account);
        searchButton=(Button)v.findViewById(R.id.searchbutton);
        searchbackButton=(Button)v.findViewById(R.id.searchMainBackButton);
        voicesearchButton=(Button)v.findViewById(R.id.voicesearchButton);
        searchmethod=(RadioGroup)v.findViewById(R.id.sortgroup);

        //listen on the search method RadioGroup
        searchmethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { //get gender
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sortPriceRadioButton:
                        sortMethod="ByPrice";
                        break;
                    case R.id.sortDepreRadioButton:
                        sortMethod="ByDepreciation";
                        break;
                    default:
                        break;
                }
            }

        });

        //listen on the "SEARCH" button
        searchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                bundle.putString("sortmethod", sortMethod);
                keyword = keywordEditText.getText().toString();
                try{
                    if(keyword.equals("")){
                        jumpflag = false;
                        CustomException e = new CustomException(3, "Please input keywords !");
                        String str = "Error " + e.getErrorno() + ": " + e.getErrormsg().toString();
                        Timestamp t = new Timestamp(new Date().getTime());
                        writeToLog(str, t);
                        throw e;
                    }
                }catch(CustomException e){
                    e.fix(getActivity(), e.getErrorno(), e.getErrormsg());

                }
                try {
                    if (sortMethod==null) {
                        jumpflag = false;
                        CustomException e = new CustomException(4, "Please select your sort method !");
                        String str = "Error " + e.getErrorno() + ": " + e.getErrormsg().toString();
                        Timestamp t = new Timestamp(new Date().getTime());
                        writeToLog(str, t);
                        throw e;
                    }
                }catch(CustomException e){
                    e.fix(getActivity(), e.getErrorno(), e.getErrormsg());
                }
                if(jumpflag==true){
                    ((Buyer_Search_Activity) getActivity()).setSortmethod(sortMethod);
                    ((Buyer_Search_Activity) getActivity()).setKeyword(keyword);
                    bundle.putString("keyword", keyword);
                    Buyer_Search_Activity activity = (Buyer_Search_Activity) getActivity();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    if (activity.getmBuyer_Search_Result_Fragment() == null) {
                        activity.setmBuyer_Search_Result_Fragment(new Buyer_Search_ResultFragment());
                    }
                    ft.replace(R.id.id_content, activity.getmBuyer_Search_Result_Fragment());
                    ft.commit();
                }
            }
        });

        //listen on "SEARCH BY VOICE" button
        voicesearchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Buyer_Voicesearch_Activity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        });

        //listen on "BACK" button
        searchbackButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Buyer_HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return v;
    }

    /*
     * writeToLog:
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
