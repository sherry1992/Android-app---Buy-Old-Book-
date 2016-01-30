/**
 * project2_Yidil_shuruil
 * 
 * ServerDriver;
 * This class is used to create and start a new server.
 */
package server;

public class ServerDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//create a new Sever
	
		Server server = new Server(8080);
		
		//start work
		server.startServer();  
	} 

}
        