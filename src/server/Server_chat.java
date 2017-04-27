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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import db.DBManager;

//DB가 가져야할 정보 num/users/내용
public class Server_chat extends Thread{
   Socket socket;
   BufferedReader buffr;
   BufferedWriter buffw;
   Vector<ThreadManager> userThread=new Vector<ThreadManager>();
   
   JSONArray value;
   String msgValue,myIdValue,yourIdValue;
   int roomNumberValue;
   JSONObject valueCheck;
   Connection con;
   DBManager manager;
   
   public Server_chat(Socket socket,Vector<ThreadManager> userThread) {
      this.socket=socket;
      this.userThread=userThread;
      manager  = DBManager.getInstance();
      con = manager.getConnection();
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
         Vector<String> chatMembers= new Vector<String>() ; //채팅 관련된 사람들을 묶는 벡터.
         
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
                   }
                }
                
                
                value = (JSONArray)obj.get("chatMembers");
                System.out.println("쳇멤버의 사이즈 = " + value.size());
                if(value.size()!=0){
                	for(int i=0;i<value.size();i++){
                		//if(i==0){
                			JSONObject jsonArray = (JSONObject)value.get(i);
                			
               			    String val = (String)jsonArray.get("chatMember");
                    		System.out.println(val);
                       		chatMembers.add(val);

                	}
                }
                
                
                //벨류가 chatMembers일때.
                sendMsg(msgValue,myIdValue,yourIdValue,chatMembers);
                //클라이언트에게 메시지, 나의 아이디, 상대방 아이디, 챗 멤버들 목록을 전송.

             }
         
         }
         else if(type.equals("loginID")){
            String id=(String)obj.get("content");
            
            int c=0;
            for(int i=0;i<userThread.size();i++){
            	if(userThread.get(i).id==id){
            		c++;
            	}
            }
            if(c==0){
	            for(int i=0;i<userThread.size();i++){
	               if(userThread.elementAt(i).id==null){            	  
	                  userThread.elementAt(i).id=id;
	                  System.out.println(i+": "+id); 
	               }
	            }
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
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
               }
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
          try {
             if(fos!=null){
                fos.close();
             }
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   
   public void sendMsg(String msg,String myIdValue,String yourIdValue,Vector chatMember){
      //서버가 보내주는 것
         
      
         String timeValue=getTime();
         System.out.println("보내지니????");
         //판단해서 보내주기
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
         System.out.println("서버가 각 유저에게 보내주는거."+myString);
         
		System.out.println("유저쓰레드 사이즈 = "+userThread.size());
			//여기서 쓰레드를 찾으면서... 해당하는 유저의 클라이언트 쓰레드에만 내가 읽어들인걸 쓰면됨...
			
			
			PreparedStatement pstmt =null;
			ResultSet rs= null;
			
			
			//1. 보내주는 대상의 벡터를 만든다.(벡터는 userThread에 있는거를 가져와서 벡터에 담으면 됨.)
			Vector<Vector> chatMates = new Vector<Vector>();
			Vector<Vector> chatMates2 = new Vector<Vector>();
			
			
			//2. 채팅과 관련된 애들을 하나로 묶어서 쳇메이트에 올린다.
			Vector<Object> chatConsist = new Vector<Object>();
			Vector<ThreadManager> chatConsist2 = new Vector<ThreadManager>();
			
			
			for(int i=0; i<userThread.size();i++){
				//테스트용(다중)
				System.out.println("1");
				for(int j=0;j<chatMember.size();j++){
					System.out.println("2");
					System.out.println("아이디는??? = "+userThread.elementAt(i).id);
					System.out.println("비교되는 아이디 값은 = "+chatMember.get(j));
					if(userThread.elementAt(i).id.equals(chatMember.get(j))){
						chatConsist2.add(userThread.elementAt(i));
						System.out.println("담기는 유저쓰레드 주소는?"+userThread.elementAt(i));
						System.out.println("챗 컨시스2가 담김?" + chatConsist2.size());
					}
				}
				
/*				//실제1:1채팅용
				if(userThread.elementAt(i).id.equals(myIdValue)){
					chatConsist.add(userThread.elementAt(i));
					System.out.println("담기는 쓰레드 주소는?"+ userThread.elementAt(i));
				}else if(userThread.elementAt(i).id.equals(yourIdValue)){
					chatConsist.add(userThread.elementAt(i));
					System.out.println("담기는 쓰레드 주소는111?"+ userThread.elementAt(i));
				}*/
				
			}
			//chatMates.add(chatConsist);
			chatMates2.add(chatConsist2);					//테스트용(다중)
			
			System.out.println("채팅관련 쓰레드의 정보가 담겨있는 사이즈는?" + chatConsist2.size());
			

			System.out.println("들어온 아이디 선택 아이디"+myIdValue + " " + yourIdValue);
			
			for(int i=0;i<chatMates2.size();i++){
				Vector<String> idGet = new Vector<String>();
				ThreadManager tm=null;
				Vector vec=null;
				boolean myid= false;
				boolean yourid = false;
/*				for(int j=0;j<chatConsist.size();j++){
					vec = chatMates.elementAt(i);
					tm = (ThreadManager)vec.elementAt(j);

					System.out.println("쓰레드에 들어있는 아이디 값은 = "+tm.id);
					//tm에 들어잇는 ID가져오기...
					idGet.add(tm.id);
			
				}*/

				//테스트용(다중)
				vec = chatMates2.elementAt(i);
				for(int j=0;j<chatConsist2.size();j++){
					tm = (ThreadManager)vec.elementAt(j);

					System.out.println("쓰레드에 들어있는 아이디 값은 = "+tm.id);
					//tm에 들어잇는 ID가져오기...
					idGet.add(tm.id);
			
				}
				
				//=======================================================
				
/*				for(int q=0;q<idGet.size();q++){
					System.out.println("겟 큐가 가져오는 값들은? = " + idGet.get(q));
					if(idGet.get(q).equals(myIdValue)){
						myid = true;
					}else if(idGet.get(q).equals(yourIdValue)){
						yourid = true;
					}
				}*/
				
				
				//사이즈만큼 카운트와 일치하면!!! 그때 그안에 잇는 쓰레드에게 buffw시키자!!
				//돌려야할 쓰레드의 싸이즈는 chatmate의 사이즈!!
				
/*		*/	//	다중 채팅안되는 버전!!!
 				int count =0;
				for(int j=0;j<idGet.size();j++){
					for(int q=0; q<chatConsist2.size();q++){
						System.out.println("비교되는 값이 일치?? = " + idGet.get(j));
						System.out.println("괄호값 = "+ chatConsist2.get(q).id);
						if(idGet.get(j).equals(chatConsist2.elementAt(q).id)){
							count++;
						}	
					}
					
				}
				
				
				
				//int count =0;
				
				
				//------------------------------------------------------
				
/*				if(myid && yourid){
					try {
						//위에 i의 인덱스를 가져와서..그 인덱스에 잇는 쓰레드매니저 전체를 돌려야함.
						for(int j=0;j<chatConsist.size();j++){

							tm = (ThreadManager)vec.elementAt(j);
							System.out.println("쓰레드는??"  + tm);

							tm.sever_chat.buffw.write(myString+"\n");
							tm.sever_chat.buffw.flush();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/
				
				//테스트용(다중)
				if(count==chatConsist2.size()){
					try {
						//위에 i의 인덱스를 가져와서..그 인덱스에 잇는 쓰레드매니저 전체를 돌려야함.
						for(int j=0;j<chatConsist2.size();j++){

							tm = (ThreadManager)vec.elementAt(j);
							System.out.println("쓰레드는??"  + tm);

							tm.sever_chat.buffw.write(myString+"\n");
							tm.sever_chat.buffw.flush();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
			
   }

   public void send(File file){
      JSONObject obj=new JSONObject();
      obj.put("type", "image");
      obj.put("content", "http://"+socket.getInetAddress().getHostAddress()+":9090/data/"+file.getName());
      
      String myString = obj.toString();
       try {
         for(int i=0;i<userThread.size();i++){

            userThread.get(i).sever_chat.buffw.write(myString+"\n");
            userThread.get(i).sever_chat.buffw.flush();
            userThread.elementAt(i).sever_chat.buffw.write(myString+"\n");
            userThread.elementAt(i).sever_chat.buffw.flush();
   
            //userThread.get(i).chat.buffw.write(myString+"\n");
            //userThread.get(i).chat.buffw.flush();

            userThread.elementAt(i).sever_chat.buffw.write(myString+"\n");
            userThread.elementAt(i).sever_chat.buffw.flush();

         }
         
      } catch (IOException e) {
         e.printStackTrace();
      }
   
   }
   static String getTime() {
      SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
      return sdf.format(new Date());
   }
   
   
   public void run() {
      
      while(true){
         listen();
      }
   }
}