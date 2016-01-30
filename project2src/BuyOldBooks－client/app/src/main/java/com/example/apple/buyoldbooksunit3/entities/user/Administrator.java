/**
 * project2_Yidil_shuruil
 *
 * Administor:
 * The Administrator is in charg of creating database, send notice to Buyer and Seller when there
 * an order is generated
 */
package com.example.apple.buyoldbooksunit3.entities.user;

import android.content.Context;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Administrator {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /*
     * createDatabase:
     * This method is used to create database and tables
     */
    public void createDatebase(Context context){
        synchronized (context){
            MyThreadAdmin thr = new MyThreadAdmin(context,"createDatebase");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /*
     * createOrderNotice:
     * This method is used to send a notice to Seller when Buyer creates an order
     */
    public void createOrderNotice(Context context, String sellerName, String buyeraccount){
        synchronized(context){
            MyThreadAdmin thr = new MyThreadAdmin(context,sellerName,buyeraccount,"createOrderNotice");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /*
     * createConfirmNotice:
     * This method is used to send a confirm notice to Buyer when Seller confirm the order
     */
    public void createConfirmNotice(Context context, String sellerName, String buyerName){
        synchronized(context){
            MyThreadAdmin thr = new MyThreadAdmin(context,sellerName,buyerName,"createConfirmNotice",1);
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * MyThreadAdmin:
     * This class is an inner class and extends Thread class and will operate the functionality of
     * administrator in a single thread.
     */
    class MyThreadAdmin extends Thread{

        /* Declarations of the fields needed */
        private Context context;
        private ObjectInputStream reader;
        private ObjectOutputStream writer;
        private String option;
        private String sellerName;
        private String buyeraccount;
        private String buyerName;

        /* Constructor with arguments used to create database*/
        public MyThreadAdmin(Context context, String option){
            this.context = context;
            this.option = option;
        }

        /* Constructor with arguments used to createOrderNotice*/
        public MyThreadAdmin(Context context, String sellerName, String buyeraccount,String option){
            this.context = context;
            this.sellerName = sellerName;
            this.buyeraccount = buyeraccount;
            this.option = option;
        }

        /* Constructor with arguments used to createConfirmNotice */
        public MyThreadAdmin(Context context, String sellerName, String buyerName,String option, int a){
            this.context = context;
            this.sellerName = sellerName;
            this.buyerName = buyerName;
            this.option = option;
        }

        /*
         * run:
         * This method is used to realize createDatabase, createOrderNotice and createConfirmNotice
         * methods in their single threads using socket.
         */
        @Override
        public void run() {
            synchronized (context){
                Socket socket = new Socket();
                try {
                    socket.connect(new InetSocketAddress("10.0.0.5", 8080), 5000);
                    writer = new ObjectOutputStream(socket.getOutputStream());
                    reader = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(option.equals("createDatebase")){
                    try {
                        writer.writeObject("build");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }if(option.equals("createOrderNotice")){
                    HashMap<String,String> where = new HashMap<>();
                    where.put("buyer_email", buyeraccount);
                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Buyer");
                        writer.flush();
                        writer.writeObject(where);
                        writer.flush();
                        writer.writeObject(method);
                        writer.flush();
                        writer.writeObject(order);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<HashMap<String,String>> result = null;
                    Object object = null;
                    try {
                        object = reader.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    result = (ArrayList<HashMap<String,String>>)object;
                    String buyerName = result.get(0).get("buyer_nickname");
                    String content = "Hi,\n";
                    content = content+sellerName+", "+buyerName +"has place an order on your book just now.";
                    HashMap searchInfo=new HashMap();
                    searchInfo.put("seller_nickname",sellerName);
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Seller");
                        writer.flush();
                        writer.writeObject(searchInfo);
                        writer.flush();
                        writer.writeObject(method);
                        writer.flush();
                        writer.writeObject(order);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<HashMap<String,String>> c = null;
                    try {
                        object = reader.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    c = (ArrayList<HashMap<String,String>>)object;
                    String sellerId = c.get(0).get("seller_id");
                    HashMap searchInfo1=new HashMap();
                    searchInfo1.put("buyer_nickname",buyerName);
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Buyer");
                        writer.flush();
                        writer.writeObject(searchInfo1);
                        writer.flush();
                        writer.writeObject(method);
                        writer.flush();
                        writer.writeObject(order);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        object = reader.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    c = (ArrayList<HashMap<String,String>>)object;
                    String buyerId = c.get(0).get("buyer_id");
                    String senderId = buyerId;
                    String receiverId = sellerId;
                    Date now=new Date();
                    String noticeDate=format.format(now);
                    HashMap<String,String>notice = new HashMap<>();
                    notice.put("sender_id",senderId);
                    notice.put("receiver_id",receiverId);
                    notice.put("seller_id_notice",sellerId);
                    notice.put("buyer_id_notice",buyerId);
                    notice.put("notice_content",content);
                    notice.put("notice_date", noticeDate);
                    try {
                        writer.writeObject("c");
                        writer.flush();
                        writer.writeObject("Notice");
                        writer.flush();
                        writer.writeObject(notice);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(option.equals("createConfirmNotice")){
                    String content = "Hi,\n";
                    content = content+buyerName+", "+sellerName +"has answered your order just now.";
                    HashMap searchInfo=new HashMap();
                    searchInfo.put("seller_nickname",sellerName);
                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Seller");
                        writer.flush();
                        writer.writeObject(searchInfo);
                        writer.flush();
                        writer.writeObject(method);
                        writer.flush();
                        writer.writeObject(order);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<HashMap<String,String>> c = null;
                    Object object = null;
                    try {
                        object = reader.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    c = (ArrayList<HashMap<String,String>>)object;
                    String sellerId = c.get(0).get("seller_id");
                    HashMap searchInfo1=new HashMap();
                    searchInfo1.put("buyer_nickname",buyerName);
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Buyer");
                        writer.flush();
                        writer.writeObject(searchInfo1);
                        writer.flush();
                        writer.writeObject(method);
                        writer.flush();
                        writer.writeObject(order);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        object = reader.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    c = (ArrayList<HashMap<String,String>>)object;
                    String buyerId = c.get(0).get("buyer_id");
                    String senderId = sellerId;
                    String receiverId = buyerId;
                    Date now=new Date();
                    String noticeDate=format.format(now);
                    HashMap<String,String>notice = new HashMap<>();
                    notice.put("sender_id",senderId);
                    notice.put("receiver_id",receiverId);
                    notice.put("seller_id_notice",sellerId);
                    notice.put("buyer_id_notice",buyerId);
                    notice.put("notice_content",content);
                    notice.put("notice_date", noticeDate);
                    try {
                        writer.writeObject("c");
                        writer.flush();
                        writer.writeObject("Notice");
                        writer.flush();
                        writer.writeObject(notice);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    writer.writeObject("quit");
                    writer.flush();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                context.notifyAll();

            }

        }
    }
}
