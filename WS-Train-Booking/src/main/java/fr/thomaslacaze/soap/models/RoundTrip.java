package fr.thomaslacaze.soap.models;

public class RoundTrip {

	public Train[] trainsoutbound;
	public Train[] trainsreturn;

	public Train[] getTrainsoutbound() {
		return trainsoutbound;
	}

	public void setTrainsoutbound(Train[] trains_outbound) {
		this.trainsoutbound = trains_outbound;
	}

	public Train[] getTrainsreturn() {
		return trainsreturn;
	}

	public void setTrainsreturn(Train[] trains_return) {
		this.trainsreturn = trains_return;
	}
}
