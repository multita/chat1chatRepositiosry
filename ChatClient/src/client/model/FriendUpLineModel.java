package client.model;

import java.io.Serializable;

public class FriendUpLineModel implements Serializable{
	String command;
	String username;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "FriendOnline [command=" + command + ", username=" + username + "]";
	}
	public FriendUpLineModel(String command, String username) {
		super();
		this.command = command;
		this.username = username;
	}

	
}
