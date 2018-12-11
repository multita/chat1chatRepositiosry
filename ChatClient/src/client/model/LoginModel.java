package client.model;

import java.io.Serializable;


public class LoginModel implements Serializable{
	//写一个实体类要有get(),set(),tostring();
	private static final long serialVersionUID = -6957361951748382519L;
	private String command= "login";
	private String username;
	private String password;
	public LoginModel(String username, String password) {
		this.username = username;
		this.password = password;
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
	@Override
	public String toString() {
		return "LoginModel [command=" + command + ", username=" + username + ", password=" + password + "]";
	}
	
	
	
}
