package client.chat;

import java.util.Vector;

public class Chat {
	private String myId;
	private String yourId;
	private String msg;
	private String timeValue;
	private Vector<String> chatMember;
	private int index;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Vector<String> getChatMember() {
		return chatMember;
	}
	public void setChatMember(Vector<String> chatMember) {
		this.chatMember = chatMember;
	}
	public String getMyId() {
		return myId;
	}
	public void setMyId(String myId) {
		this.myId = myId;
	}
	public String getYourId() {
		return yourId;
	}
	public void setYourId(String yourId) {
		this.yourId = yourId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTimeValue() {
		return timeValue;
	}
	public void setTimeValue(String timeValue) {
		this.timeValue = timeValue;
	}
	

}
