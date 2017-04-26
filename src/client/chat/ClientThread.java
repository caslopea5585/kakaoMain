package client.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import main.KakaoMain;

public class ClientThread extends Thread{
	Socket socket;
	Socket imgsocket;
	KakaoMain kakaoMain;
	
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public Chat chatDto;
	String msgValue,timeValue,senderValue;
	int roomNumberValue;
	JSONArray value;
	JSONObject valueCheck;
	
	public ClientThread(Socket socket,KakaoMain kakaoMain) {
		this.socket=socket;
		this.kakaoMain=kakaoMain;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen(){
		String msg=null;

		JSONParser parser=new JSONParser();
		chatDto=new Chat();
		try {
			msg=buffr.readLine();
			
			JSONObject obj=(JSONObject)parser.parse(msg);
			System.out.println("클라이언트  파써"+msg);
			String type=(String)obj.get("type");
			
			if(type.equals("chat")){
				 value=(JSONArray)obj.get("contents");
				 System.out.println("서버 : 파서배열"+value.size());
				 
				 if(value.size()!=0){
					 for(int i=0;i<value.size();i++){
						 System.out.println("파서의 값은?" + value.get(i));
						 if(i==0){
							 JSONObject json = (JSONObject)value.get(i);
							 System.out.println("첫번째값!!!!"+json.get("msg"));
							 msgValue=(String)json.get("msg");
						 }else if(i==1){
							 JSONObject json = (JSONObject)value.get(i);
							 timeValue=(String)json.get("time");
						 }else if(i==2){
							 JSONObject json = (JSONObject)value.get(i);
							 senderValue=(String)json.get("sender");
						 }else if(i==3){
							 JSONObject json = (JSONObject)value.get(i);
							 roomNumberValue= Integer.parseInt((String)json.get("roomNumber"));
						 }
					 }

					 System.out.println("클라이언트에서 받는 메세지는???: "+msgValue+senderValue+timeValue+roomNumberValue);
					 chatDto.setMsg(msgValue);
					 chatDto.setSender(senderValue);
					 chatDto.setTime(timeValue);
					 chatDto.setRoomNumber(roomNumberValue);
					 
					 
					 
					 System.out.println("클라이언트 쓰레드에서 셋한 dto메세지 값은??:"+chatDto.getMsg());
					
					
					  //kMain.chat.get(index).model.addRow(chatDto);
					 //모델에 행을 추가시켜야함
					kakaoMain.chat.get(0).model.addRow(chatDto);
				 }
				 
			}
			
			if(type.equals("join")){//접속시 아이디 부여
				String str=(String)obj.get("content");
			}
			else if(type.equals("image")){
				String str=(String)obj.get("content");//tomcat file path
				System.out.println(str); 
		}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String msg,String time,String sender,int roomNumber){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append(	"\"type\":\"chat\",");
			sb.append("\"contents\":[{\"msg\":\""+msg+"\"},{\"time\":\""+time+"\"},{\"sender\":\""+sender+"\"},{\"roomNumber\":\""+roomNumber+"\"}]");
			sb.append("}");
			
			String myString = sb.toString();
			
			System.out.println("클라이언트에서 서버로 보내는말: "+myString);
			System.out.println("");
			
			buffw.write(myString+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendFile(File file){
		Thread thread=new Thread(){
			@Override
			public void run() {
				DataInputStream din;
				DataOutputStream dout;
				JSONObject obj=new JSONObject();
				obj.put("type", "image");
				try {
					byte[] size = Files.readAllBytes(file.toPath());
					obj.put("content", Integer.toString(size.length));
					System.out.println("size:"+Integer.toString(size.length));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				String myString = obj.toString();
				try {
					din=new DataInputStream(socket.getInputStream());
					dout=new DataOutputStream(socket.getOutputStream());
					buffw.write(myString+"\n");
					buffw.flush();
					
					FileInputStream fin=new FileInputStream(file);
					dout.writeUTF(file.getName());
					byte[] readData=new byte[2048];
					
					int i=0;
					while((i=fin.read(readData))!=-1){
						dout.write(readData, 0, i);
					}
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	public void sendID(String id){
		JSONObject obj=new JSONObject();
		obj.put("type", "loginID");
		obj.put("content",id);
		
		try {
			buffw.write(obj.toString()+"\n");
			buffw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		
		while(true){
			listen();	
			//kMain.chat.get(index).table.setModel(kMain.chat.get(index).model);
			//kMain.chat.get(index).table.updateUI();
		}
	}
}
