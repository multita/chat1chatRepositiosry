package client.model;

import java.io.Serializable;
import java.util.Arrays;

public class UserModel implements Serializable{
	//写一个实体类要有get(),set(),tostring();
	private static long serialVersionUID = -6957361951748222519L;
	String command ;	
	String username;
	String password;
	FriendModel[] friends;
	
	public Boolean isFriend(String object) {
		for(FriendModel friendModel : this.getFriends()) {
			if(friendModel.getName().equals(object)) return true;
		}
		return false;
	}
	public UserModel(String command,String username, String password,FriendModel[] friends) {
		
		this.command = command;
		this.username = username;
		this.password = password;
		this.friends = friends;
	}
	
	@Override
	public String toString() {
		return "UserModel [command=" + command + ", username=" + username + ", password=" + password + ", friends="
				+ Arrays.toString(friends) + "]";
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public FriendModel[] getFriends() {
		return friends;
	}

	public void setFriends(FriendModel[] friends) {
		this.friends = friends;
	}
	
}
