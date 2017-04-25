package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import client.chat.Chat;

//DB가 가져야할 정보 num/users/내용
public class Server_chat extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();
	
	JSONArray value;
	String msgValue,timeValue,senderValue;
	JSONObject valueCheck;
	
	
	
	public Server_chat(Socket socket,Vector<ThreadManager> userThread) {
		this.socket=socket;
		this.userThread=userThread;
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen(){
		//클라에서 받는 것ㄴ
		
		String msg=null;
		DataInputStream dis =null;
		File file=null;
		FileOutputStream fos=null;
		BufferedOutputStream bos=null;
		try {
			msg=buffr.readLine();
			System.out.println("서버 파서대상"+  msg);
			//여기서 판단하기
			System.out.println(msg+"ServerChat에서 파서할 내용.....");
			JSONParser parser=new JSONParser();
			JSONObject obj=(JSONObject)parser.parse(msg);
			
			
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
						 }
					 }
					 				 
					 sendMsg(msgValue,timeValue,senderValue);
					 System.out.println(msgValue+senderValue+timeValue);
				 }
			
			}
			else if(type.equals("image")){
						String size_s=(String)obj.get("content");//file size
						int size=Integer.parseInt(size_s);
						
						try {
							dis = new DataInputStream (socket.getInputStream());
							String str=dis.readUTF();
							String path="C:/myserver/data/"+str;
							file=new File(path);
							fos=new FileOutputStream(file);
							bos=new BufferedOutputStream(fos);
							
							int totalBytesRead = 0;
							byte[] data = new byte[size];
							
							while (totalBytesRead < size) {
								int bytesRemaining = size - totalBytesRead;
								int bytesRead = dis.read(data, totalBytesRead, bytesRemaining);
								if (bytesRead == -1) {
									return; // socket has been closed
								}
								totalBytesRead += bytesRead;
							}
							bos.write(data,0,totalBytesRead);
							bos.flush();
							send(new File(path));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
		    try {
		    	if(fos!=null){
		    		fos.close();
		    	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMsg(String msg,String time,String sender){
		//서버가 보내주는 것
			System.out.println("보내지니????");
			//판단해서 보내주기
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append(	"\"type\":\"chat\",");
			sb.append("\"contents\":[{\"msg\":\""+msg+"\"},{\"time\":\""+time+"\"},{\"sender\":\""+sender+"\"}]");
			sb.append("}");
			String myString = sb.toString();
				

			System.out.println("유저쓰레드 사이즈 = "+userThread.size());
		for(int i=0;i<userThread.size();i++){
			
				try {
					userThread.elementAt(i).sever_chat.buffw.write(myString+"\n");
					userThread.elementAt(i).sever_chat.buffw.flush();
					System.out.println("서버에서 참여자들에게 보내는 메세지는???"+myString);
					System.out.println("유저 쓰레드???"+userThread.get(i));


				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
			
	}

	public void send(File file){
		JSONObject obj=new JSONObject();
		obj.put("type", "image");
		obj.put("content", "http://"+socket.getInetAddress().getHostAddress()+":9090/data/"+file.getName());
		
//		String str="http://"+socket.getInetAddress().getHostAddress()+":9090/data"+file.getName();
//		StringBuffer sb = new StringBuffer();
//		sb.append("{");
//		sb.append(	"\"type\":\"image\",");
//		sb.append("\"contents+\":"+"\""+str+"\"");
//		sb.append("}");
//		String myString = sb.toString();
		
		String myString = obj.toString();
	    try {
			for(int i=0;i<userThread.size();i++){
				userThread.get(i).sever_chat.buffw.write(myString+"\n");
				userThread.get(i).sever_chat.buffw.flush();
				userThread.elementAt(i).sever_chat.buffw.write(myString+"\n");
				userThread.elementAt(i).sever_chat.buffw.flush();
				
				
				/*
				userThread.get(i).chat.buffw.write(myString+"\n");
				userThread.get(i).chat.buffw.flush();*/
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	public void run() {
		
		while(true){
			listen();
		}
	}
}