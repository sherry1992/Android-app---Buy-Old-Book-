/**
 * project2_Yidil_shuruil
 * 
 * DatabaseConnector:
 * This class is mainly used for create database and tables and delete the dabase
 */
package dblayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
public class DatabaseConnector {
	
	/*
	 * createDataBase:
	 * This method is used to create database
	 */
	public void createDataBase(){

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3307","root","680113");
			Statement stam=conn.createStatement();
			stam.execute("CREATE DATABASE IF NOT EXISTS BuyOldBook;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/*
	 * createTables
	 * This method is used to create tables
	 */
	public void createTables(){
		Connection conn = connectDB();
		try {
			Statement stmt =conn.createStatement();
			stmt.execute("CREATE TABLE  IF NOT EXISTS Seller(seller_id int AUTO_INCREMENT, "
					+ "seller_email varchar(30) , seller_password varchar(30) ,"
					+ "seller_nickname varchar(30) , seller_gender varchar(10) ,"
					+ " seller_phone varchar(30) , seller_address varchar(30) , PRIMARY KEY (seller_id));");
			stmt.execute("CREATE TABLE  IF NOT EXISTS Buyer(buyer_id int AUTO_INCREMENT, "
					+ "buyer_email varchar(30), buyer_password varchar(30) , "
					+ "buyer_nickname varchar(30), buyer_gender varchar(10) ,"
					+ " buyer_phone varchar(30), buyer_address varchar(30),buyer_cart varchar(30), PRIMARY KEY (buyer_id));");
			

			stmt.execute("CREATE TABLE  IF NOT EXISTS Book(book_id int AUTO_INCREMENT, "
					+ "seller_id_book int, book_name varchar(30), price REAL,"
					+ " depreciation REAL, book_photo varchar(60), description varchar(300), payment varchar(30),"
					+ " express varchar(30), PRIMARY KEY (book_id), FOREIGN KEY(seller_id_book) REFERENCES Seller(seller_id));");
			stmt.execute("CREATE TABLE  IF NOT EXISTS Purchase" +
                    "(purchase_id int  AUTO_INCREMENT, seller_id_purchase int," +
                    " buyer_id_purchase int, book_id_purchase int, purchase_price REAL," +
                    " purchase_date varchar(30), purchase_state varchar(10), PRIMARY KEY (purchase_id)," +
                    " FOREIGN KEY(seller_id_purchase) REFERENCES Seller(seller_id), FOREIGN KEY(buyer_id_purchase) REFERENCES Buyer(buyer_id));");
			stmt.execute("CREATE TABLE IF NOT EXISTS Notice" +
                    "(notice_id int  AUTO_INCREMENT, sender_id int, receiver_id int ," +
                    "seller_id_notice int, buyer_id_notice int, notice_content varchar(300)," +
                    " notice_date varchar(30), PRIMARY KEY (notice_id), FOREIGN KEY(seller_id_notice) REFERENCES Seller(seller_id)," +
                    "FOREIGN KEY(buyer_id_notice) REFERENCES Buyer(buyer_id));");
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * connectDB:
	 * This method is used to connect the database
	 */
	public Connection connectDB(){
		String database="BuyOldBook";
		String url="jdbc:mysql://127.0.0.1:3307";
		String username="root";
		String password="680113";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			return DriverManager.getConnection(url+"/"+database, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/*
	 *clearDB:
	 *This method is used to delete the database
	 */
	public void clearDB(){
		Connection conn = connectDB();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("DROP DATABASE BuyOldBook");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
