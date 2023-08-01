package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	public static Connection getConnection(){
		Connection connection = null;
	    try {
	        // Provide database credentials
	        String url = "jdbc:mysql://localhost:3306/bank";
	        String username = "root";
	        String password = "kingkabi";

	        // Register the JDBC driver
	        Class.forName("com.mysql.jdbc.Driver");

	        // Create the connection
	        connection = DriverManager.getConnection(url, username, password);

	        // Check if the connection is successful
	        if (connection != null) {
//	            System.out.println("Processing Please Wait!!!");
	            // You can perform database operations here
	        }
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return connection;
		}
}
