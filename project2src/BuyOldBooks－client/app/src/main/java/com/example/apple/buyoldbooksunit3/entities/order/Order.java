/**
 * project2_Yidil_shuruil
 *
 * Order:
 * This class represent order object and is an internal class
 */
package com.example.apple.buyoldbooksunit3.entities.order;

import java.util.Date;

/* Declarations of Order's fields */
public class Order {
    private int orderNumber;
    private String orderSeller;
    private String orderBuyer;
    private float orderTotalPrice;
    private String orderBook;
    private Date orderDate;
    private String orderState;

    /* Constructor with arguments */
    protected Order(int orderNumber,String orderSeller,String orderBuyer, float orderTotalPrice,
                    String orderBook,Date orderDate,String orderState){
        this.orderNumber = orderNumber;
        this.orderSeller = orderSeller;
        this.orderBuyer = orderBuyer;
        this.orderTotalPrice = orderTotalPrice;
        this.orderBook = orderBook;
        this.orderDate = orderDate;
        this.orderState = orderState;

    }

    /*
     * getOrderNumber:
     * Get method of orderNumber
     */
    protected int getOrderNumber() {
        return orderNumber;
    }

    /*
     * getOrderSeller:
     * Get method of orderSeller
     */
    protected String getOrderSeller() {
        return orderSeller;
    }


    /*
     * getOrderBuyer;
     * Get method of orderBuyer
     */
    protected String getOrderBuyer() {
        return orderBuyer;
    }

    /*
     * getOrderTotalPrice;
     * Get method of orderTotalPrice
     */
    protected float getOrderTotalPrice() {
        return orderTotalPrice;
    }

    /*
     * getOrderDate:
     * Get method of orderDate
     */
    protected Date getOrderDate() {
        return orderDate;
    }

    /*
     * getOrderBook;
     * Get method of orderBook
     */
    protected String getOrderBook() {
        return orderBook;
    }

    /*
     * getOrderState:
     * Get method of orderState
     */
    public String getOrderState() {
        return orderState;
    }


}
