/**
 * Class: Cn_MainFragment
 * This class Cn_MainFragment can implement the operation of viewing the list of notices a user has.
 * This class extends the super class Fragment, so it will override the method defined in the super
 * class Fragment.
 *
 * This Fragment works for the Cn_Activity together with Cn_NoticeDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.cn;

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
import com.example.apple.buyoldbooksunit3.entities.notice.NoticeList;
import com.example.apple.buyoldbooksunit3.entities.user.Buyer;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;
import com.example.apple.buyoldbooksunit3.ui.buyer_home.Buyer_HomeActivity;
import com.example.apple.buyoldbooksunit3.ui.seller_home.Seller_Home_Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class Cn_MainFragment extends Fragment {

    //private instance variable
    private Bundle bundlelast;
    private Bundle bundle;
    private String roll;
    private String account;
    private Cn_Activity activity;
    private NoticeList noticeList;

    //constructor without any argument passed in
    public Cn_MainFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing the
     * list of notices a user has or just come back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the list_item1 for this fragment
        View view = inflater.inflate(R.layout.cn_main_fragment, container, false);
        bundlelast = getActivity().getIntent().getExtras();
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        ((Cn_Activity)getActivity()).setRoll(roll);
        ((Cn_Activity)getActivity()).setAccount(account);
        bundle  = new Bundle();
        bundle.putString("roll", roll);
        bundle.putString("account", account);
        activity = (Cn_Activity)getActivity();
        if(roll.equals("seller")){
            Seller seller = new Seller();
            noticeList = seller.checkNotice(getActivity().getApplicationContext(),account,roll);
        }else{
            Buyer buyer = new Buyer();
            noticeList = buyer.checkNotice(getActivity().getApplicationContext(),account,roll);
        }
        ListView myListView = (ListView)view.findViewById(R.id.cnMyListView);
        List<String> list= new ArrayList<>();
        for(int i = 0; i < noticeList.getNoticeList().size(); i++){
            list.add(noticeList.getNoticeSender(i)+"/"+noticeList.getNoticeDate(i));
        }

        //set the result list into the ListView control
        myListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item1, list));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String noteSelected = (String) ((TextView) view).getText();
                String noticeSender=noteSelected.split("/")[0];
                String noticeDate=noteSelected.split("/")[1];
                ((Cn_Activity)getActivity()).setNoticeSender(noticeSender);
                System.out.println(noticeSender+" "+noticeDate);
                HashMap noticeInfo = noticeList.noticeInfo(noticeSender, noticeDate);
                ((Cn_Activity)getActivity()).setNoticeReceiver(noticeInfo.get("noticeReceiver").toString());
                ((Cn_Activity)getActivity()).setNoticeSeller(noticeInfo.get("noticeSeller").toString());
                ((Cn_Activity)getActivity()).setNoticeBuyer(noticeInfo.get("noticeBuyer").toString());
                ((Cn_Activity)getActivity()).setNoticeContent(noticeInfo.get("noticeContent").toString());
                ((Cn_Activity)getActivity()).setNoticeDate((Date) noticeInfo.get("noticeDate"));
                Cn_Activity activity = (Cn_Activity) getActivity();
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (activity.getmCn_NoticeDetails_Fragment() == null) {
                    activity.setmCn_NoticeDetails_Fragment(new Cn_NoticeDetailsFragment());
                }
                ft.replace(R.id.id_content, activity.getmCn_NoticeDetails_Fragment());
                ft.commit();
            }
        });

        //listen on the "BACK" button
        Button cNBackButton = (Button)view.findViewById(R.id.CNBackButton);
        cNBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(roll.equals("seller")){
                    intent.setClass(getActivity(), Seller_Home_Activity.class);
                }else{
                    intent.setClass(getActivity(), Buyer_HomeActivity.class);
                }
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        //listen on the "CLEAR NOTIFICATION" button
        Button cNClearButton = (Button)view.findViewById(R.id.CNClearButton);
        cNClearButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear the table in database
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if(activity.getmCn_Main_Fragment()==null){
                    activity.setmCn_Main_Fragment(new Cn_MainFragment());
                }
                ft.replace(R.id.id_content, activity.getmCn_Main_Fragment());
                ft.commit();
            }
        });
        return view;
    }
}
