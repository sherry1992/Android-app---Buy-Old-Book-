/**
 * project2_Yidil_shuruil
 *
 * Book:
 * This class represent book object and is an internal class
 */
package com.example.apple.buyoldbooksunit3.entities.book;

import java.io.Serializable;

public class Book implements Serializable {

    /* Declarations of Book's fields */
    private String bookName;
    private float bookPrice;
    private float bookDepreciation;
    private String bookDescription;
    private String bookPayment;
    private String bookExpress;
    private String bookSeller;


    /* Constructor with arguments */
    protected Book(String bookName, float bookPrice, float bookDepreciation,
                   String bookDescription,String bookPayment,String bookExpress,
                   String bookSeller){
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookDescription = bookDescription;
        this.bookDepreciation = bookDepreciation;
        this.bookPayment = bookPayment;
        this.bookExpress = bookExpress;
        this.bookSeller = bookSeller;
    }

    /*
    * getBookName:
    * Get method of bookName
    */
    protected String getBookName() {
        return bookName;
    }


    /*
    * getBookPrice:
    * Get method of bookPrice
    */
    protected float getBookPrice() {
        return bookPrice;
    }

    /*
     * getBookDepreciation:
     * Get method of bookDepreciation
     */
    protected float getBookDepreciation() {
        return bookDepreciation;
    }

    /*
     * setBookDepreciation:
     * Set method of bookDepreciation
     */
    protected void setBookDepreciation(float bookDepreciation) {
        this.bookDepreciation = bookDepreciation;
    }

    /*
     * getBookPayment:
     * Get method of bookPayment
     */
    protected String getBookDescription() {
        return bookDescription;
    }


    /*
    * getBookPayment:
    * Get method of bookPayment
    */
    protected String getBookPayment() {
        return bookPayment;
    }


    /*
     * getBookExpress:
     * Get method of bookExpress
     */
    protected String getBookExpress() {
        return bookExpress;
    }


    /*
    * getBookSeller:
    * Get method bookSeller
    */
    protected String getBookSeller() {
        return bookSeller;
    }


}
