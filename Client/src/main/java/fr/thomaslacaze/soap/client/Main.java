package fr.thomaslacaze.soap.client;

import java.rmi.RemoteException;

import fr.thomaslacaze.soap.account.*;
import fr.thomaslacaze.soap.booking.BookingStub.RoundTrip;
import fr.thomaslacaze.soap.booking.BookingStub.Ticket;
import fr.thomaslacaze.soap.booking.BookingClient;

public class Main {

	/**
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException {

		String userName = "thomas";
		String password = "test";
		AccountClient account = new AccountClient(userName, password);
		account.register();
		System.out.println("UserID=" + account.getUserID());

		BookingClient booking = new BookingClient(userName, password);

		System.out.println("Affichage de tous les allers-retours");
		RoundTrip roundTrip = booking.getFromFilter(null, null, null, null, null, null, null, null, null);
		booking.showRoundTrip(roundTrip);

		System.out.println("");

		System.out.println(
				"Affichage des allers-retours en départ de Paris direction Marseille départ le 2022-01-11 et retour le 2022-01-11");
		roundTrip = booking.getFromFilter("Marseille", "Paris", "", "2022-01-11", "", "2022-01-11", "", "", "");
		booking.showRoundTrip(roundTrip);

		System.out.println("");
		System.out.println("");

		if (roundTrip.getTrainsoutbound() != null && roundTrip.getTrainsreturn() != null) {
			System.out.println("Reservation du train aller " + roundTrip.getTrainsoutbound()[0].getName()
					+ " et du train retour " + roundTrip.getTrainsreturn()[0].getName());

			System.out.println("");

			System.out.println(roundTrip.getTrainsoutbound()[0].getName()+ " ticket en Première non flexible");
			System.out.println(booking.reserveTrain(roundTrip.getTrainsoutbound()[0].getId(), 2, false));

			System.out.println("");

			System.out.println(roundTrip.getTrainsreturn()[0].getName()+" ticket en Business flexible");
			System.out.println(booking.reserveTrain(roundTrip.getTrainsreturn()[0].getId(), 1, true));

			System.out.println("");
			System.out.println("");

			System.out.println("Affichages des tickets appartenants à l'user :");
			Ticket[] tickets = booking.getTicketsByUserID(account.getUserID());
			booking.showTickets(tickets);

			if (tickets != null) {
				System.out.println("On supprime une réservation avec un autre utilisateur: " + tickets[0].getId());
				BookingClient booking2 = new BookingClient("dfgjhfkjg", password);
				System.out.println(booking2.unReserveTrain(tickets[0].getId()));

				System.out.println("");

				System.out.println("On supprime la réservation du ticket: " + tickets[0].getId());
				System.out.println(booking.unReserveTrain(tickets[0].getId()));
				booking.showTickets(booking.getTicketsByUserID(account.getUserID()));

			}
		}

	}
}