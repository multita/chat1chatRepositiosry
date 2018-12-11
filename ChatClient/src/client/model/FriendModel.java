package client.model;

import java.io.Serializable;

public class FriendModel implements Serializable{
	String name ;
	//String remark ;
	Boolean isOnLine;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getIsOnLine() {
		return isOnLine;
	}
	public void setIsOnLine(Boolean isOnLine) {
		this.isOnLine = isOnLine;
	}
	public FriendModel(String name, Boolean isOnLine) {
		this.name = name;
		this.isOnLine = isOnLine;
	}
	@Override
	public String toString() {
		return "FriendModel [name=" + name + ", isOnLine=" + isOnLine + "]";
	}

	
	
}
