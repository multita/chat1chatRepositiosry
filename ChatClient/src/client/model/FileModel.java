package client.model;

import java.io.Serializable;

public class FileModel  implements Serializable{
	String command ;

	String fileName ;
	
	String content ;

	String sender ;
	String receiver ;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "FileModel [command=" + command + ", fileName=" + fileName + ", content=" + content + ", sender="
				+ sender + ", receiver=" + receiver + "]";
	}
	public FileModel(String command, String fileName, String content, String sender, String receiver) {
	
		this.command = command;
		this.fileName = fileName;
		this.content = content;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	
}
