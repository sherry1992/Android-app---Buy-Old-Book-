/**
 * project2_Yidil_shuruil
 *
 * Seller:
 * The Seller class represent the concept of Buyer and extends User class
 */
package com.example.apple.buyoldbooksunit3.entities.user;

import android.content.Context;
import com.example.apple.buyoldbooksunit3.entities.book.BookList;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
public class Seller extends User implements UserOperation{
    private BookList availableBookList;                     //using BookList store the available books

    /* Constructor with no_arg */
    public Seller(){
        availableBookList = new BookList();
    }


    /*
     * uploadBook:
     * This method is used to upload a new book
     */
    public void uploadBook(Context context,String emailAddress,byte[] photoByte,String bookName, String bookPrice,
                           String depreciation, String description,String payment,String express){
        MyThreadSeller thr = new MyThreadSeller(context,emailAddress,photoByte,bookName,bookPrice,depreciation,
                description,payment,express,"uploadBook");
        thr.start();
    }

    /*
     * readBookList:
     * This method is used to read the available books in the form of BookList
     */

    public BookList readBookList(Context context, String account){
        synchronized (context){
            MyThreadSeller thr = new MyThreadSeller(context, account,"readBookList");
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
   * updateBook:
   * This method is used to update the book's information
   */
    public void updateBook(Context context,String emailAddress,String bookName,String bookPrice,String description){
        synchronized (context){
            MyThreadSeller thr = new MyThreadSeller(context,emailAddress,bookName,bookPrice,description,"updateBook");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    /*
     * deleteBook:
     * This method is used to delete a book
     */
    public void deleteBook(Context context,String emailAddress,String bookName){
        synchronized (context){
            MyThreadSeller thr = new MyThreadSeller(context, emailAddress,bookName,"deleteBook");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /*
    * confirmOrder:
    * This method is used to confirm an order the Buyer created
    */
    public void confirmOrder(Context context,String choice,int orderNumber){
        synchronized (context){
            MyThreadSeller thr = new MyThreadSeller(context,choice,orderNumber,"confirmOrder");
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /*
     * findId:
     * This method is used to find the Seller's id
     */
    public String findId(Context context, String name){
        synchronized (context){
            MyThreadSeller thr = new MyThreadSeller(context, name, "findId",1);
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thr.getIdd();
        }

    }
    /*
  * MyThreadSeller:
  * This class is an inner class and extends Thread class and will operate the functionality of
  * Seller in a single thread.
  */
    class MyThreadSeller extends Thread{

        /* Declarations of the fields needed */
        private Context context;
        private String emailAddress;
        private byte[] photoByte;
        private String bookName;
        private String bookPrice;
        private String depreciation;
        private String description;
        private String payment;
        private String express;
        private String option;
        private ObjectOutputStream writer;
        private ObjectInputStream reader;
        private BookList bookList;
        private String account;
        private String choice;
        private int orderNumber;
        private String name;
        private String id;
        private DataOutputStream dos;

        /* The constructor is for uploadBook */
        public MyThreadSeller (Context context,String emailAddress,byte[] photoByte, String bookName,String bookPrice,
                               String depreciation, String description,String payment,String express,
                               String option){
            this.context = context;
            this.emailAddress = emailAddress;
            this.photoByte=photoByte;
            this.bookName = bookName;
            this.bookPrice = bookPrice;
            this.depreciation = depreciation;
            this.description = description;
            this.payment = payment;
            this.express = express;
            this.option = option;
        }

        /*The constructor is for readBookList */
        public MyThreadSeller(Context context, String account,String option){
            this.context = context;
            this.account = account;
            this.option = option;
        }

        /*The constructor is for updateBook */
        public MyThreadSeller(Context context,String emailAddress,String bookName,String bookPrice,
                              String description,String option){
            this.context = context;
            this.emailAddress = emailAddress;
            this.bookName = bookName;
            this.bookPrice = bookPrice;
            this.description = description;
            this.option = option;
        }

        /*The constructor is for deleteBook*/
        public MyThreadSeller(Context context,String emailAddress,String bookName,String option){
            this.context = context;
            this.emailAddress = emailAddress;
            this.bookName = bookName;
            this.option = option;
        }
        public MyThreadSeller(Context context,String choice,int orderNumber,String option){
            this.context = context;
            this.choice = choice;
            this.orderNumber = orderNumber;
            this.option = option;
        }

        /*The constructor is for confirmOrder*/
        public MyThreadSeller(Context context, String name,String option,int a){
            this.context = context;
            this.name = name;
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
                    dos = new DataOutputStream(socket.getOutputStream());

                    writer = new ObjectOutputStream(socket.getOutputStream());
                    reader = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(option.equals("uploadBook")){
                    HashMap check=new HashMap();
                    check.put("seller_email", emailAddress);
                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Seller");
                        writer.flush();
                        writer.writeObject(check);
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
                    int seller_id=Integer.parseInt(c.get(0).get("seller_id"));
                    HashMap bookInfo=new HashMap();
                    bookInfo.put("seller_id_book",String.valueOf(seller_id));
                    bookInfo.put("book_name",bookName);
                    bookInfo.put("price", bookPrice);
                    bookInfo.put("depreciation", depreciation);
                    bookInfo.put("description", description);
                    bookInfo.put("payment", payment);
                    bookInfo.put("express", express);

                    try {
                        writer.writeObject("c");
                        writer.flush();
                        writer.writeObject("Book");
                        writer.flush();
                        writer.writeObject(bookInfo);
                        writer.flush();
                        writer.writeObject("p");
                        writer.flush();
                        writer.writeObject(emailAddress);
                        writer.flush();
                        writer.writeObject(bookName);
                        writer.flush();
//                        int length = photoByte.length;
//                        int lengthstart = 0;
//                        while (length > 0) {
//                            if(length>1024){
//                                dos.write(photoByte, lengthstart, 1024);
//                                length = length - 1024;
//                                lengthstart = lengthstart+1024;
//                                dos.flush();
//                            }else{
//                                dos.write(photoByte, lengthstart, length);
//                                dos.flush();
//                                length = -1;
//                            }
//
//
//
//                        }


                        DataOutputStream dos = null;
                        FileInputStream fis = null;
                        int length = 0;
                        byte[] sendBytes = null;
                        dos = new DataOutputStream(socket.getOutputStream());
                        File file = new File("/sdcard/myImage/tmp.jpg");
                        fis = new FileInputStream(file);
                        sendBytes = new byte[1024];
                        while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                            dos.write(sendBytes, 0, length);
                            dos.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else if(option.equals("readBookList")){
                    HashMap<String,String> where = new HashMap<>();
                    String bookName;
                    float bookPrice;
                    float bookDepreciation;
                    String bookDescription;
                    String bookPayment;
                    String bookExpress;
                    String bookSeller;
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
                    bookSeller = result.get(0).get("seller_nickname");
                    where = new HashMap<>();
                    where.put("seller_id_book", String.valueOf(sellerId));
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Book");
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
                    bookList = new BookList();
                    for(int b = 0; b < result.size(); b++){
                        bookName = result.get(b).get("book_name");
                        bookPrice = Float.parseFloat(result.get(b).get("price"));
                        bookDepreciation = Float.parseFloat(result.get(b).get("depreciation"));
                        bookDescription = result.get(b).get("description");
                        bookPayment = result.get(b).get("payment");
                        bookExpress = result.get(b).get("express");
                        bookList.addBook(bookName,bookPrice,bookDepreciation,bookDescription,bookPayment,
                                bookExpress,bookSeller);
                    }


                }else if(option.equals("updateBook")){
                    HashMap check=new HashMap();
                    check.put("seller_email", emailAddress);
                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Seller");
                        writer.flush();
                        writer.writeObject(check);
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

                    int seller_id=Integer.parseInt(c.get(0).get("seller_id"));
                    HashMap searchInfo=new HashMap();
                    searchInfo.put("seller_id_book", String.valueOf(seller_id));
                    searchInfo.put("book_name",bookName);
                    HashMap updateInfo=new HashMap();
                    if(!bookPrice.equals("") && !description.equals("")){
                        updateInfo.put("price",bookPrice);
                        updateInfo.put("description",description);
                    }else if(!bookPrice.equals("") && description.equals("")){
                        updateInfo.put("price",bookPrice);
                    }else if(bookPrice.equals("") && !description.equals("")){
                        updateInfo.put("description",description);
                    }
                    try {
                        writer.writeObject("u");
                        writer.flush();
                        writer.writeObject("Book");
                        writer.flush();
                        writer.writeObject(updateInfo);
                        writer.flush();
                        writer.writeObject(searchInfo);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(option.equals("deleteBook")){
                    HashMap check=new HashMap();
                    check.put("seller_email", emailAddress);
                    String method = null;
                    String order = null;
                    try {
                        writer.writeObject("r");
                        writer.flush();
                        writer.writeObject("Seller");
                        writer.flush();
                        writer.writeObject(check);
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
                    int seller_id=Integer.parseInt(c.get(0).get("seller_id"));
                    HashMap deleteInfo=new HashMap();
                    deleteInfo.put("seller_id_book",String.valueOf(seller_id));
                    deleteInfo.put("book_name", bookName);
                    try {
                        writer.writeObject("d");
                        writer.flush();
                        writer.writeObject("Book");
                        writer.flush();
                        writer.writeObject(deleteInfo);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else if(option.equals("confirmOrder")){
                    HashMap<String,String> update = new HashMap<>();
                    update.put("purchase_state",choice);
                    HashMap<String,String> where = new HashMap<>();
                    where.put("purchase_id",String.valueOf(orderNumber));
                    try {
                        writer.writeObject("u");
                        writer.flush();
                        writer.writeObject("Purchase");
                        writer.flush();
                        writer.writeObject(update);
                        writer.flush();
                        writer.writeObject(where);
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(option.equals("findId")){
                    HashMap searchInfo=new HashMap();
                    searchInfo.put("seller_nickname",name);
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
                    id = c.get(0).get("seller_id");
                }
                try {
                    if(dos==null){
                        writer.writeObject("quit");
                        writer.flush();
                        socket.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                context.notifyAll();

            }

        }

        /*
         * get methods of bookList and id
         */
        public BookList getBookList() {
            return bookList;
        }

        public String getIdd() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
