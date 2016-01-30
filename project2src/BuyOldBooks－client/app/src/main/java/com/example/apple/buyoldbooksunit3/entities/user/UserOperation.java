/**
 * UserOperation:
 * The class UserOperation is an interface which defined those common method between the class
 * Seller and the class Buyer. So class Seller and Buyer have to implement this interface, and
 * have to override the methods defined in this interface.

 */
package com.example.apple.buyoldbooksunit3.entities.user;

import android.content.Context;

import com.example.apple.buyoldbooksunit3.entities.notice.NoticeList;
import com.example.apple.buyoldbooksunit3.entities.order.OrderList;

public interface UserOperation {

    /*
    * register:
    * This method is to let the user to register on this application
    */
    public int register(Context context,String table,String identity,String emailAddress,String password);

    /*
     * login:
     * This method is let the user to log into this application
     */
    public int login(Context context, String eMail, String pwd, String roll);

    /*
     * updateInfo:
     * This method is used to update the information of the user
     */
    public void updateInfo(Context context, String eMail, String nickName, String gender,
                           String cellPhone, String homeAddress, String roll);

    /*
     * chechNotice:
     * This method is used to check the notice
     */
    public NoticeList checkNotice(Context context,String account, String roll);

    /*
     * checkOrder:
     * This method is used to check the order
     */
    public OrderList checkOrder(Context context,String account, String roll);
}
