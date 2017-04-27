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
import java.util.Vector;

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
	String msgValue,timeValue,myIdValue,yourIdValue;
	int roomNumberValue;
	JSONArray value;
	JSONObject valueCheck;
	boolean flag = true;
	int index=0;
	
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
		Vector<String> chatMember=new Vector<String>();
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
					 
					 
					 
				 value = (JSONArray)obj.get("chatMembers");
				 if(value.size()!=0){
					 for(int i=0; i<value.size();i++){
						 JSONObject json  = (JSONObject)value.get(i);
						 
						 String val = (String)json.get("chatMember");
						 System.out.println("채팅방에 들어있는 사람은? = "+ val);
						 if(i==0){
							 kakaoMain.senderId = val; //보내는 사람이 누구인지 알려고! (chatRender에 쓰일예쩡)
						 }
						 chatMember.add(val);
					}
					 kakaoMain.chatMember=chatMember;
				 }
/*					 chatDto.setMyId(myIdValue);
					 chatDto.setYourId(yourIdValue);*/
					 chatDto.setMsg(msgValue);
					 chatDto.setTimeValue(timeValue);
					  
					
					  //kMain.chat.get(i).model.addRow(chatDto);
					 //모델에 행을 추가시켜야함
/*					 System.out.println("카카오메시지 사이즈는???" + kakaoMain.chat.size());
					 
					for(int i=0;i<kakaoMain.chat.size();i++){
						String myid=kakaoMain.chat.get(i).myId;
						String yourid= kakaoMain.chat.get(i).yourId;
						
						System.out.println("가져오는 값은 = "+myid + yourid);
						
						//여기서 쳇 멤버에 담긴 배열의 아이디가 모두 같아야하는거지...
						if((myIdValue.equals(myid)|| myIdValue.equals(yourid)) && (yourIdValue.equals(yourid)||yourIdValue.equals(myid))){
							kakaoMain.chat.get(i).model.addRow(chatDto);
							System.out.println("클라이언트쓰레드??? 뿌려져야할 chat의 주소는 ??"+ kakaoMain.chat.get(i));
						}
						
					}
					*/
					
					//테스트용(다중)
					//쳇은 사용자의 정보를 담은 chatmember를 가지고 있따.
					//개별아이디가 아니라. 
					int count=0;
					
					for(int i=0; i<kakaoMain.chat.size();i++){
						//쳇멤버리스트에서 사람을 조사!!
						//Dto의 chatMember의 인덱스와..chat의 배열안에있는 chatMember의 아이디가 같으면 그 인덱스를...라이터
						
						//쳇의 i만큼 돌리는데 그 안에 있는 쳇멤버를 쳇멤버의 사이즈만큼 j번돌려야한다.
						//j가 한번돌때 Dto의 chatMember는 q번 돌아야 한다.(dto의 쳇멤버만큼)
						
						for(int j=0; j<kakaoMain.chat.get(i).chatMember.size();j++){
							
							for(int q=0; q<chatMember.size();q++){
									if(kakaoMain.chat.get(i).chatMember.get(j).equals(chatMember.get(q)) && kakaoMain.chat.get(i).chatMember.size()==chatMember.size()){
										System.out.println("클쓰 괄호 밖 ="+kakaoMain.chat.get(i).chatMember.get(j));
										System.out.println("클쓰 괄호 안 = " + chatMember.get(q));
										count++;
										index=i;
									}
							}
							
						}
						
					}
					System.out.println("클 쓰레드 카운터는 " + count);
					System.out.println("클 쓰레드 인덱스는?? " + index);
					if(count==kakaoMain.chat.get(index).chatMember.size()){
						//만약 쳇멤버의 사이즈가 2가 아니라면 setyourID는 사이즈 -1까지 돌아야한다.
						chatDto.setMyId(chatMember.get(0));
						System.out.println("쳇멤버의 사이즈는"+chatMember.size());
						if(chatMember.size() >=3){
							//1부터 사이즈 인덱스까지 setyourId를 해줘야한다.
							for(int i=1; i<chatMember.size();i++){
								chatDto.setYourId(chatMember.get(i));
							}
						}else{
							
							chatDto.setYourId(chatMember.get(1));							
						}
						
						kakaoMain.chat.get(index).model.addRow(chatDto);
						System.out.println("클 쓰레드 쳇의 겟의 인덱스의 쳇메인? "+kakaoMain.chat.get(index));
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
	
	public void sendMsg(String msg,String myId,String yourId,Vector<String> chatMember){
		try {
			StringBuffer sb = new StringBuffer();
			   sb.append("{");
		         sb.append(   "\"type\":\"chat\",");
		         sb.append("\"contents\":[{\"msg\":\""+msg+"\"},{\"myId\":\""+myIdValue+"\"},{\"yourId\":\""+yourIdValue+"\"},{\"timeValue\":\""+timeValue+"\"}],");
		         sb.append("\"chatMembers\":[");
		         int size = chatMember.size();
		         System.out.println("사이즈는?" +size);
		         for(int i=0;i<chatMember.size();i++){
		        	 if(i==chatMember.size()-1){
		        		sb.append("{\"chatMember\" :\""+chatMember.get(i)+"\"}");
		        	 }else{
		        		 sb.append("{\"chatMember\" :\""+chatMember.get(i)+"\"},");
		        	 }
		         }
		         sb.append(" ] ");
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

		}
	}
}
