package airlinesReservationSystem;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date; 
import airlinesReservationSystem.TicketShower;

public class Ticket extends TicketShower{
	Scanner sc=new Scanner(System.in);
	private String flightno;
	private String name;
	private String gender;
	private int age;
	private Date arrivaldate;
	private Date departuredate;
	public void bookTicket(String flightno, Date arrivaldate, Date departuredate) {
	    try {
	        String query = "INSERT INTO ticket (flightno, name, gender, age, arrivaldate, departuredate) VALUES (?, ?, ?, ?, ?, ?)";
	        Connection con = DBConnection.getConnection();
	        PreparedStatement st = con.prepareStatement(query);
	   

	        st.setString(1, flightno);

	        System.out.println("Enter your name: ");
	        String name = sc.next();
	        st.setString(2, name);

	        System.out.println("Enter your gender (M/F): ");
	        String gender = sc.next();
	        st.setString(3, gender);

	        System.out.println("Enter your age: ");
	        int age = sc.nextInt();
	        st.setInt(4, age);

	        st.setDate(5, new java.sql.Date(arrivaldate.getTime()));
	        st.setDate(6, new java.sql.Date(departuredate.getTime())); 

	        st.executeUpdate();
	        this.age=age;
	        this.name=name;
	        this.arrivaldate=arrivaldate;
	        this.departuredate=departuredate;
	        this.gender=gender;
	        this.flightno=flightno;
	        ticketShow();
	        
	        System.out.println("Ticket booked successfully!");
	        String updateQuery = "UPDATE flight SET seats = seats - 1 WHERE flightno = ? and departuredate=?";
	        PreparedStatement updateSt = con.prepareStatement(updateQuery);
	        updateSt.setString(1, flightno);
	        updateSt.setDate(2, new java.sql.Date(departuredate.getTime())); 
	        updateSt.executeUpdate();

	        updateSt.close();

	        st.close();
	        con.close();
	    } catch (SQLException e) {
	        System.out.println("An error occurred while connecting to the database or executing the SQL statement.");
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.out.println("Error while booking the ticket.");
	        e.printStackTrace();
	    }
	}

	@Override
	 void ticketShow() {
		System.out.println("============================================\n");
        System.out.println("Ticket details:");
        System.out.println("Flight Number: " + flightno);
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Age: " + age);
        System.out.println("Arrival Date: " + arrivaldate);
        System.out.println("Departure Date: " + departuredate);
		System.out.println("============================================\n");

    }

	@Override
	void ticketShow(String flightno, Date departuredate) {
	    try {
	        String query = "SELECT name, gender, age FROM ticket WHERE flightno=? AND departuredate=?";
	        Connection con = DBConnection.getConnection();
	        PreparedStatement st = con.prepareStatement(query);
	        st.setString(1, flightno);
	        st.setDate(2, new java.sql.Date(departuredate.getTime()));
	        ResultSet rs = st.executeQuery();

	        System.out.println("============================================");
	        System.out.println("Ticket details for Flight Number: " + flightno + " on Departure Date: " + departuredate);
	        System.out.printf("%-15s %-15s %-15s\n", "Name", "Gender", "Age");

	        while (rs.next()) {
	            System.out.printf("%-15s %-15s %-15d\n", rs.getString("name"), rs.getString("gender"), rs.getInt("age"));
	        }

	        System.out.println("============================================");
	        st.close();
	        con.close();
	    } catch (Exception e) {
	        System.out.println("Error while fetching ticket details.");
	        e.printStackTrace();
	    }
	}


	
}
