package server.model;

import java.io.Serializable;

public class FriendDownLineModel implements Serializable{
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
		return "FriendDownLineModel [command=" + command + ", username=" + username + "]";
	}
	public FriendDownLineModel(String command, String username) {
		
		this.command = command;
		this.username = username;
	}

	
}

