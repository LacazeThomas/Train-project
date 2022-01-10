package fr.thomaslacaze.soap.account;

import java.rmi.RemoteException;

import fr.thomaslacaze.soap.account.AccountStub.*;

public class AccountClient {

	String userName;
	String password;

	public AccountClient(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public void register() throws RemoteException {
		AccountStub acc = new AccountStub();
		Register r = new Register();
		r.setUserName(this.userName);
		r.setPassword(this.password);
		System.out.println(acc.register(r).get_return());
	}

	public String getUserID() throws RemoteException {
		AccountStub acc = new AccountStub();
		GetID i = new GetID();
		i.setUserName(this.userName);
		i.setPassword(this.password);
		return acc.getID(i).get_return();
	}

}