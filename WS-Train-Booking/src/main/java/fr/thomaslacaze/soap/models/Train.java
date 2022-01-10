package fr.thomaslacaze.soap.models;

public class Train {
	public String Id;
	public String name;
	public String departure_station;
	public String arrival_station;
	public String departure_date;
	public String arrival_date;
	public int number_seat_first;
	public int number_seat_business;
	public int number_seat_standard;
	public int number_ticket_first_available;
	public int number_ticket_business_available;
	public int number_ticket_standard_available;

	public int getNumber_seat_first() {
		return number_seat_first;
	}

	public void setNumber_seat_first(int number_seat_first) {
		this.number_seat_first = number_seat_first;
	}

	public int getNumber_seat_business() {
		return number_seat_business;
	}

	public void setNumber_seat_business(int number_seat_business) {
		this.number_seat_business = number_seat_business;
	}

	public int getNumber_seat_standard() {
		return number_seat_standard;
	}

	public void setNumber_seat_standard(int number_seat_standard) {
		this.number_seat_standard = number_seat_standard;
	}

	public int getNumber_ticket_first_available() {
		return number_ticket_first_available;
	}

	public void setNumber_ticket_first_available(int number_ticket_first_available) {
		this.number_ticket_first_available = number_ticket_first_available;
	}

	public int getNumber_ticket_business_available() {
		return number_ticket_business_available;
	}

	public void setNumber_ticket_business_available(int number_ticket_business_available) {
		this.number_ticket_business_available = number_ticket_business_available;
	}

	public int getNumber_ticket_standard_available() {
		return number_ticket_standard_available;
	}

	public void setNumber_ticket_standard_available(int number_ticket_standard_available) {
		this.number_ticket_standard_available = number_ticket_standard_available;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeparture_station() {
		return departure_station;
	}

	public void setDeparture_station(String departure_station) {
		this.departure_station = departure_station;
	}

	public String getArrival_station() {
		return arrival_station;
	}

	public void setArrival_station(String arrival_station) {
		this.arrival_station = arrival_station;
	}

	public String getDeparture_date() {
		return departure_date;
	}

	public void setDeparture_date(String departure_date) {
		this.departure_date = departure_date;
	}

	public String getArrival_date() {
		return arrival_date;
	}

	public void setArrival_date(String arrival_date) {
		this.arrival_date = arrival_date;
	}

}
