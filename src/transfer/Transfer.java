package transfer;

import java.io.IOException;
import java.sql.*;

import connect.Connect;

interface Controls{
	public boolean check(int accno,float bal);
}
class MoneyTransfers implements Controls{
	private Connection con=null;
	public MoneyTransfers() {
		con=Connect.getConnection();
		// TODO Auto-generated constructor stub
	}
	public boolean check(int accountNumber,float bal) {
		
		 String query = "select balance from user WHERE account_number = ?";
		 try {
		 PreparedStatement stmt = con.prepareStatement(query);
	     stmt.setInt(1, accountNumber);
	     ResultSet rs=stmt.executeQuery();
	     float balance=0;
	     if(rs.next()) {
	     balance=rs.getFloat(1);
		 }
	     return bal<=balance;
		 }
		 catch(Exception e) {
			 System.out.println("Error Fetching Balance!!! Try Again");
		 }
		return false;
	}
	
}
public class Transfer extends MoneyTransfers {
	private Connection con=null;
	public Transfer() {
		con=Connect.getConnection();
		// TODO Auto-generated constructor stub
	}
	public float check(int accountNumber) {
		String query = "select balance from user WHERE account_number = ?";
		 try {
		 PreparedStatement stmt = con.prepareStatement(query);
	     stmt.setInt(1, accountNumber);
	     ResultSet rs=stmt.executeQuery();
	     float balance=0;
	     if(rs.next()) {
	     balance=rs.getFloat(1);
		 }
	     return balance;
		 }
		 catch(Exception e) {
			 System.out.println("Error Fetching Balance!!! Try Again");
			 return -1;
		 }
	}
	public void transfer(int from,int to,float amt){
		
		String updateQuery1 = "UPDATE user SET balance = balance + ? WHERE account_number = ?";
		String updateQuery2 = "UPDATE user SET balance = balance - ? WHERE account_number = ?";
		 try {
		 PreparedStatement preparedStatement1 = con.prepareStatement(updateQuery1);
	     // Set parameters for the update query
	     preparedStatement1.setDouble(1, amt);
	     preparedStatement1.setInt(2, to);
	     // Execute the update query
	     int num=preparedStatement1.executeUpdate();
	     if(num<=0) {
	    	 throw new IOException("Account Number Not Found");
	     }
		 PreparedStatement preparedStatement2 = con.prepareStatement(updateQuery2);
	     preparedStatement2.setDouble(1, amt);
	     preparedStatement2.setInt(2, from);
	     preparedStatement2.executeUpdate();

		 System.out.println("Money Transfer Successful");
		 float bal=check(from);
		 if(bal!=-1) {
			 System.out.println("Updated Balance : "+bal);
		 }
		 }
		 catch(Exception e) {
			 System.out.println("Error Can't Transfer Money!!! Try Again"+e);
		 }
		
		
	}
}
