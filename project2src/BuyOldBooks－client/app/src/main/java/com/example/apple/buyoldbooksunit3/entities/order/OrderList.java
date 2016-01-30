/**
 * project2_Yidil_shuruil
 *
 * OrderList:
 * This class serves as an external class and is used to manage Order such as get the information
 * of an order and insert a new order into the order list.
 */
package com.example.apple.buyoldbooksunit3.entities.order;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderList {
    private ArrayList<Order> orderList;                //An ArrayList that stores orders

    /* Constructor with no-arg */
    public OrderList(){
        orderList = new ArrayList<>();
    }

    /*
     * getOrderList:
     * Get method of getOrderList
     */
    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    /*
     * addNotice:
     * Add a new order into the order list
     */
    public void addOrder(int orderNumber,String orderSeller,String orderBuyer,float orderTotalPrice,
                         String orderBook,Date orderDate,String orderState){
        Order order = new Order(orderNumber,orderSeller,orderBuyer,orderTotalPrice,orderBook,orderDate,orderState);
        orderList.add(order);
    }

    /*
     * getOrderNumber:
     * Get the number of a particular order in order list
     */
    public int getOrderNumber(int index){
        return orderList.get(index).getOrderNumber();
    }

    /*
     * getOrderSeller:
     * Get the orderSeller of a particular order in order list
     */
    public String getOrderSeller(int index){
        return orderList.get(index).getOrderSeller();
    }

    /*
     * getOrderBuyer:
     * Get the orderBuyer of a particular order in order list
     */
    public String getOrderBuyer(int index){
        return orderList.get(index).getOrderBuyer();
    }

    /*
     * getOrderTotalPrice:
     * Get the orderTotalPrice of a particular order in order list
     */
    public float getOrderTotalPrice(int index){
        return orderList.get(index).getOrderTotalPrice();
    }

    /*
     * getOrderBook:
     * Get the orderBook of a particular order in order list
     */
    public String getOrderBook(int index){
        return orderList.get(index).getOrderBook();
    }

    /*
     * getOrderDate:
     * Get the orderDate of a particular order in order list
     */
    public Date getOrderDate(int index){
        return orderList.get(index).getOrderDate();
    }

    /*
     * getOrderState:
     * Get the orderState of a particular order in order list
     */
    public String getOrderState(int index) {
        return orderList.get(index).getOrderState();
    }

    /*
     * orderInfo;
     * This method is used to search the information of a particular order according to the order's
     * sender and order's date and return the information in a HashMap.
     */
    public HashMap orderInfo(int orderNumber,Context context){
        synchronized (context){
            MyThreadOrderList thr = new MyThreadOrderList(orderNumber,context);
            thr.start();
            try {
                context.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thr.getInfo();
        }

    }

    /*
     * MyThreadOrderList Class
     * This class is an inner class and extends Thread class and will search the information of the
     * order in a new thread
     */
    class MyThreadOrderList extends Thread{

        /* Declare the fields that are used to search the information of a new thread */
        private int orderNumber;
        private Context context;
        private HashMap info;
        private ObjectInputStream reader;
        private ObjectOutputStream writer;

        /* Constructor with arguments */
        public MyThreadOrderList(int orderNumber,Context context){
            this.orderNumber = orderNumber;
            this.context = context;
        }

        /*
         * run:
         * This method is used to connect to the Server and get the information from the database of
         * server using socket
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
                info=new HashMap();
                for(int i=0;i<orderList.size();i++){
                    if(getOrderNumber(i)==orderNumber){
                        info.put("orderNumber",getOrderNumber(i));
                        info.put("orderSeller",getOrderSeller(i));
                        info.put("orderBuyer",getOrderBuyer(i));
                        info.put("orderBook",getOrderBook(i));
                        info.put("orderTotalPrice",getOrderTotalPrice(i));
                        info.put("orderDate", getOrderDate(i));
                        info.put("orderState",getOrderState(i));
                        HashMap<String,String>sellerIdWhere = new HashMap<>();
                        sellerIdWhere.put("seller_nickname", getOrderSeller(i));
                        System.out.println(getOrderSeller(i));
                        String method = null;
                        String order = null;
                        try {
                            writer.writeObject("r");
                            writer.flush();
                            writer.writeObject("Seller");
                            writer.flush();
                            writer.writeObject(sellerIdWhere);
                            writer.flush();
                            writer.writeObject(method);
                            writer.flush();
                            writer.writeObject(order);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ArrayList<HashMap<String,String>> sellerRs = null;
                        Object object = null;

                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sellerRs = (ArrayList<HashMap<String,String>>)object;
                        int sellerId = Integer.parseInt(sellerRs.get(0).get("seller_id"));
                        HashMap<String,String> where = new HashMap<>();
                        System.out.println(getOrderBook(i) + "  " + sellerId);
                        where.put("book_name",getOrderBook(i));
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
                        ArrayList<HashMap<String,String>> result = null;
                        try {
                            object = reader.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        result = (ArrayList<HashMap<String,String>>)object;
                        if(result.size()!=0){
                            info.put("bookDepreciation", result.get(0).get("depreciation"));
                            info.put("paymentMethod",result.get(0).get("payment"));
                            info.put("expressMethod",result.get(0).get("express"));
                            System.out.print(info);
                        }
                        break;
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
         * getInfo:
         * Get method of info
         */
        public HashMap getInfo() {
            return info;
        }

    }
}
