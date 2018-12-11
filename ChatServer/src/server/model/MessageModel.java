package server.model;

import java.io.Serializable;

public class MessageModel  implements Serializable{
	private String command;
	private String message;
	private String sender ;
	private String receiver;
	private String nowTime ;
	
	@Override
	public String toString() {
		return "MessageModel [command=" + command + ", message=" + message + ", sender=" + sender + ", receiver="
				+ receiver + ", nowTime=" + nowTime + "]";
	}
	public MessageModel(String command,String message, String sender, String receiver,String nowTime) {
		this.command= command;
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
		this.nowTime= nowTime;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
}
