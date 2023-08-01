package airlinesReservationSystem;

import java.util.Date;

abstract class TicketShower {
	abstract void ticketShow();
	abstract void ticketShow(String flightno, Date departuredate);
}
