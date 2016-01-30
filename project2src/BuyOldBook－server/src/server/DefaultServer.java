
/**
 * project2_Yidil_shuruil
 * 
 * DefaultSocketClient:
 * The DefaultSocketClient class is a threaded socket clients and it has facilities to read and write object.
 * Because this class extends Thread, every client calls that accept will run on a particular thread and will 
 * not block each other. In the run() method, fist it will call the openConnection method to open the ObjectStream
 * ,if success, then called handleSession method to accept request from the server and do related operations.
 * When it receive the "quit" information from the client, this socket will close and the server will waiting 
 * for other calls from the client. 

 * 
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import dblayout.DatabaseCRUD;
import dblayout.DatabaseConnector;

public class DefaultServer extends Thread{
	
	private DatabaseCRUD crud = new DatabaseCRUD();  //Instantiate DatabaseCRUD
    private ObjectInputStream reader;    //ObjectInputStream used when read Object from the stream
    private ObjectOutputStream writer;   //ObjectOutputStream used when wrie Object to stream
    private Socket socket;               //The socket that accept from the client
    DataInputStream dis = null;          //Declare a variable of DataInputSteam
    FileOutputStream fos = null;         //Declare a variable of FileOutputStream
    
    /* Constructor that use the accepted socket from client as parameter */
    public DefaultServer(Socket socket){
    	this.socket = socket;
    }
    

	/*
	 * openConnection:
	 * This method is overridden from the SocketClentInterface. Its function is to open the object streams.
	 */
    public boolean openConnection(){
    	try {
    		writer = new ObjectOutputStream(socket.getOutputStream());
    		reader = new ObjectInputStream(socket.getInputStream());
    		
    	} catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
    
    /*
	 * handleSession:
	 * This method is to handle all kinds of cases according to the request reading from the client
	 */
    public void handleSession(){
    	String option = null;
    	try {
    		
			while(dis==null&&(option = (String)reader.readObject())!=null){
				if(option.equals("c")){
					Object object = reader.readObject();
					String tableName = (String)object;
					Object object1 = reader.readObject();
					HashMap<String,String> insert = (HashMap<String,String>)object1;
					crud.addToTable(tableName, insert);
				}else if (option.equals("r")){
					Object object = reader.readObject();
					String tableName = (String)object;
					Object object1 = reader.readObject();
					HashMap<String,String> search = (HashMap<String,String>)object1;
					Object object2 = reader.readObject();
					String method = (String)object2;
					Object object3 = reader.readObject();
					String order = (String)object3;
					System.out.println(search);
					writer.writeObject(crud.readData(tableName, search, method, order));
				}else if(option.equals("u")){
					Object object = reader.readObject();
					String tableName = (String)object;
					Object object1 = reader.readObject();
					HashMap<String,String> update = (HashMap<String,String>)object1;
					Object object2 = reader.readObject();
					HashMap<String,String> search = (HashMap<String,String>)object2;
					crud.updateData(tableName, update, search);
				}else if(option.equals("d")){
					Object object = reader.readObject();
					String tableName = (String)object;
					Object object1 = reader.readObject();
					HashMap<String,String> deleteWhere = (HashMap<String,String>)object1;
					crud.delete(tableName, deleteWhere);
				}else if (option.equals("build")){
					DatabaseConnector databaseConnector = new DatabaseConnector();
					databaseConnector.createDataBase();
					databaseConnector.createTables();
				}else if (option.equals("p")){
					Object object = reader.readObject();
					String account = (String)object;
					object = reader.readObject();
					String bookName = (String)object;
					String path = "./"+ account+bookName+".jpg";
					HashMap<String,String>update = new HashMap<String,String>();
					update.put("book_photo", path);
					HashMap<String,String>search = new HashMap<String,String>();
					search.put("book_name", bookName);
					crud.updateData("Book", update, search);
			        byte[] inputByte = null;
			        int length = 0;
			        try {
						dis = new DataInputStream(socket.getInputStream());
				          fos = new FileOutputStream(new File(path));
				            inputByte = new byte[1024];
				            System.out.println("开始接收数据...");
				            while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
				                if(length<1024){
					                fos.write(inputByte, 0, length);
					                fos.flush();
				                }else{
					                fos.write(inputByte, 0, length);
					                fos.flush();
				                }
				                

				            }
			                if (fos != null)
			                    fos.close();
			                if (dis != null)
			                    dis.close();
			                dis = null;
			                fos = null;
				            System.out.println("完成接收");
			                break;

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }else if(option.equals("rp")){
			    	Object object = reader.readObject();
			    	String bookName = (String)object;
			    	object = reader.readObject();
			    	String account = (String)object;
			    	System.out.println(account+"lihjkb,kn");

			    	String method = null;
			    	String order = null;
			    	HashMap<String, String> search = new HashMap<String,String>();
			    	search.put("book_name", bookName);
			    	HashMap<String,String>sellerS = new HashMap<String,String>();
			    	sellerS.put("seller_email", account);
			    	ArrayList<HashMap<String,String>> resultS = crud.readData("Seller", sellerS, method, order);
			    	String sellerId = resultS.get(0).get("seller_id");
			    	search.put("seller_id_book",sellerId);
			    	System.out.println(bookName+" "+sellerId);
			    	ArrayList<HashMap<String,String>> result = crud.readData("Book", search, method, order);
			    	String path = result.get(0).get("book_photo");
		            DataOutputStream dos = null;
		            FileInputStream fis = null;
		            int length = 0;
		            byte[] sendBytes = null;
                    dos = new DataOutputStream(socket.getOutputStream());
                    File file = new File(path);
                    fis = new FileInputStream(file);
                    sendBytes = new byte[1024];
                    while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                        dos.write(sendBytes, 0, length);
                        dos.flush();
                    }
                    break;

			    }
				//operation: quit
				else if(option.equals("quit")){
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    /*
	 * closeSession:
	 * This method is overridden from the SocketClentInterface. Its function is to close the streams and socket.
	 */
    public void closeSession(){
    	try {

    		writer.close();
    		reader.close();
    		writer = null;
    		reader = null;

    		socket.close();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }


	/*
	 * run:
	 * First it will call the openConnection method to open the ObjectStreamif success, then called handleSession
	 * method to accept request from the server and do related operations. Then if the client want to quit, it 
	 * will call closeSession method to close the streams and sockets.
	 */
    public void run(){
    	if(openConnection()){
    		handleSession();
    		closeSession();
    	}

    	
    }


}
