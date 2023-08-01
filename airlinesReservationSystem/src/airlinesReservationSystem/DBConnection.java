package airlinesReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String url="jdbc:mysql://localhost:3306/airlines?createDatabaseIfNotExist=true";
	private static final String dbusername="root";
	private static final String dbpassword="root";
	public static Connection getConnection() throws SQLException{
		return  DriverManager.getConnection(url,dbusername,dbpassword);
	}
}
