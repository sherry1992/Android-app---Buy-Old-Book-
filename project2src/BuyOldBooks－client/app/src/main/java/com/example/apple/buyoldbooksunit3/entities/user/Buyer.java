/**
 * project2_Yidil_shuruil
 *
 * Buyer:
 * The Buyer class represent the concept of Buyer and extends User class
 *
 */
package com.example.apple.buyoldbooksunit3.entities.user;

import android.content.Context;
import com.example.apple.buyoldbooksunit3.entities.book.BookList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apple on 15-7-18.
 */
public class Buyer extends User implements UserOperation {
    private BookList cart;                                //Using BookList to represent a cart
    public Buyer(){
        cart = new BookList();
    }

    /*
     * getCartList:
     * This method is used to return the cart list in the form of BookList
     */
    public BookList getCartList(Context context,ArrayList bookNo) {
        synchronized (context){
            MyThreadBuyer thr = new MyThreadBuyer(context,bookNo,"getCartList");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return thr.getCartList();
        }

    }


    /*
    * clearCart:
    * This method is used to clear the cart
    */
    public void clearCart(Context context,String account){
        synchronized (context){
            MyThreadBuyer thr = new MyThreadBuyer(context, account,"clearCart");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }





    /*
     * searchBook:
     * This method is used to search the book the Buyer has searched in the form of BookList
     */

    public BookList searchBook(Context context,String topic,String sortMethod){
        synchronized(context){
            MyThreadBuyer thr = new MyThreadBuyer(context, topic, sortMethod,"searchBook");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thr.getBookList();

        }

    }

    /*
    * searchCart:
    * This method is to used to return the cart information stored in the database
    */
    public String searchCart(Context context,String account){
        synchronized (context){
            MyThreadBuyer thr = new MyThreadBuyer(context,account,"searchCart",1);
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(thr.getCart()==null)
                return "";

            return thr.getCart();
        }


    }

     /*
     * updateCart:
     * This method is used to update the information of the Buyer's cart
     */

    public void updateCart(Context context,String account,String bookName,String sellerName){
        synchronized (context){
            MyThreadBuyer thr = new MyThreadBuyer(context,account,bookName,sellerName,"updateCart");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }





    /*
     * createOrder:
     * This method is used to create order
     */
    public void createOrder(Context context,String account,String seller_id,String book_id , String price, String date, String state){
        synchronized(context){
            MyThreadBuyer thr = new MyThreadBuyer(context,account,seller_id,book_id,price,date,state,"createOrder");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /*
    * MyThreadBuyer:
    * This class is an inner class and extends Thread class and will operate the functionality of
    * Buyer in a single thread.
    */
    class MyThreadBuyer extends Thread{

        /* Declarations of the fields needed */
        private Context context;
        private ArrayList bookNo;
        private String option;
        private ObjectInputStream reader;
        private ObjectOutputStream writer;
        private BookList cartList;
        private String account;
        private String topic;
        private String sortMethod;
        private BookList bookList;
        private String cart;
        private String bookName;
        private String sellerName;
        private String seller_id;
        private String book_id;
        private String price;
        private String date;
        private String state;

        /* The constructor is for getCartList */
        public MyThreadBuyer(Context context,ArrayList bookNo, String option){
            this.context = context;
            this.bookNo = bookNo;
            this.option = option;
        }

        /* This constructor is used for clearCart */
        public MyThreadBuyer(Context context,String account,String option){
            this.context = context;
            this.account = account;
            this.option = option;


        }

        /*This constructor is used for searchBook*/
        public MyThreadBuyer(Context context,String topic,String sortMethod,String option){
            this.context = context;
            this.topic = topic;
            this.sortMethod = sortMethod;
            this.option = option;
        }

        /*This constructor is used for searchCart*/
        public MyThreadBuyer(Context context,String account,String option, int a){
            this.context = context;
            this.account = account;
            this.option = option;
        }

        /* This constructor is used for updateCart */
        public MyThreadBuyer(Context context,String account,String bookName,String sellerName,String option){
            this.context = context;
            this.account = account;
            this.bookName = bookName;
            this.sellerName = sellerName;
            this.option = option;

        }

        /*This constructor is used for createOrder*/
        public MyThreadBuyer(Context context,String account,String seller_id,String book_id,
                             String price, String date, String state,String option){
            this.context = context;
            this.account = account;
            this.seller_id = seller_id;
            this.book_id = book_id;
            this.price = price;
            this.date = date;
            this.state = state;
            this.option = option;
        }




        /*
        * run:
        * This method is used to realize getCartList, clearCart, searchBook, searchCart, updateCart and
        * createOrder methods in their single threads using socket.
        */
        @Override
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
                if(option.equals("getCartList")){

                    cartList=new BookList();
                    for(int i=0;i<bookNo.size();i++){
                        HashMap bookInfo=new HashMap();
                        HashMap searchInfo=new HashMap();
                        HashMap checkDelete = new HashMap();

                        String method = null;
                        String order = null;
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Book");
                            writer.flush();
                            writer.writeObject(checkDelete);
                            writer.flush();
                            writer.writeObject(method);
                            writer.flush();
                            writer.writeObject(order);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ArrayList<HashMap<String,String>> a = null;
                        Object object = null;

                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        a = (ArrayList<HashMap<String,String>>)object;
                        int flag = 0;
                        for(int j = 0;j<a.size();j++){
                            if(bookNo.get(i).equals(a.get(j).get("book_id"))){
                                flag = 1;
                                break;
                            }
                        }
                        if(flag == 1){
                            searchInfo.put("book_id", String.valueOf(bookNo.get(i)));
                        }else{
                            continue;
                        }
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Book");
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
                        String bookName = c.get(0).get("book_name");
                        float bookPrice=Float.parseFloat(c.get(0).get("price"));
                        float bookDepreciation=Float.parseFloat(c.get(0).get("depreciation"));
                        String bookDescription=c.get(0).get("description");
                        String payment=c.get(0).get("payment");
                        String express=c.get(0).get("express");
                        String seller_id=c.get(0).get("seller_id_book");
                        // String seller_name=searchSellerName(context, seller_id);
                        HashMap searchInfoname=new HashMap();


                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Seller");
                            writer.flush();
                            writer.writeObject(searchInfoname);
                            writer.flush();
                            writer.writeObject(method);
                            writer.flush();
                            writer.writeObject(order);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ArrayList<HashMap<String,String>> c2 = null;

                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        c2 = (ArrayList<HashMap<String,String>>)object;
                        String seller_name=c2.get(0).get("seller_nickname");

                        searchInfoname.put("seller_id",seller_id);
                        bookInfo.put("bookName",bookName);
                        bookInfo.put("bookPrice",bookPrice);
                        bookInfo.put("bookDepreciation",bookDepreciation);
                        bookInfo.put("bookDescription",bookDescription);
                        bookInfo.put("payment",payment);
                        bookInfo.put("express",express);
                        bookInfo.put("seller_name",seller_name);


                        cartList.addBook((bookInfo.get("bookName")).toString(), Float.parseFloat((bookInfo.get("bookPrice").toString())), Float.parseFloat((bookInfo.get("bookDepreciation")).toString()),
                                (bookInfo.get("bookDescription")).toString(),(bookInfo.get("payment")).toString(),(bookInfo.get("express")).toString(),(bookInfo.get("seller_name")).toString());
                    }

                }if(option.equals("clearCart")){

                    HashMap searchInfo=new HashMap();
                    searchInfo.put("buyer_email", account);
                    HashMap updateInfo=new HashMap();
                    updateInfo.put("buyer_cart", "");
                    try {
                        writer.writeObject("u");
                        writer.flush();
                        writer.writeObject("Buyer");
                        writer.flush();
                        writer.writeObject(updateInfo);
                        writer.flush();
                        writer.writeObject(searchInfo);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }if(option.equals("searchBook")){

                    bookList=new BookList();
                    HashMap searchInfo=new HashMap();
                    ArrayList<HashMap<String,String>> c=null;
                    String method = null;
                    String order = null;
                    if(sortMethod.equals("ByPrice")) {
                        method = "price";
                        order = "asc";

                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Book");
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
                        Object object = null;

                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        c = (ArrayList<HashMap<String,String>>)object;
                    }else{
                        method = "depreciation";
                        order = "desc";
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Book");
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
                        Object object = null;

                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        c = (ArrayList<HashMap<String,String>>)object;
                    }
                    for(int i = 0; i < c.size(); i++){
                        String bookName=c.get(i).get("book_name");
                        if(bookName.contains(topic)){
                            //searchedBooks.add(bookName);
                            HashMap check=new HashMap();
                            check.put("book_name", bookName);

                            String method1 = null;
                            String order1 = null;
                            try {
                                writer.writeObject("r");
                                writer.flush();
                                writer.writeObject("Book");
                                writer.flush();
                                writer.writeObject(check);
                                writer.flush();
                                writer.writeObject(method1);
                                writer.flush();
                                writer.writeObject(order1);
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ArrayList<HashMap<String,String>> cr = null;
                            Object object = null;

                            try {
                                object = reader.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            cr = (ArrayList<HashMap<String,String>>)object;
                            int seller_id = Integer.parseInt(cr.get(0).get("seller_id_book"));

                            HashMap check2=new HashMap();
                            check2.put("seller_id",String.valueOf(seller_id));


                            try {
                                writer.writeObject("r");
                                writer.flush();
                                writer.writeObject("Seller");
                                writer.flush();
                                writer.writeObject(check2);
                                writer.flush();
                                writer.writeObject(method1);
                                writer.flush();
                                writer.writeObject(order1);
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ArrayList<HashMap<String,String>> cr2 = null;

                            try {
                                object = reader.readObject();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            cr2 = (ArrayList<HashMap<String,String>>)object;
                            String sellerName=cr2.get(0).get("seller_nickname");
                            float price=Float.parseFloat(c.get(i).get("price"));
                            float depreciation=Float.parseFloat(c.get(i).get("depreciation"));
                            String description=c.get(i).get("description");
                            String payment=c.get(i).get("payment");
                            String express=c.get(i).get("express");
                            bookList.addBook(bookName,price,depreciation,description,payment,express,sellerName);
                        }
                    }

                }if(option.equals("searchCart")){
                    HashMap searchInfo=new HashMap();
                    searchInfo.put("buyer_email",account);

                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Buyer");
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
                    cart = c.get(0).get("buyer_cart");


                }if(option.equals("updateCart")){
                    //根据bookName 和 sellerName找id
                    HashMap searchInfo1=new HashMap();
                    searchInfo1.put("seller_nickname",sellerName);

                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Seller");
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
                    ArrayList<HashMap<String,String>> c1 = null;
                    Object object = null;

                    try {
                        object = reader.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    c1 = (ArrayList<HashMap<String,String>>)object;
                    int seller_id= Integer.parseInt(c1.get(0).get("seller_id"));


                    HashMap searchInfo2=new HashMap();
                    searchInfo2.put("seller_id_book",String.valueOf(seller_id));
                    searchInfo2.put("book_name",bookName);


                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Book");
                        writer.flush();
                        writer.writeObject(searchInfo2);
                        writer.flush();
                        writer.writeObject(method);
                        writer.flush();
                        writer.writeObject(order);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<HashMap<String,String>> c2 = null;

                    try {
                        object = reader.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    c2 = (ArrayList<HashMap<String,String>>)object;
                    int book_id=Integer.parseInt(c2.get(0).get("book_id"));


                    HashMap searchInfo3=new HashMap();
                    searchInfo3.put("buyer_email",account);


                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Buyer");
                        writer.flush();
                        writer.writeObject(searchInfo3);
                        writer.flush();
                        writer.writeObject(method);
                        writer.flush();
                        writer.writeObject(order);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<HashMap<String,String>> c3 = null;

                    try {
                        object = reader.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    c3 = (ArrayList<HashMap<String,String>>)object;
                    String cart=c3.get(0).get("buyer_cart");


                    if(cart==null) cart="";

                    if(!cart.contains(String.valueOf(book_id))){
                        cart=cart+book_id+":";
                    }


                    HashMap updateInfo=new HashMap();
                    updateInfo.put("buyer_cart",cart);
                    HashMap searchInfo=new HashMap();
                    searchInfo.put("buyer_email", account);
                    try {
                        writer.writeObject("u");
                        writer.flush();
                        writer.writeObject("Buyer");
                        writer.flush();
                        writer.writeObject(updateInfo);
                        writer.flush();
                        writer.writeObject(searchInfo);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }if(option.equals("createOrder")){
                    HashMap searchInfo=new HashMap();
                    searchInfo.put("buyer_email",account);

                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Buyer");
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
                    String id= c.get(0).get("buyer_id");

                    HashMap orderInfo=new HashMap();
                    orderInfo.put("buyer_id_purchase",id);
                    orderInfo.put("seller_id_purchase",seller_id);
                    orderInfo.put("book_id_purchase",book_id);
                    orderInfo.put("purchase_price",price);
                    orderInfo.put("purchase_date",date);
                    orderInfo.put("purchase_state",state);

                    try {
                        writer.writeObject("c");
                        writer.flush();
                        writer.writeObject("Purchase");
                        writer.flush();
                        writer.writeObject(orderInfo);
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

        /*
         * get methods of cartList and bookList and cart
         */
        public BookList getCartList() {
            return cartList;
        }


        public BookList getBookList() {
            return bookList;
        }


        public String getCart() {
            return cart;
        }

    }



}
