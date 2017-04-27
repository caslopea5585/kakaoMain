package main;

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

import client.chat.Chat;

public class ClientThread extends Thread{
	Socket socket;
	Socket imgsocket;
	KakaoMain kakaoMain;
	
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public Chat chatDto;
	String msgValue,timeValue,myIdValue,yourIdValue;
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
							 myIdValue=(String)json.get("myId");
						 }else if(i==2){
							 JSONObject json = (JSONObject)value.get(i);
							 yourIdValue=(String)json.get("yourId");
						 }else if(i==3){
							 JSONObject json = (JSONObject)value.get(i);
							 timeValue= (String)json.get("timeValue");
						 }
					 }
					 
					 chatDto.setMyId(myIdValue);
					 chatDto.setYourId(yourIdValue);
					 chatDto.setMsg(msgValue);
					 chatDto.setTimeValue(timeValue);
					  
					
					  //kMain.chat.get(index).model.addRow(chatDto);
					 //모델에 행을 추가시켜야함
					 System.out.println("카카오메시지 사이즈는???" + kakaoMain.chat.size());
					 
					for(int i=0;i<kakaoMain.chat.size();i++){
						String myid=kakaoMain.chat.get(i).myId;
						String yourid= kakaoMain.chat.get(i).yourId;
						
						System.out.println("가져오는 값은 = "+myid + yourid);
						
						if((myIdValue.equals(myid)|| myIdValue.equals(yourid)) && (yourIdValue.equals(yourid)||yourIdValue.equals(myid))){
							kakaoMain.chat.get(i).model.addRow(chatDto);
							System.out.println("메인에 있는???? 뿌려져야할 chat의 주소는 ??"+ kakaoMain.chat.get(i));
						}
						
					}
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
	
	public void sendMsg(String msg,String myId,String yourId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append(	"\"type\":\"chat\",");
			sb.append("\"contents\":[{\"msg\":\""+msg+"\"},{\"myId\":\""+myId+"\"},{\"yourId\":\""+yourId+"\"}]");
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
