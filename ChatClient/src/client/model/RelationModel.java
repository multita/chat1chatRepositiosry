package client.model;

import java.io.Serializable;

public class RelationModel implements Serializable{
	private static long serialVersionUID = -6957361951748232419L;
	
	private String command;
	private String username;
	private String object;

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
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public RelationModel(String command, String username, String object) {

		this.command = command;
		this.username = username;
		this.object = object;
	}
	@Override
	public String toString() {
		return "RelationModel [command=" + command + ", username=" + username + ", object=" + object + "]";
	}

}
