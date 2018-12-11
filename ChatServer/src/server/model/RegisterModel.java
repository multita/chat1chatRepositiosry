package server.model;

import java.io.Serializable;

public class RegisterModel implements Serializable{
	
	String command ;
	String username;
	String password;
	
	public RegisterModel( String command , String username, String password) {
		
		this.command = command;
		this.username = username;
		this.password = password;
	}

	
	@Override
	public String toString() {
		return "RegisterModel [command=" + command + ", username=" + username + ", password=" + password + "]";
	}


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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
