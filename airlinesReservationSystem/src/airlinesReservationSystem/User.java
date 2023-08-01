package airlinesReservationSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
public class User {

	String username="";
	String password="";
	Scanner sc=new Scanner(System.in);
	public User(){
		
	}
	public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
	public void menuOperations() {
		if(this.username=="admin") {
			System.out.println("Operations: ");
			System.out.println("1. Add new flights : ");
			System.out.println("2. Update flight time : ");
			System.out.println("3. Show ticket list by flight number and date : ");
			System.out.println("4. Exit");
			System.out.println("Enter a number : ");
			int n =sc.nextInt();
			switch(n) {
			case 1:
			{
				Flight f=new Flight();
				System.out.println("Enter flight details");
				f.enterFlightDetails();
				menuOperations();
				break;
			}
			case 2:{
				Flight f=new Flight();
				f.updateFlight();
				menuOperations();
				break;
			}
			case 3:{
				Ticket t=new Ticket();
				System.out.println("Enter the flight number you wish to search ");
				String fno=sc.next();
				System.out.println("Enter the departure dare ");
				String date=sc.next();
				Date d=java.sql.Date.valueOf(DateCaller.parseDate(date));
				t.ticketShow(fno, d);
			}
			case 4:
			{
					this.exit();
					break;
			}
			}
		}
		else {
			System.out.println("Operations");
			System.out.println("1. Search flights");
			System.out.println("2. Book Tickets");
			System.out.println("3. Exit");
			System.out.println("Enter a number : ");
			int n=sc.nextInt();
			switch(n) {
			case 1:
			{
				Flight f=new Flight();
				f.searchFlight();
				menuOperations();
				break;
			}
			case 2:{
				Flight f=new Flight();
				f.seatAvail();
				menuOperations();
				break;
			}
			case 3:
			{
					this.exit();
					break;
			}
			}
			
		}
	}
	public String createUser() {
		try {
			String query="insert into users (firstname, lastname, email, password) values (?, ?, ?, ?)";
			System.out.println("Enter user first name : ");
			String firstname=sc.next();
			System.out.println("Enter user last name : ");
			String lastname=sc.next();
			System.out.println("Enter user email : ");
			String email=sc.next();
			System.out.println("Set user password : ");
			String pass=sc.next();
			Connection con = DBConnection.getConnection();			
			PreparedStatement st=con.prepareStatement(query);
			st.setString(1,firstname);
			st.setString(2, lastname);
			st.setString(3, email);
			st.setString(4,pass);
			Class.forName("com.mysql.cj.jdbc.Driver");
			st.executeUpdate();
			System.out.println("User created successfully");
			st.close();
			con.close();
			return "dus";
		}
		catch(Exception e) {
			return "No connection possible";
		}
	}
	public String login() {
		System.out.println("Enter as : ");
		System.out.println("1. User : ");
		System.out.println("2. Admin : ");
		System.out.println("Enter a number : ");
		int n=sc.nextInt();
		if (n==1) {
			System.out.println("Enter your email : ");
			String name=sc.next();
			System.out.println("Enter your password : ");
			String pass=sc.next();
			if(this.passValid(name, pass)) {
				this.username=name;
				this.password=pass;
				try {
					String query="select firstname, lastname from users where email=?";
					Connection con = DBConnection.getConnection();					PreparedStatement st=con.prepareStatement(query);
					st.setString(1,name);
					ResultSet rs=st.executeQuery();
					rs.next();
					System.out.println("Welcome home "+ rs.getString(1)+" "+rs.getString(2));
			    	menuOperations();

				}
				catch(Exception e) {
					System.out.println("Nope");
				}
				
			}
		}
		if(n == 2) {
			System.out.println("Enter password : ");
		    String password = sc.next();
		    if(password.equals("admin123")) {
		    	this.username="admin";
		    	this.password="admin123";
		    	System.out.println("Welcome home admin");
		    	menuOperations();
		    	
		    }
		    else {
		    	System.out.println("Wrong password");
		    	this.exit();
		    }
		}
		return " ";
		
	}
	public boolean passValid(String name, String pass) {
		try {
			String query="select email, password from users where email=?";
			Connection con = DBConnection.getConnection();			PreparedStatement st=con.prepareStatement(query);
			st.setString(1,name);
			ResultSet rs=st.executeQuery();
			rs.next();
			if(rs.getString(2).equals(pass)){
				return true;
			}
			System.out.println("Wrong password");	
			return false;
			
		}
		catch(Exception e) {
			System.out.println("No user exists");
			return false;
		}
	}
	public void exit() {
		System.out.println("Thank you for using Meliora Airlines! ");
		System.exit(0);
	}
}
