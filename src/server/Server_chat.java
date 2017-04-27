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

//DB�� �������� ���� num/users/����
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
      //Ŭ�󿡼� �޴� �ͤ�
      
      String msg=null;
      DataInputStream dis =null;
      File file=null;
      FileOutputStream fos=null;
      BufferedOutputStream bos=null;
      try {
         msg=buffr.readLine();
         System.out.println("���� �ļ����"+  msg);
         //���⼭ �Ǵ��ϱ�
         System.out.println(msg+"ServerChat���� �ļ��� ����.....");
         JSONParser parser=new JSONParser();
         JSONObject obj=(JSONObject)parser.parse(msg);
         Vector<String> chatMembers= new Vector<String>() ; //ä�� ���õ� ������� ���� ����.
         
         String type=(String)obj.get("type");
         if(type.equals("chat")){
             value=(JSONArray)obj.get("contents");
             System.out.println("���� : �ļ��迭"+value.size());
             
             if(value.size()!=0){
                for(int i=0;i<value.size();i++){
                   System.out.println("�ļ��� ����?" + value.get(i));
                   if(i==0){
                      JSONObject json = (JSONObject)value.get(i);
                      System.out.println("ù��°��!!!!"+json.get("msg"));
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
                System.out.println("�¸���� ������ = " + value.size());
                if(value.size()!=0){
                	for(int i=0;i<value.size();i++){
                		//if(i==0){
                			JSONObject jsonArray = (JSONObject)value.get(i);
                			
               			    String val = (String)jsonArray.get("chatMember");
                    		System.out.println(val);
                       		chatMembers.add(val);

                	}
                }
                
                
                //������ chatMembers�϶�.
                sendMsg(msgValue,myIdValue,yourIdValue,chatMembers);
                //Ŭ���̾�Ʈ���� �޽���, ���� ���̵�, ���� ���̵�, ê ����� ����� ����.

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
      //������ �����ִ� ��
         
      
         String timeValue=getTime();
         System.out.println("��������????");
         //�Ǵ��ؼ� �����ֱ�
         StringBuffer sb = new StringBuffer();
		   sb.append("{");
	         sb.append(   "\"type\":\"chat\",");
	         sb.append("\"contents\":[{\"msg\":\""+msg+"\"},{\"myId\":\""+myIdValue+"\"},{\"yourId\":\""+yourIdValue+"\"},{\"timeValue\":\""+timeValue+"\"}],");
	         sb.append("\"chatMembers\":[");
	         int size = chatMember.size();
	         System.out.println("�������?" +size);
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
         System.out.println("������ �� �������� �����ִ°�."+myString);
         
		System.out.println("���������� ������ = "+userThread.size());
			//���⼭ �����带 ã���鼭... �ش��ϴ� ������ Ŭ���̾�Ʈ �����忡�� ���� �о���ΰ� �����...
			
			
			PreparedStatement pstmt =null;
			ResultSet rs= null;
			
			
			//1. �����ִ� ����� ���͸� �����.(���ʹ� userThread�� �ִ°Ÿ� �����ͼ� ���Ϳ� ������ ��.)
			Vector<Vector> chatMates = new Vector<Vector>();
			Vector<Vector> chatMates2 = new Vector<Vector>();
			
			
			//2. ä�ð� ���õ� �ֵ��� �ϳ��� ��� �¸���Ʈ�� �ø���.
			Vector<Object> chatConsist = new Vector<Object>();
			Vector<ThreadManager> chatConsist2 = new Vector<ThreadManager>();
			
			
			for(int i=0; i<userThread.size();i++){
				//�׽�Ʈ��(����)
				System.out.println("1");
				for(int j=0;j<chatMember.size();j++){
					System.out.println("2");
					System.out.println("���̵��??? = "+userThread.elementAt(i).id);
					System.out.println("�񱳵Ǵ� ���̵� ���� = "+chatMember.get(j));
					if(userThread.elementAt(i).id.equals(chatMember.get(j))){
						chatConsist2.add(userThread.elementAt(i));
						System.out.println("���� ���������� �ּҴ�?"+userThread.elementAt(i));
						System.out.println("ê ���ý�2�� ���?" + chatConsist2.size());
					}
				}
				
/*				//����1:1ä�ÿ�
				if(userThread.elementAt(i).id.equals(myIdValue)){
					chatConsist.add(userThread.elementAt(i));
					System.out.println("���� ������ �ּҴ�?"+ userThread.elementAt(i));
				}else if(userThread.elementAt(i).id.equals(yourIdValue)){
					chatConsist.add(userThread.elementAt(i));
					System.out.println("���� ������ �ּҴ�111?"+ userThread.elementAt(i));
				}*/
				
			}
			//chatMates.add(chatConsist);
			chatMates2.add(chatConsist2);					//�׽�Ʈ��(����)
			
			System.out.println("ä�ð��� �������� ������ ����ִ� �������?" + chatConsist2.size());
			

			System.out.println("���� ���̵� ���� ���̵�"+myIdValue + " " + yourIdValue);
			
			for(int i=0;i<chatMates2.size();i++){
				Vector<String> idGet = new Vector<String>();
				ThreadManager tm=null;
				Vector vec=null;
				boolean myid= false;
				boolean yourid = false;
/*				for(int j=0;j<chatConsist.size();j++){
					vec = chatMates.elementAt(i);
					tm = (ThreadManager)vec.elementAt(j);

					System.out.println("�����忡 ����ִ� ���̵� ���� = "+tm.id);
					//tm�� ����մ� ID��������...
					idGet.add(tm.id);
			
				}*/

				//�׽�Ʈ��(����)
				vec = chatMates2.elementAt(i);
				for(int j=0;j<chatConsist2.size();j++){
					tm = (ThreadManager)vec.elementAt(j);

					System.out.println("�����忡 ����ִ� ���̵� ���� = "+tm.id);
					//tm�� ����մ� ID��������...
					idGet.add(tm.id);
			
				}
				
				//=======================================================
				
/*				for(int q=0;q<idGet.size();q++){
					System.out.println("�� ť�� �������� ������? = " + idGet.get(q));
					if(idGet.get(q).equals(myIdValue)){
						myid = true;
					}else if(idGet.get(q).equals(yourIdValue)){
						yourid = true;
					}
				}*/
				
				
				//�����ŭ ī��Ʈ�� ��ġ�ϸ�!!! �׶� �׾ȿ� �մ� �����忡�� buffw��Ű��!!
				//�������� �������� ������� chatmate�� ������!!
				
/*		*/	//	���� ä�þȵǴ� ����!!!
 				int count =0;
				for(int j=0;j<idGet.size();j++){
					for(int q=0; q<chatConsist2.size();q++){
						System.out.println("�񱳵Ǵ� ���� ��ġ?? = " + idGet.get(j));
						System.out.println("��ȣ�� = "+ chatConsist2.get(q).id);
						if(idGet.get(j).equals(chatConsist2.elementAt(q).id)){
							count++;
						}	
					}
					
				}
				
				
				
				//int count =0;
				
				
				//------------------------------------------------------
				
/*				if(myid && yourid){
					try {
						//���� i�� �ε����� �����ͼ�..�� �ε����� �մ� ������Ŵ��� ��ü�� ��������.
						for(int j=0;j<chatConsist.size();j++){

							tm = (ThreadManager)vec.elementAt(j);
							System.out.println("�������??"  + tm);

							tm.sever_chat.buffw.write(myString+"\n");
							tm.sever_chat.buffw.flush();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/
				
				//�׽�Ʈ��(����)
				if(count==chatConsist2.size()){
					try {
						//���� i�� �ε����� �����ͼ�..�� �ε����� �մ� ������Ŵ��� ��ü�� ��������.
						for(int j=0;j<chatConsist2.size();j++){

							tm = (ThreadManager)vec.elementAt(j);
							System.out.println("�������??"  + tm);

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