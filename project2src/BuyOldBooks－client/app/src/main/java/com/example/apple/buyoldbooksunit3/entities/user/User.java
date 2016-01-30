/**
 * project2_Yidil_shuruil
 *
 * User:
 * This User class is an Abstract class which is extended by Seller and Buyer. There are a
 * bunch of common field and common method between the Seller and Buyer defined in the abstract
 * class User.
 */
package com.example.apple.buyoldbooksunit3.entities.user;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;
import com.example.apple.buyoldbooksunit3.entities.notice.NoticeList;
import com.example.apple.buyoldbooksunit3.entities.order.OrderList;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public abstract class User {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /* Declarations of some fields needed */
    private String nickName;
    private String emailAddress;
    private int photo;
    private String gender;
    private String phone;
    private String address;
    private NoticeList noticeBox;
    private OrderList orderBox;

    /*
     * register:
     * This method is to let the user to register on this application
     */
    public int register(Context context,String table,String identity,String emailAddress,String password){
        synchronized (context){
            MyThread thr = new MyThread(context,table,identity,emailAddress,password,"register");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {

            }
            return thr.getRegisterFlag();
        }
    }

    /*
     * login:
     * This method is let the user to log into this application
     */
    public int login(Context context, String emailAddress, String password, String roll){
        synchronized (context){
            MyThread thr = new MyThread(context, emailAddress,password,roll,"login");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thr.getLoginFlag();
        }

    }

    /*
     * updateInfo:
     * This method is used to update the information of the user
     */
    public void updateInfo(Context context, String emailAddress, String nickName, String gender, String phone,
                           String address, String roll){
        synchronized (context){
            MyThread thr = new MyThread(context,emailAddress,nickName,gender,phone,address,roll,"updateInfo");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /*
     * chechNotice:
     * This method is used to check the notice
     */
    public NoticeList checkNotice(Context context,String account, String roll){
        synchronized (context){
            MyThread thr = new MyThread(context,account,roll,"checkNotice");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thr.getNoticeList();
        }


    }

    /*
     * checkOrder:
     * This method is used to check the order
     */
    public OrderList checkOrder(Context context,String account, String roll){
        synchronized (context){
            MyThread thr = new MyThread(context,account,roll,"checkOrder");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thr.getOrderList();
        }


    }

    /*
     * readPicture:
     * This method is used to read the picture the Seller uploads for a book
     */
    public void readPicture(Context context, String bookName,String account,ImageView imageView){
        synchronized (context){
            MyThread thr = new MyThread(context,bookName,account,"readPicture",imageView);
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /*
     * findAccount:
     * This method is used to find the account of the user
     */
    public String findAccount(Context context,String nickName){
        synchronized (context){
            MyThread thr = new MyThread(context,nickName,"findAccount");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thr.getAccount();
        }

    }

    /*
     * MyThread:
     * This class is an inner class and extends Thread class and will operate the functionality of
     * User in a single thread.
     */
    class MyThread extends Thread{

        /* Declarations of fields needed */
        private Context context;
        private String table;
        private String identity;
        private String emailAddress;
        private String password;
        private String option;
        private ObjectInputStream reader;
        private ObjectOutputStream writer;
        private int registerFlag;
        private int loginFlag;
        private String nickName;
        private String gender;
        private String phone;
        private String address;
        private String roll;
        private NoticeList noticeList;
        private OrderList orderList;
        private String account;
        private String bookName;
        private DataInputStream dis;
        private FileOutputStream fos;
        private ImageView imageView;

        /* Constructor that used to for the user to register */
        public MyThread(Context context,String table,String identity,String emailAddress,String password,String option){
            this.context = context;
            this.table = table;
            this.identity = identity;
            this.emailAddress = emailAddress;
            this.password = password;
            this.option = option;
            registerFlag = 1;
        }

        /* Constructor that used to for the user to login */
        public MyThread(Context context, String emailAddress, String password, String roll,String option){
            this.context = context;
            this.emailAddress = emailAddress;
            this.password = password;
            this.roll = roll;
            this.option = option;
        }

        /* Constructor that used to for the user to update their information */
        public MyThread(Context context, String emailAddress, String nickName, String gender, String phone,
                        String address, String roll, String option){
            this.context = context;
            this.emailAddress = emailAddress;
            this.nickName = nickName;
            this.gender = gender;
            this.phone = phone;
            this.address = address;
            this.roll = roll;
            this.option = option;
        }

        /* Constructor that used to for the user to check the notice */
        public MyThread(Context context,String account, String roll,String option){
            this.context = context;
            this.account = account;
            this.roll = roll;
            this.option = option;
        }

        /* Constructor that used to for the user to check the order */
        public MyThread(Context context, String bookName,String account,String option,ImageView imageView){
            this.imageView = imageView;
            this.context = context;
            this.bookName = bookName;
            this.account = account;
            this.option = option;

        }

        /* Constructor that used to for the user to find an account */
        public MyThread(Context context, String nickName,String option){
            this.context = context;
            this.nickName = nickName;
            this.option = option;
        }

        @Override
        /*
         * run:
         * This method is used to realize register, login, updateInfo, checkNotice, checkOrder and
         * findAccount methods in their single threads using socket.
         */
        public void run() {
            synchronized (context){
                Socket socket = new Socket();
                try {
                    socket.connect(new InetSocketAddress("10.0.0.5", 8080), 5000);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer = new ObjectOutputStream(socket.getOutputStream());
                    reader = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(option.equals("register")){
                    HashMap checkInfo=new HashMap();
                    HashMap userInfo=new HashMap();
                    String method = null;
                    String order = null;
                    if(identity.equals("seller")){
                        checkInfo.put("seller_email",emailAddress);

                        userInfo.put("seller_email",emailAddress);
                        userInfo.put("seller_password",password);
                        ArrayList<HashMap<String,String>> rs = null;
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Seller");
                            writer.flush();
                            writer.writeObject(checkInfo);
                            writer.flush();
                            writer.writeObject(method);
                            writer.flush();
                            writer.writeObject(order);
                            writer.flush();
                            Object object = reader.readObject();
                            rs = (ArrayList<HashMap<String,String>>)object;

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        if(rs.size()!=0){
                            registerFlag=0;
                        }else{
                            try {
                                writer.writeObject("c");
                                System.out.println("testtest");

                                writer.flush();
                                writer.writeObject("Seller");
                                writer.flush();
                                writer.writeObject(userInfo);
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        checkInfo.put("buyer_email",emailAddress);
                        userInfo.put("buyer_email",emailAddress);
                        userInfo.put("buyer_password", password);
                        ArrayList<HashMap<String,String>> rs = null;
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Buyer");
                            writer.flush();
                            writer.writeObject(checkInfo);
                            writer.flush();
                            writer.writeObject(method);
                            writer.flush();
                            writer.writeObject(order);
                            writer.flush();
                            Object object = reader.readObject();
                            rs = (ArrayList<HashMap<String,String>>)object;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        if(rs.size()!=0){
                            registerFlag=0;
                        }else{
                            try {

                                writer.writeObject("c");
                                System.out.println("testtest");

                                writer.flush();
                                writer.writeObject("Buyer");
                                writer.flush();
                                writer.writeObject(userInfo);
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }                    }
                    }
                }else if (option.equals("login")){
                    loginFlag = 0;//1 not exist, 2 pwd wrong, 0 successful
                    HashMap<String,String> where = new HashMap<>();
                    ArrayList<HashMap<String,String>> result = null;
                    String method = null;
                    String order = null;
                    if(roll.equals("seller")){
                        where.put("seller_email", emailAddress);
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Seller");
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

                        Object object = null;
                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        result = (ArrayList<HashMap<String,String>>)object;
                        if(result.size()==0){
                            loginFlag = 1;
                        }else{
                            String passwordReal = null;
                            for(int i = 0; i < result.size(); i++){
                                passwordReal = result.get(i).get("seller_password");
                            }
                            if(!passwordReal.equals(password)){
                                loginFlag = 2;
                            }
                        }
                    }else{
                        where.put("buyer_email", emailAddress);
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

                        Object object = null;
                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        result = (ArrayList<HashMap<String,String>>)object;
                        if(result.size()==0){
                            loginFlag = 1;
                        }else{
                            String passwordReal = null;
                            for(int i = 0; i < result.size(); i++){
                                passwordReal = result.get(i).get("buyer_password");
                            }
                            if(!passwordReal.equals(password)){
                                loginFlag = 2;
                            }
                        }


                    }
                }else if (option.equals("updateInfo")){

                    HashMap<String,String> update = new HashMap<String, String>();
                    HashMap<String,String> where = new HashMap<>();
                    if(roll.equals("seller")){
                        update.put("seller_nickname", nickName);
                        update.put("seller_gender", gender);
                        update.put("seller_phone", phone);
                        update.put("seller_address", address);
                        where.put("seller_email", emailAddress);
                        try {
                            writer.writeObject("u");
                            writer.flush();
                            writer.writeObject("Seller");
                            writer.flush();
                            writer.writeObject(update);
                            writer.flush();
                            writer.writeObject(where);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        update.put("buyer_nickname", nickName);
                        update.put("buyer_gender", gender);
                        update.put("buyer_phone", phone);
                        update.put("buyer_address", address);
                        where.put("buyer_email", emailAddress);
                        try {
                            writer.writeObject("u");
                            writer.flush();
                            writer.writeObject("Buyer");
                            writer.flush();
                            writer.writeObject(update);
                            writer.flush();
                            writer.writeObject(where);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else if (option.equals("checkNotice")){
                    noticeList = null;
                    HashMap<String,String> where = new HashMap<>();
                    String noticeContent;
                    int noticeSenderId;
                    String noticeSender;
                    String noticeReceiver;
                    String noticeSeller;
                    String noticeBuyer;
                    Date noticeDate = null;
                    String noticeDateStr;
                    if(roll.equals("seller")){
                        int sellerId;
                        where.put("seller_email", account);
                        String method = null;
                        String order = null;
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Seller");
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
                        sellerId = Integer.parseInt(result.get(0).get("seller_id"));
                        noticeReceiver = result.get(0).get("seller_nickname");
                        noticeSeller = noticeReceiver;
                        where = new HashMap<>();
                        where.put("seller_id_notice", String.valueOf(sellerId));
                        where.put("receiver_id", String.valueOf(sellerId));
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Notice");
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
                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        result = (ArrayList<HashMap<String,String>>)object;
                        noticeList = new NoticeList();
                        HashMap<String,String> receiverwhere = null;
                        ArrayList<HashMap<String,String>> senderRs;
                        for(int i =0; i < result.size();i++){
                            noticeContent= result.get(i).get("notice_content");
                            noticeSenderId = Integer.parseInt(result.get(i).get("sender_id"));
                            receiverwhere = new HashMap<>();
                            receiverwhere.put("buyer_id", String.valueOf(noticeSenderId));
                            try {
                                writer.writeObject("r");
                                writer.flush();
                                writer.writeObject("Buyer");
                                writer.flush();
                                writer.writeObject(receiverwhere);
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

                            senderRs = (ArrayList<HashMap<String,String>>)object;
                            noticeSender = senderRs.get(0).get("buyer_nickname");
                            noticeBuyer = noticeSender;
                            noticeDateStr = result.get(i).get("notice_date");
                            try {
                                noticeDate = format.parse(noticeDateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            noticeList.addNotice(noticeContent,noticeSender,noticeReceiver, noticeSeller,
                                    noticeBuyer,noticeDate);
                        }
                    }else{
                        int buyerId;
                        where.put("buyer_email", account);
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
                        buyerId = Integer.parseInt(result.get(0).get("buyer_id"));
                        noticeReceiver = result.get(0).get("buyer_nickname");
                        noticeBuyer = noticeReceiver;
                        where = new HashMap<>();
                        where.put("buyer_id_notice", String.valueOf(buyerId));
                        where.put("receiver_id",String.valueOf(buyerId));
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Notice");
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
                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        result = (ArrayList<HashMap<String,String>>)object;
                        noticeList = new NoticeList();
                        HashMap<String,String> senderwhere = null;
                        ArrayList<HashMap<String,String>> senderRs;
                        for(int j = 0; j<result.size();j++){
                            noticeContent= result.get(j).get("notice_content");
                            noticeSenderId = Integer.parseInt(result.get(j).get("sender_id"));
                            senderwhere = new HashMap<>();
                            senderwhere.put("seller_id", String.valueOf(noticeSenderId));
                            try {
                                writer.writeObject("r");
                                writer.flush();
                                writer.writeObject("Seller");
                                writer.flush();
                                writer.writeObject(senderwhere);
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
                            senderRs = (ArrayList<HashMap<String,String>>)object;
                            noticeSender = senderRs.get(0).get("seller_nickname");
                            noticeSeller = noticeSender;
                            noticeDateStr = result.get(j).get("notice_date");
                            try {
                                noticeDate = format.parse(noticeDateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            noticeList.addNotice(noticeContent,noticeSender,noticeReceiver, noticeSeller,
                                    noticeBuyer,noticeDate);

                        }
                    }
                }else if (option.equals("checkOrder")){
                    HashMap<String,String> where = new HashMap<>();
                    int orderNumber;
                    String orderSeller;
                    String orderBuyer;
                    float orderTotalPrice;
                    int bookId;
                    String orderBook;
                    Date orderDate = null;
                    String orderDateStr;
                    String orderState;
                    int sellerId;
                    int buyerId;
                    if (roll.equals("seller")){
                        where.put("seller_email", account);
                        String method = null;
                        String order = null;
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Seller");
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
                        sellerId = Integer.parseInt(result.get(0).get("seller_id"));
                        orderSeller = result.get(0).get("seller_nickname");
                        where = new HashMap<>();
                        where.put("seller_id_purchase", String.valueOf(sellerId));
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Purchase");
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
                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        result = (ArrayList<HashMap<String,String>>)object;
                        orderList = new OrderList();
                        HashMap<String,String> buyerwhere = null;
                        HashMap<String,String> bookwhere = null;
                        ArrayList<HashMap<String,String>> buyerRs;
                        ArrayList<HashMap<String,String>> bookRs;
                        for(int k = 0; k < result.size(); k++){
                            orderNumber = Integer.parseInt(result.get(k).get("purchase_id"));
                            orderState = result.get(k).get("purchase_state");
                            buyerId = Integer.parseInt(result.get(k).get("buyer_id_purchase"));
                            buyerwhere = new HashMap<>();
                            buyerwhere.put("buyer_id",String.valueOf(buyerId));
                            try {
                                writer.writeObject("r");
                                writer.flush();
                                writer.writeObject("Buyer");
                                writer.flush();
                                writer.writeObject(buyerwhere);
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
                            buyerRs = (ArrayList<HashMap<String,String>>)object;
                            orderBuyer = buyerRs.get(0).get("buyer_nickname");

                            bookId = Integer.parseInt(result.get(k).get("book_id_purchase"));
                            bookwhere = new HashMap<>();
                            bookwhere.put("book_id",String.valueOf(bookId));
                            try {
                                writer.writeObject("r");
                                writer.flush();
                                writer.writeObject("Book");
                                writer.flush();
                                writer.writeObject(bookwhere);
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
                            bookRs = (ArrayList<HashMap<String,String>>)object;
                            if(bookRs.size()!=0){
                                orderBook = bookRs.get(0).get("book_name");
                                orderTotalPrice = Float.parseFloat(result.get(k).get("purchase_price"));
                                orderDateStr = result.get(k).get("purchase_date");
                                try {
                                    orderDate = format.parse(orderDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                orderList.addOrder(orderNumber, orderSeller, orderBuyer, orderTotalPrice, orderBook,
                                        orderDate,orderState);
                            }

                        }
                    }else {
                        where.put("buyer_email", account);

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
                        buyerId = Integer.parseInt(result.get(0).get("buyer_id"));
                        orderBuyer = result.get(0).get("buyer_nickname");
                        where = new HashMap<>();
                        where.put("buyer_id_purchase", String.valueOf(buyerId));
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Purchase");
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
                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        result = (ArrayList<HashMap<String,String>>)object;
                        orderList = new OrderList();
                        HashMap<String, String> sellerwhere = null;
                        HashMap<String, String> bookwhere = null;
                        ArrayList<HashMap<String,String>> sellerRs;
                        ArrayList<HashMap<String,String>> bookRs;
                        for(int q = 0; q < result.size(); q++){
                            orderNumber = Integer.parseInt(result.get(q).get("purchase_id"));
                            orderState = result.get(q).get("purchase_state");
                            sellerId = Integer.parseInt(result.get(q).get("seller_id_purchase"));
                            sellerwhere = new HashMap<>();
                            sellerwhere.put("seller_id", String.valueOf(sellerId));
                            try {
                                writer.writeObject("r");
                                writer.flush();
                                writer.writeObject("Seller");
                                writer.flush();
                                writer.writeObject(sellerwhere);
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
                            sellerRs = (ArrayList<HashMap<String,String>>)object;
                            orderSeller = sellerRs.get(0).get("seller_nickname");
                            bookId = Integer.parseInt(result.get(q).get("book_id_purchase"));
                            bookwhere = new HashMap<>();
                            bookwhere.put("book_id", String.valueOf(bookId));
                            try {
                                writer.writeObject("r");
                                writer.flush();
                                writer.writeObject("Book");
                                writer.flush();
                                writer.writeObject(bookwhere);
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
                            bookRs = (ArrayList<HashMap<String,String>>)object;
                            if(bookRs.size()!=0) {
                                orderBook = bookRs.get(0).get("book_name");
                                orderTotalPrice = Float.parseFloat(result.get(q).get("purchase_price"));
                                orderDateStr = result.get(q).get("purchase_date");
                                try {
                                    orderDate = format.parse(orderDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                orderList.addOrder(orderNumber, orderSeller, orderBuyer, orderTotalPrice, orderBook,
                                        orderDate, orderState);
                            }
                        }
                    }

                }else if(option.equals("readPicture")){
                    try {
                        writer.writeObject("rp");
                        writer.flush();
                        writer.writeObject(bookName);
                        writer.flush();
                        writer.writeObject(account);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] inputByte = null;
                    int length = 0;
                    try {
                        dis = new DataInputStream(socket.getInputStream());
                        String saveDir = Environment.getExternalStorageDirectory()
                                + "/liangPic.jpg";

                        fos = new FileOutputStream(new File(saveDir));
                        inputByte = new byte[1024];
                        System.out.println("开始接收数据...");
                        while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {

                            if(length<1024){
                                System.out.println(length);
                                fos.write(inputByte, 0, length);
                                fos.flush();
                            }else{
                                System.out.println(length);
                                fos.write(inputByte, 0, length);
                                fos.flush();
                            }
                        }
                        if (fos != null)
                            fos.close();
                        if (dis != null){
                            dis.close();
                        }
                        dis = null;
                        fos = null;
                        System.out.println("完成接收");
                        String myJpgPath = "/sdcard/liangPic.jpg";
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
                        imageView.setImageBitmap(bm);
                    } catch (Exception e) {
                    }
                }else if(option.equals("findAccount")){
                    HashMap<String,String> findAccount = new HashMap<String,String>();
                    findAccount.put("seller_nickname",nickName);
                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Seller");
                        writer.flush();
                        writer.writeObject(findAccount);
                        writer.flush();
                        writer.writeObject(method);
                        writer.flush();
                        writer.writeObject(order);
                        writer.flush();
                        ArrayList<HashMap<String,String>> result = new ArrayList<>();
                        Object object = new Object();
                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        result = (ArrayList<HashMap<String,String>>)object;
                        account = result.get(0).get("seller_email");

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

        /*
         * getRegisterFlag:
         * Get method of regiserFlag
         */
        public int getRegisterFlag() {
            return registerFlag;
        }

        /*
         * setRegisterFlag:
         * Set method of regiserFlag
         */
        public void setRegisterFlag(int registerFlag) {
            this.registerFlag = registerFlag;
        }

        /*
         * getLoginFlag:
         * Get method of loginFlag
         */
        public int getLoginFlag() {
            return loginFlag;
        }

        /*
         *  getNoticeList:
         *  Get method of noticeList
         */
        public NoticeList getNoticeList() {
            return noticeList;
        }

        /*
         *  getOrderList:
         *  Get method of orderList
         */
        public OrderList getOrderList() {
            return orderList;
        }

        /*
         * getAccount:
         * Get method of account
         */
        public String getAccount() {
            return account;
        }

        /*
         * setAccount:
         * Set method of account
         */
        public void setAccount(String account) {
            this.account = account;
        }
    }
}
