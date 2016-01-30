/**
 * project2_Yidil_shuruil
 *
 * CustomException:
 * This  class is a custom exception class extends the super class Exception.
 * This class can handle the exception if the user forgets to input important values and etc.
 */

package com.example.apple.buyoldbooksunit3.custom_exception;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;

public class CustomException extends Exception{

    private int errorno;                 //Error number
    private String errormsg;             //Error message



    /* Constructor with no argument */
    public CustomException(){
        super();
    }

    /* Constructor with both errorno and errormsg as arguments */
    public CustomException(int errorno,String errormsg){
        this.errorno=errorno;
        this.errormsg=errormsg;
    }

    /* Get method of errorno */
    public int getErrorno() {
        return errorno;
    }

    /*Get method of errormsg*/
    public String getErrormsg() {
        return errormsg;
    }




    /*
     * fix:
     * This method is used to handle any of the three possible exceptions accoding to the errorno.
     */
    public  void  fix(Context context, int errorno,String errormsg){
        Fix1to100 fix=new Fix1to100();
        showDialog(context, errorno, errormsg);
        if(errorno==1) fix.fix1(errorno);
        if(errorno==2) fix.fix2(errorno);
        if(errorno==3) fix.fix3(errorno);
        if(errorno==4) fix.fix4(errorno);
        if(errorno==5) fix.fix5(errorno);


    }

    /*
     * showDialof:
     * This method is used to display a AlertDialog showing the exception message
     */
    public void showDialog(Context context, int errorno, String errormsg){
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Exception "+errorno+" !")
                .setMessage(errormsg).create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP); //window.setGravity(Gravity.BOTTOM);
        dialog.show();

    }

}
