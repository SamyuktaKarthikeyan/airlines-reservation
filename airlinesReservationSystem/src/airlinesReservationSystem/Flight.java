package airlinesReservationSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Flight {
    Scanner sc = new Scanner(System.in);
    public Flight() {

    }
    public void enterFlightDetails() {
        try {
            String query = "INSERT INTO flight (flightno, arrival, departure, arrivaldate, arrivaltime, departuredate, departuretime, seats) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            Connection con = DBConnection.getConnection();

            System.out.println("Enter flight number: ");
            String flightNo = sc.next();

            System.out.println("Enter arrival location: ");
            String arrival = sc.next();

            System.out.println("Enter departure location: ");
            String departure = sc.next();

            System.out.println("Enter arrival date (dd/MM/yyyy): ");
            String arrivalDateStr = sc.next();

            System.out.println("Enter arrival time (HH:mm): ");
            String arrivalTimeStr = sc.next();

            System.out.println("Enter departure date (dd/MM/yyyy): ");
            String departureDateStr = sc.next();

            System.out.println("Enter departure time (HH:mm): ");
            String departureTimeStr = sc.next();

            System.out.println("Enter number of available seats: ");
            int seats = sc.nextInt();

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, flightNo);
            preparedStatement.setString(2, arrival);
            preparedStatement.setString(3, departure);
            preparedStatement.setDate(4, java.sql.Date.valueOf(DateCaller.parseDate(arrivalDateStr)));
            preparedStatement.setTime(5, java.sql.Time.valueOf(DateCaller.parseTime(arrivalTimeStr)));
            preparedStatement.setDate(6, java.sql.Date.valueOf(DateCaller.parseDate(departureDateStr)));
            preparedStatement.setTime(7, java.sql.Time.valueOf(DateCaller.parseTime(departureTimeStr)));
            preparedStatement.setInt(8, seats);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Flight details have been stored in the database.");

        } catch (SQLException e) {
            System.out.println("An error occurred while connecting to the database or executing the SQL statement.");
            e.printStackTrace();
        }
    }

    public void searchFlight() {
        System.out.println("Enter the departure location: ");
        String dep = sc.next();

        System.out.println("Enter the arrival location: ");
        String arr = sc.next();

        System.out.println("Enter departure date (dd/MM/yyyy): ");
        String date = sc.next();

        try {
            String query = "SELECT * FROM flight WHERE arrival=? AND departure=? AND departuredate=?";
            Connection con = DBConnection.getConnection();
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, arr);
            st.setString(2, dep);
            st.setDate(3, java.sql.Date.valueOf(DateCaller.parseDate(date)));
            ResultSet rs = st.executeQuery();

            System.out.printf("%-10s %-10s %-10s %-14s %-14s %-14s %-14s %-10s\n", "Flightno", "Arrival", "Departure",
                    "Arrivaldate", "Arrivaltime", "Departuredate", "Departuretime", "Seats");

            while (rs.next()) {
                System.out.printf("%-10s %-10s %-10s %-14s %-14s %-14s %-14s %-10d\n", rs.getString("flightno"),
                        rs.getString("arrival"), rs.getString("departure"), rs.getDate("arrivaldate"),
                        rs.getTime("arrivaltime"), rs.getDate("departuredate"), rs.getTime("departuretime"),
                        rs.getInt("seats"));
            }

            st.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("An error occurred while connecting to the database or executing the SQL statement.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("No flights found.");
        }
    }

    public void seatAvail() {
        System.out.println("Enter the flight number:");
        String fno = sc.next();
        System.out.println("Enter departure date (dd/MM/yyyy): ");
        String date = sc.next();

        System.out.println("Enter the number of passengers you wish to book for:");
        int p = sc.nextInt();

        try {
            String q1 = "SELECT seats, arrivaldate, departuredate FROM flight WHERE flightno=? and departuredate=?";
            Connection con = DBConnection.getConnection();
            PreparedStatement st = con.prepareStatement(q1);
            st.setString(1, fno);
            st.setDate(2, java.sql.Date.valueOf(DateCaller.parseDate(date)));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int num = rs.getInt("seats");
                java.util.Date arrivalDate = rs.getDate("arrivaldate");
                java.util.Date departureDate = rs.getDate("departuredate");
                if (num - p < 0) {
                    System.out.println("Seats unavailable");
                } else {
                    System.out.println("Seats available");
                    while (p > 0) {
                        p--;
                        Ticket t = new Ticket();
                        t.bookTicket(fno, arrivalDate, departureDate);
                    }
                }
            } else {
                System.out.println("Flight not found");
            }

            st.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error while checking seat availability.");
            e.printStackTrace();
        }
    }
    public void updateFlight() {

        System.out.println("Enter flight number: ");
        String flightNo = sc.next();
        
        System.out.println("Enter arrival date (dd/MM/yyyy): ");
        String arrivalDateStr = sc.next();

        System.out.println("Enter arrival time (HH:mm): ");
        String arrivalTimeStr = sc.next();

        System.out.println("Enter departure date (dd/MM/yyyy): ");
        String departureDateStr = sc.next();

        System.out.println("Enter departure time (HH:mm): ");
        String departureTimeStr = sc.next();

        System.out.println("Enter number of available seats: ");
        int seats = sc.nextInt();

    	try {
            String query = "UPDATE flight SET arrivaldate=?, arrivaltime=?, departuredate=?, departuretime=?, seats=? WHERE flightno=?";
            Connection con = DBConnection.getConnection();
            PreparedStatement preparedStatement=con.prepareStatement(query);
            preparedStatement.setDate(1, java.sql.Date.valueOf(DateCaller.parseDate(arrivalDateStr)));
            preparedStatement.setTime(2, java.sql.Time.valueOf(DateCaller.parseTime(arrivalTimeStr)));
            preparedStatement.setDate(3, java.sql.Date.valueOf(DateCaller.parseDate(departureDateStr)));
            preparedStatement.setTime(4, java.sql.Time.valueOf(DateCaller.parseTime(departureTimeStr)));
            preparedStatement.setInt(5, seats);
            preparedStatement.setString(6, flightNo);
			preparedStatement.executeUpdate();
			System.out.println("Flight details successfully");
			preparedStatement.close();
			con.close();
    		
    	}catch(Exception e) {
    		
    	}
    }
}
