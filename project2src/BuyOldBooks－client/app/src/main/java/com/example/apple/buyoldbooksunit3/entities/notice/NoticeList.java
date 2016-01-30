/**
 * project2_Yidil_shuruil
 *
 * NoticeList:
 * This class serves as an external class and is used to manage Notice such as get the information
 * of a notice and insert a new notice into the notice list.
 */
package com.example.apple.buyoldbooksunit3.entities.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NoticeList {
    private ArrayList<Notice> noticeList;        //An ArrayList that stores notice

    /* Constructor with no-arg */
    public NoticeList(){
        noticeList = new ArrayList<>();
    }

    /*
     * getNoticeList:
     * Get method of noticeList
     */
    public ArrayList<Notice> getNoticeList() {
        return noticeList;
    }

    /*
     * addNotice:
     * Add a new notice into the notice list
     */
    public void addNotice(String noticeContent,String noticeSender,String noticeReceiver,
                          String noticeSeller,String noticeBuyer,Date noticeDate){
        Notice notice = new Notice(noticeContent,noticeSender,noticeReceiver, noticeSeller,
                noticeBuyer,noticeDate);
        noticeList.add(notice);
    }

    /*
     * getNoticeSender:
     * Get method of noticeSender
     */
    public String getNoticeSender(int index){
        return noticeList.get(index).getNoticeSender();
    }

    /*
     * getNoticeDate:
     * Get method of noticeDate
     */
    public Date getNoticeDate(int index){
        return noticeList.get(index).getNoticeDate();
    }

    /*
     * getNoticeReceiver:
     * Get method of noticeReceiver
     */
    public String getNoticeReceiver(int index){
        return noticeList.get(index).getNoticeReceiver();
    }

    /*
     * Get method of noticeSeller
     */
    public String getNoticeSeller(int index){
        return noticeList.get(index).getNoticeSeller();
    }

    /*
     * Get method of noticeBuyer
     */
    public String getNoticeBuyer(int index){
        return noticeList.get(index).getNoticeBuyer();
    }

    /*
     * Get method of noticeContent
     */
    public String getNoticeContent(int index){
        return noticeList.get(index).getNoticeContent();
    }

    /*
     * noticeInfo;
     * This method is used to search the information of a particular notice according to the notice's
     * sender and notice's date and return the information in a HashMap.
     */
    public HashMap noticeInfo(String noticeSender, String noticeDate){
        HashMap info=new HashMap();
        for(int i=0;i<noticeList.size();i++){
            if(getNoticeSender(i).equals(noticeSender) && getNoticeDate(i).toString().equals(noticeDate)){
                info.put("noticeSender",getNoticeSender(i));
                info.put("noticeReceiver",getNoticeReceiver(i));
                info.put("noticeSeller",getNoticeSeller(i));
                info.put("noticeBuyer",getNoticeBuyer(i));
                info.put("noticeDate",getNoticeDate(i));
                info.put("noticeContent",getNoticeContent(i));
                break;
            }
        }
        return info;
    }
}
