package fr.thomaslacaze.soap.booking;

import java.rmi.RemoteException;
import fr.thomaslacaze.soap.booking.BookingStub.*;

public class BookingClient {

	private String userName;
	private String password;

	public BookingClient(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public RoundTrip getFromFilter(String arrival_station, String departure_station, String outbound_arrival_date,
			String outbound_departure_date, String return_arrival_date, String return_departure_date,
			String number_ticket_first_available, String number_ticket_standard_available,
			String number_ticket_business_available) throws RemoteException {
		BookingStub book = new BookingStub();
		All s = new All();
		s.setOutbound_arrival_date(outbound_arrival_date);
		s.setOutbound_departure_date(outbound_departure_date);
		s.setReturn_arrival_date(return_arrival_date);
		s.setReturn_departure_date(return_departure_date);
		s.setDeparture_station(departure_station);
		s.setArrival_station(arrival_station);
		s.setNumber_ticket_business_available(number_ticket_business_available);
		s.setNumber_ticket_first_available(number_ticket_first_available);
		s.setNumber_ticket_standard_available(number_ticket_standard_available);

		return book.all(s).get_return();
	}

	public String reserveTrain(String id, int type, boolean flexible) throws RemoteException {
		BookingStub book = new BookingStub();
		Reserve b = new Reserve();
		b.setIdTrain(id);
		b.setType(type);
		b.setFlexible(flexible);
		b.setUserName(this.userName);
		b.setPassword(this.password);

		return book.reserve(b).get_return();
	}

	public String unReserveTrain(String id) throws RemoteException {
		BookingStub book = new BookingStub();
		UnReserve u = new UnReserve();
		u.setUserName(this.userName);
		u.setPassword(this.password);
		u.setIdTicket(id);

		return book.unReserve(u).get_return();
	}

	public Ticket[] getTicketByUserID(String id) throws RemoteException {
		BookingStub book = new BookingStub();
		TicketsByUser t = new TicketsByUser();
		t.setIdUser(id);

		return book.ticketsByUser(t).get_return();
	}

	public Ticket[] getTicketsByTrainID(String id) throws RemoteException {
		BookingStub book = new BookingStub();
		TicketsByTrain t = new TicketsByTrain();

		t.setIdTrain(id);

		return book.ticketsByTrain(t).get_return();
	}

	public Ticket[] getTicketsByUserID(String id) throws RemoteException {
		BookingStub book = new BookingStub();
		TicketsByUser t = new TicketsByUser();

		t.setIdUser(id);

		return book.ticketsByUser(t).get_return();
	}

	public void showTrains(Train[] trains) {
		if (trains == null || trains.length == 0) {
			System.out.println("No train for this filters");
		} else {
			for (Train t : trains) {
				System.out.println("Train [Id=" + t.getId() + ", name=" + t.getName() + ", departure_station="
						+ t.getDeparture_station() + ", arrival_station=" + t.getArrival_station() + ", departure_date="
						+ t.getDeparture_date() + ", arrival_date=" + t.getArrival_date() + t.getNumber_seat_first()
						+ ", number_ticket_business_available=" + t.getNumber_seat_business()
						+ ", number_ticket_standard_available=" + t.localNumber_seat_standard + "]");
			}
		}

	}

	public void showTickets(Ticket[] tickets) {
		if (tickets == null || tickets.length == 0) {
			System.out.println("No tickets reserved");
		} else {
			for (Ticket t : tickets) {
				System.out.println("Ticket [Id=" + t.getId() + ", Flexible=" + t.getFlexible() + ", Type=" + t.getType()
						+ ",TrainID=" + t.getTrainID() + "]");
			}
		}

	}

	public void showRoundTrip(RoundTrip r) {
		if (r != null) {
			System.out.println("Outbound :");
			showTrains(r.getTrainsoutbound());
			System.out.println("Return :");
			showTrains(r.getTrainsreturn());
		} else {
			System.out.println("RoundTrip is empty");
		}

	}
}