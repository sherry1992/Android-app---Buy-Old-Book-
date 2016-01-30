/**
 * Class: Cn_NoticeDetailsFragment
 * This class Cn_NoticeDetailsFragment can implement the operation of viewing the detailed
 * information of a particular notice a user has. This class extends the super class Fragment, so it
 * will override the method defined in the super class Fragment.
 *
 * This Fragment works for the Cn_Activity together with Cn_MainFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.cn;

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


public class Cn_NoticeDetailsFragment extends Fragment {

    //private instance variable
    private String roll;
    private String account;
    private TextView cnSenderNameTextView;
    private TextView cnTimeTextView;
    private TextView cnDetailsContentTextViewDisplay;
    private String noticeSender;
    private String noticeContent;
    private String noticeDate;

    //constructor without any argument passed in
    public Cn_NoticeDetailsFragment() {

    }

    @Override
    /*
     * onCreateView method:
     * This method is overridden the onCreateView method defined in the super class Fragment.
     * This method can listen on the different buttons to implement the functionality of viewing the
     * detailed information of a particular notice a user has or just come back.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the list_item1 for this fragment
        View view = inflater.inflate(R.layout.cn_noticedetails_fragment, container, false);
        roll = ((Cn_Activity)getActivity()).getRoll();
        account = ((Cn_Activity)getActivity()).getAccount();
        cnSenderNameTextView = (TextView)view.findViewById(R.id.CnSenderNameTextView);
        cnTimeTextView = (TextView)view.findViewById(R.id.CnTimeTextView);
        cnDetailsContentTextViewDisplay = (TextView)view.findViewById(R.id.CnDetailsContentTextViewDisplay);
        noticeSender = ((Cn_Activity)getActivity()).getNoticeSender();
        noticeDate = ((Cn_Activity)getActivity()).getNoticeDate().toString();
        noticeContent = ((Cn_Activity)getActivity()).getNoticeContent();
        cnSenderNameTextView.setText(noticeSender);
        cnTimeTextView.setText(noticeDate);
        cnDetailsContentTextViewDisplay.setText(noticeContent);

        //listen on the "BACK" button
        Button cNNoteDetailsBackButton = (Button)view.findViewById(R.id.CNNoteDetailsBackButton);
        cNNoteDetailsBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cn_Activity activity = (Cn_Activity)getActivity();
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
