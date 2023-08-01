package airlinesReservationSystem;

public class DateCaller {
	public static java.time.LocalTime parseTime(String timeStr) {
        return java.time.LocalTime.parse(timeStr, java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static java.time.LocalDate parseDate(String dateStr) {
        return java.time.LocalDate.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
