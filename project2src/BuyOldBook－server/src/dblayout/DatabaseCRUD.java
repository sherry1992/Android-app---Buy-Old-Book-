/**
 * project2_Yidil_shuruil
 * 
 * DatabaseCRUD:
 * This class contains the create, read, update and delete operations to tables
 */
package dblayout;

import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.sql.ResultSet;
import java.util.HashMap;

public class DatabaseCRUD {
	
//Instantiate DatabaseConnector
private DatabaseConnector Connector= new DatabaseConnector();
	
/*
 * addToTable;
 * This method is used to add data into a particular table
 */
	public void addToTable(String tableName, HashMap<String,String>insert){
		Connection conn=Connector.connectDB();
		try {
			Statement stmt= conn.createStatement();
			Set<String> keys=insert.keySet();
			Iterator<String> it=keys.iterator();
			String where="";
			String values="";
			while(it.hasNext()){
				String str=(String) it.next();
				where=where+str;
				values=values+"'"+insert.get(str)+"'";
				if(it.hasNext()){
					where=where+",";
					values=values+",";
				}
			}
			String sql="insert into "+tableName+" ( "+where+" ) "+"values ("+values+");";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	 * readData:
	 * This method is used to read data from a particular table
	 */
	
	public ArrayList<HashMap<String,String>> readData(String tableName,HashMap<String,String> search,String method, String order){
		Connection conn=Connector.connectDB();
		ResultSet result=null;
		String command=null;
		Set<String> keys=search.keySet();
		HashMap <Integer, String>orderedKeys=new HashMap<Integer ,String>();
		Iterator <String>it1=keys.iterator();
		String where="";
		int i=1;
		while(it1.hasNext()){
			String key=(String) it1.next();
			orderedKeys.put(i, key);
			where=where+key+"=?";
			if(it1.hasNext()){
				where=where+" AND ";
			}
			i++;
		}
		if(method==null && order==null){
			if(search.size()==0){
				command="SELECT * FROM "+tableName+";";
			}else{
				command="SELECT * FROM "+tableName+" WHERE "+where+";";
			}
			
		}else{
			if(search.size()==0){
				command="SELECT * FROM "+tableName+" ORDER BY "+ method+" "+order+";";
			}else{
				command="SELECT * FROM "+tableName+" WHERE "+where+" ORDER BY "+ method+" "+order+";";
			}
			
		}
		
		System.out.println(tableName);
		System.out.println(where);
		System.out.println(method);
		System.out.println(order);
		System.out.println(command);
		try {
			PreparedStatement stmt = conn.prepareStatement(command);
			int j=1;
			int count=0;
			while(count<search.size()){
				stmt.setString(j,search.get(orderedKeys.get(j)));
				j++;
				System.out.println(j);
				count++;
			}
			
			result=stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSetMetaData resultM = null;
		int columnCount = 0;
		try {
			resultM = result.getMetaData();
			columnCount = resultM.getColumnCount();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		ArrayList<HashMap<String,String>> resultArrayList = new ArrayList<HashMap<String,String>>();
		try {
			while(result.next()){
				HashMap<String, String> record = new HashMap<String,String>();
				for(int k = 1; k<= columnCount;k++){
					record.put(resultM.getColumnName(k), result.getString(k));
				}
				resultArrayList.add(record);
			}
			for(int m = 0; m < resultArrayList.size(); m++){
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultArrayList;
	}
	
	
	/*
	 * updateData:
	 * This method is used to update data of a particular table
	 */
	public void updateData (String tableName, HashMap<String,String> update,
            HashMap<String,String> search){
		//UPDATE Automobiles SET Baseprice = ?  WHERE A_Id = ?;
		Connection conn=Connector.connectDB();
		String command=null;
		HashMap <Integer,String>orderedKeys=new HashMap<Integer, String>();
		Set<String> keysUpdate=update.keySet();
		Set <String>keysSearch=search.keySet();
		Iterator <String>it1=keysUpdate.iterator();
		Iterator <String>it2=keysSearch.iterator();
		String updateSentence="";
		String where="";
		int i=1;
		while(it1.hasNext()){
			String key=(String) it1.next();
			orderedKeys.put(i, key);
			updateSentence=updateSentence+key+"=?";
			if(it1.hasNext()){
				updateSentence=updateSentence+" , ";
			}
			i++;
		}
		while(it2.hasNext()){
			String key=(String) it2.next();
			orderedKeys.put(i, key);
			where=where+key+"=?";
			if(it2.hasNext()){
				where=where+" AND ";
			}
			i++;
			
		}
		command="UPDATE "+tableName+" SET "+updateSentence+" WHERE "+where+";";
		System.out.println(command);
		try {
			PreparedStatement stmt = conn.prepareStatement(command);
			Iterator <String>it3=keysUpdate.iterator();
			Iterator <String>it4=keysSearch.iterator();
			int j=1;
			while(it3.hasNext()){
				it3.next();
				if(search.containsKey(orderedKeys.get(j))){
					stmt.setString(j,search.get(orderedKeys.get(j)));
				}else{
					stmt.setString(j,update.get(orderedKeys.get(j)));
				}
				j++;
				
			}
			while(it4.hasNext()){
				it4.next();
				if(search.containsKey(orderedKeys.get(j))){
					stmt.setString(j,search.get(orderedKeys.get(j)));
				}else{
					stmt.setString(j,update.get(orderedKeys.get(j)));
				}
				j++;
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	 * delete:
	 * This method is used to delete data from a table
	 */
	public void delete(String tableName, HashMap<String,String> deleteWhere){
		Connection conn=Connector.connectDB();
		String command=null;
		Set <String>keys=deleteWhere.keySet();
		HashMap <Integer, String>orderedKeys=new HashMap<Integer,String>();
		Iterator<String> it1=keys.iterator();
		String where="";
		int i=1;
		while(it1.hasNext()){
			String key=(String) it1.next();
			orderedKeys.put(i, key);
			where=where+key+"=?";
			if(it1.hasNext()){
				where=where+" AND ";
			}
			i++;
		}
		command="DELETE FROM "+tableName+" WHERE "+where+";";
		//System.out.println(command);
		try {
			PreparedStatement stmt = conn.prepareStatement(command);
			Iterator<String> it2=keys.iterator();
			int j=1;
			while(it2.hasNext()){
				it2.next();
				stmt.setString(j,deleteWhere.get(orderedKeys.get(j)));
				j++;
			}
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
 


}
