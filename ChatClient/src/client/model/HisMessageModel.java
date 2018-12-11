package client.model;

import java.util.Arrays;

public class HisMessageModel {
	private String command;
	private MessageModel[] messageModel;
	
	public HisMessageModel(String command, MessageModel[] messageModel) {
		
		this.command = command;
		this.messageModel = messageModel;
	}
	@Override
	public String toString() {
		return "HisMessageModel [command=" + command + ", messageModel=" + Arrays.toString(messageModel) + "]";
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public MessageModel[] getMessageModel() {
		return messageModel;
	}
	public void setMessageModel(MessageModel[] messageModel) {
		this.messageModel = messageModel;
	}
	
	
}
