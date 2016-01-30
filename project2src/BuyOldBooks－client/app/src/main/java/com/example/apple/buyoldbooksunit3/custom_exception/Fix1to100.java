/**
 * project2_Yidil_shuruil
 *
 * Fix1to100:
 * This class is an helper class of MortException class and is used to deligate fixing method
 * for each exception.
 */
package com.example.apple.buyoldbooksunit3.custom_exception;

/**
 * Created by apple on 15-7-19.
 */
public class Fix1to100 {

    /*
    * fix1:
    * This method is used to deal with "Missing Information" and print the error message on
    * console
    */
    public void fix1(int errorno) {
        System.out.println("Error "+errorno+": Missing Information!");
    }


    /*
    * fix2:
    * This method is used to deal with "The user forgot to select identity" and print the error message on
    * console
    */
    public void fix2(int errorno) {
        System.out.println("Error "+errorno+": The user forgot to select identity!");
    }



    /*
     * fix3:
     * This method is used to deal with "The Buyer forgot to input keywords" and print the error message on
     * console
     */
    public void fix3(int errorno) {
        System.out.println("Error "+errorno+": The Buyer forgot to input keywords!");
    }

    /*
   * fix4:
   * This method is used to deal with "The Buyer forgot to select sort method" and print the error message on
   * console
   */
    public void fix4(int errorno) {
        System.out.println("Error "+errorno+": The Buyer forgot to select sort method !");
    }

    /*
   * fix5:
   * This method is used to deal with "The Buyer's cart is empty" and print the error message on
   * console
   */
    public void fix5(int errorno) {
        System.out.println("Error "+errorno+": The Buyer's cart is empty !");
    }


}

