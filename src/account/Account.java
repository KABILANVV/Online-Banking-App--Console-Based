package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import connect.Connect;

abstract class User{
	public abstract boolean addAccount(String user,String password);
	public abstract void showDetails();
	public abstract boolean check(int ac,String password);
//	public abstract String getUsername(int accountnumber);
	public abstract void depositMoney(float amount);
}
public class Account extends User{
	
	private String username;
	private int accno;
	private Connection con=null;
	public Account(){
		con=Connect.getConnection();
	}
	public Account(String user){
		setUsername(user);
		con=Connect.getConnection();
	}
	
	public boolean addAccount(String user, String pass) {
		try {
			Random random = new Random();
			int randomNumber = random.nextInt(90000) + 10000; // Generate a random number between 10000 and 99999
			PreparedStatement statement = con.prepareStatement("INSERT INTO user (name,password,account_number) VALUES (?,?, ?)");
            statement.setString(1, user);
            statement.setString(2, pass);
            statement.setInt(3, randomNumber);
            statement.executeUpdate();
            setUsername(user);
            setAccno(randomNumber);
            System.out.println("Your Account Number is "+randomNumber);
			return true;
		}
		catch(Exception e) {
			System.out.println(e+"Error Occured!! Failed to create Account");
		}
		return false;
	}
	public void showDetails() {
		try {
				int accid=getAccno();
				PreparedStatement statement = con.prepareStatement("SELECT * FROM user where account_number=?");
	            statement.setInt(1, accid);
	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                String name = resultSet.getString("name");
	                int accnum = resultSet.getInt("account_number");

//	                int balance = resultSet.getInt("balance");
	                float balance = resultSet.getFloat("balance");
	                System.out.println("Username : "+name);
	                System.out.println("Account-number : "+accnum);
	                System.out.println("Balance : "+balance);
				} 
	            else {
				    System.out.println("No user found with the specified account number.");
				}
		}
		catch(Exception e) {
			System.out.println(e+""+"Error Occured!! Failed to retrieve Account Details");
		}
		
	}
		
	protected void finalize() throws SQLException  
	{  
		con.close();
	}
	public boolean check(int accountnumber, String pass) {
		try {
		PreparedStatement statement = con.prepareStatement("SELECT password,name FROM user where account_number=?");
        statement.setInt(1, accountnumber);
        
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String password = resultSet.getString(1); 
        if( password.equals(pass) ) {
        	setUsername(resultSet.getString(2));
        	setAccno(accountnumber);
        	return true;
        }
		}
		}
		catch(Exception e) {
			System.out.println(e+"Error Occured !! Please Try Again !!!");
			return false;
		}
		return false;
	}
//	public String getUsername(int accountnumber) {
//
//		try {
//			PreparedStatement statement = con.prepareStatement("SELECT name FROM user where account_number=?");
//	        statement.setInt(1, accountnumber);
//	        ResultSet resultSet = statement.executeQuery();
//	        resultSet.next();
//	        String username=resultSet.getString(0);
//			return username;
//		}
//		catch (Exception e) {
//		return "Default User";
//		}
//	}
	public void depositMoney( float amount) {
		 int accountNumber=getAccno();
		 String updateQuery = "UPDATE user SET balance = balance + ? WHERE account_number = ?";
		 String query = "select balance from user WHERE account_number = ?";
		 try {
		 PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
	     // Set parameters for the update query
	     preparedStatement.setDouble(1, amount);
	     preparedStatement.setInt(2, accountNumber);
	     // Execute the update query
	     int rowsAffected = preparedStatement.executeUpdate();

		 PreparedStatement stmt = con.prepareStatement(query);
	     stmt.setInt(1, accountNumber);
	     ResultSet rs=stmt.executeQuery();
	     if(rs.next()) {
	     float bal=rs.getFloat(1);
	     if (rowsAffected > 0) {
             System.out.println("Balance updated successfully.");
             System.out.println("Updated Balance : "+bal);
         } else {
             System.out.println("Failed to update balance.");
         }
		 }
		 }
		 catch(Exception e) {
			 System.out.println("Balance not Updated!!! Try Again");
		 }
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	
}
