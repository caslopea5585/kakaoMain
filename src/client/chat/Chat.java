package client.chat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class Chat {
	private String myId;
	private String yourId;
	private String msg;
	private String timeValue;
	private Vector<String> chatMember;
	private int index;
	private int count;
	
	Map<String, Boolean> readCheck=new HashMap<String, Boolean>();//각 채팅을 보는 사람들의 목록 읽었으면 true 로 변환한다 false 안읽은사람
	
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

	public int getCount(){
		return count;
	}
	
	public void setCount(){
		int c=0;
		Iterator<String> iter=readCheck.keySet().iterator();
		
		while(iter.hasNext()){
			String key=iter.next();
			
			if(readCheck.containsKey(key)){
				if(!readCheck.get(key)){
					c++;
				}
			}
		}
		this.count=c;
	}


}
