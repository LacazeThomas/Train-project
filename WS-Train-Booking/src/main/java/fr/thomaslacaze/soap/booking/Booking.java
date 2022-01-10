package fr.thomaslacaze.soap.booking;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.thomaslacaze.soap.Soap;
import fr.thomaslacaze.soap.models.RoundTrip;
import fr.thomaslacaze.soap.models.Ticket;
import fr.thomaslacaze.soap.models.Train;

public class Booking extends Soap {
	public RoundTrip all(String departure_station, String arrival_station, String outbound_departure_date,
			String outbound_arrival_date, String return_departure_date, String return_arrival_date,
			String number_ticket_first_available, String number_ticket_standard_available,
			String number_ticket_business_available) {

		StringBuilder builder = new StringBuilder("?"); //Builder will contain all query params
		if (departure_station != null && !departure_station.isEmpty()) {
			builder.append("departure_station=" + departure_station + "&");
		}
		if (arrival_station != null && !arrival_station.isEmpty()) {
			builder.append("arrival_station=" + arrival_station + "&");
		}
		if (outbound_departure_date != null && !outbound_departure_date.isEmpty()) {
			builder.append("outbound_departure_date=" + outbound_departure_date + "&");
		}
		if (outbound_arrival_date != null && !outbound_arrival_date.isEmpty()) {
			builder.append("outbound_arrival_date=" + outbound_arrival_date + "&");
		}
		if (return_departure_date != null && !return_departure_date.isEmpty()) {
			builder.append("return_departure_date=" + return_departure_date + "&");
		}
		if (return_arrival_date != null && !return_arrival_date.isEmpty()) {
			builder.append("return_arrival_date=" + return_arrival_date + "&");
		}
		if (number_ticket_first_available != null && !number_ticket_first_available.isEmpty()) {
			builder.append("number_ticket_first_available=" + number_ticket_first_available + "&");
		}
		if (number_ticket_standard_available != null && !number_ticket_standard_available.isEmpty()) {
			builder.append("number_ticket_standard_available=" + number_ticket_standard_available + "&");
		}
		if (number_ticket_business_available != null && !number_ticket_business_available.isEmpty()) {
			builder.append("number_ticket_business_available=" + number_ticket_business_available + "&");
		}

		ClientResource resource = new ClientResource(baseURL + "/trains" + builder.toString());
		final Gson gson = new GsonBuilder().create();

		try {
			return gson.fromJson(resource.get().getText(), RoundTrip.class); //Automatic transform JSON return to RoudTrip class
		} catch (ResourceException | IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public Train trainById(String idTrain) {

		ClientResource resource = new ClientResource(baseURL + "/trains/" + idTrain);
		final Gson gson = new GsonBuilder().create();

		try {
			return gson.fromJson(resource.get().getText(), Train.class);
		} catch (ResourceException | IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Ticket> ticketsByTrain(String idTrain) {

		ClientResource resource = new ClientResource(baseURL + "/trains/" + idTrain + "/tickets");
		final Gson gson = new GsonBuilder().create();

		try {
			Ticket[] ticketArray = gson.fromJson(resource.get().getText(), Ticket[].class);
			List<Ticket> ticketList = Arrays.asList(ticketArray);
			return ticketList;
		} catch (ResourceException | IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public List<Ticket> ticketsByUser(String idUser) {

		ClientResource resource = new ClientResource(baseURL + "/users/" + idUser + "/tickets");
		final Gson gson = new GsonBuilder().create();

		try {
			Ticket[] ticketArray = gson.fromJson(resource.get().getText(), Ticket[].class);
			List<Ticket> ticketList = Arrays.asList(ticketArray);
			return ticketList;
		} catch (ResourceException | IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public String reserve(String idTrain, int type, boolean flexible, String userName, String password) {
		String url = baseURL + "/trains/" + idTrain + "/reserve";
		final Gson gson = new GsonBuilder().create();

		String idUser = getUser(userName, password);
		if (idUser.isEmpty()) {
			return "Wrong user or password";
		}

		ClientResource resource = new ClientResource(url);
		Ticket ticket = new Ticket(type, flexible, idTrain, idUser);

		try {
			String response = resource.post(gson.toJson(ticket)).getText();
			return response.equals("true") ? "Successful reservation"
					: response.equals("false") ? "Reservation error or the train " + idTrain + " is not available"
							: response.toString();
		} catch (ResourceException | IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	public String unReserve(String idTicket, String userName, String password) {
		String idUser = this.getUser(userName, password);
		if (idUser.isEmpty()) {
			return "Wrong user or password";
		}

		String url = baseURL + "/tickets/" + idTicket + "?userID=" + idUser;

		ClientResource resource = new ClientResource(url);

		try {
			return resource.delete().getText();
		} catch (ResourceException | IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}

}