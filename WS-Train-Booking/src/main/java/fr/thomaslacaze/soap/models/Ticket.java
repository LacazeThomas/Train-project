package fr.thomaslacaze.soap.models;

public class Ticket {

	private String Id;
	private boolean Flexible;
	private int Type;
	private String TrainID;
	private String UserID;

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getTrainID() {
		return TrainID;
	}

	public void setTrainID(String trainID) {
		TrainID = trainID;
	}

	public Ticket(int type, boolean flexible, String trainID, String userID) {
		this.Flexible = flexible;
		this.Type = type;
		this.TrainID = trainID;
		this.UserID = userID;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public boolean isFlexible() {
		return Flexible;
	}

	public void setFlexible(boolean flexible) {
		this.Flexible = flexible;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		this.Type = type;
	}

}
