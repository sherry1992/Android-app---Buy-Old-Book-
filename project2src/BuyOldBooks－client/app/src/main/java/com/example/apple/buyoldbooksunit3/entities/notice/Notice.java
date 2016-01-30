/**
 * project2_Yidil_shuruil
 *
 * This class represent notice object and is an internal class
 */
package com.example.apple.buyoldbooksunit3.entities.notice;

import java.util.Date;

public class Notice {

    /* Declarations of Notice's fields */
    private String noticeContent;
    private String noticeSender;
    private String noticeReceiver;
    private String noticeSeller;
    private String noticeBuyer;
    private Date noticeDate;

    /* Constructor with arguments */
    protected Notice(String noticeContent,String noticeSender,String noticeReceiver,
                     String noticeSeller,String noticeBuyer,Date noticeDate){
        this.noticeContent = noticeContent;
        this.noticeSender = noticeSender;
        this.noticeReceiver = noticeReceiver;
        this.noticeSeller = noticeSeller;
        this.noticeBuyer = noticeBuyer;
        this.noticeDate = noticeDate;
    }

    /*
     * getNoticeContent:
     * Get method of noticeContent
     */
    protected String getNoticeContent() {
        return noticeContent;
    }

    //setter method for noticeContent
    protected void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    /*
     * getNoticeSender:
     * Get method of noticeSender
     */
    protected String getNoticeSender() {
        return noticeSender;
    }


    /*
     * getNoticeReceiver:
     * Get method of noticeReceiver
     */
    protected String getNoticeReceiver() {
        return noticeReceiver;
    }


    /*
     * getNoticeDate:
     * Get method of noticeDate
     */
    protected Date getNoticeDate() {
        return noticeDate;
    }


    /*
     * getNoticeSeller:
     * Get method of noticeSeller
     */
    protected String getNoticeSeller() {
        return noticeSeller;
    }


    /*
     * getNoticeBuyer:
     * Get method of noticeBuyer
     */
    public String getNoticeBuyer() {
        return noticeBuyer;
    }


}
