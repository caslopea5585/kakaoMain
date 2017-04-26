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
                
                
                
                sendMsg(msgValue,myIdValue,yourIdValue);
                //sendMsg(msgValue,timeValue,senderValue,roomNumberValue);
                
                //System.out.println(msgValue+senderValue+timeValue+roomNumberValue);
             }
         
         }
         else if(type.equals("loginID")){
            String id=(String)obj.get("content");

            for(int i=0;i<userThread.size();i++){
               if(userThread.elementAt(i).id==null){
                  userThread.elementAt(i).id=id;
                  System.out.println(i+": "+id); 
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

   
   public void sendMsg(String msg,String myIdValue,String yourIdValue){
      //������ �����ִ� ��
         
      
         String timeValue=getTime();
         System.out.println("��������????");
         //�Ǵ��ؼ� �����ֱ�
         StringBuffer sb = new StringBuffer();
         sb.append("{");
         sb.append(   "\"type\":\"chat\",");
         sb.append("\"contents\":[{\"msg\":\""+msg+"\"},{\"myId\":\""+myIdValue+"\"},{\"yourId\":\""+yourIdValue+"\"},{\"timeValue\":\""+timeValue+"\"}]");
         sb.append("}");
         String myString = sb.toString();

/*         
          chatDto.setMsg(msgValue);
          chatDto.setSender(senderValue);
          chatDto.setTime(timeValue);*/

         
         
/*         try {
            buffw.write(myString+"\n");
            buffw.flush();
         } catch (IOException e) {
            e.printStackTrace();
         }*/


         System.out.println("���������� ������ = "+userThread.size());
         //���⼭ �����带 ã���鼭... �ش��ϴ� ������ Ŭ���̾�Ʈ �����忡�� ���� �о���ΰ� �����...
         
         
         PreparedStatement pstmt =null;
         ResultSet rs= null;
         
/*         String sql="select e_mail from chats where roomnumber=?";
         Vector<String> roomMember=new Vector<String>();
         try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, roomNumber);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
               roomMember.add(rs.getString("e_mail"));
            }
            
         } catch (SQLException e1) {
            e1.printStackTrace();
         }
*/         
         
         
         //1. �����ִ� ����� ���͸� �����.(���ʹ� userThread�� �ִ°Ÿ� �����ͼ� ���Ϳ� ������ ��.)
         Vector<ThreadManager> chatMates = new Vector<ThreadManager>();
         for(int i=0; i<userThread.size();i++){
            if(userThread.elementAt(i).id.equals(myIdValue) || userThread.elementAt(i).id.equals(yourIdValue)){
               chatMates.add(userThread.elementAt(i));
            }
         }
         
         for(int i=0; i<chatMates.size();i++){
            
            try {
               chatMates.elementAt(i).sever_chat.buffw.write(myString+"\n");
               chatMates.elementAt(i).sever_chat.buffw.flush();
               
               /*chatMates.elementAt(i).buffw.write(myString+"\n");
               chatMates.elementAt(i).buffw.flush();*/
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         
         
         
         
      for(int i=0;i<userThread.size();i++){
         
/*            try {
               //�� Ŭ���̾�Ʈ �����忡 buffw�ϴ� �۾�.
               
               for(int j=0;j<2;j++){
                  System.out.println("���� ���̵�"+userThread.elementAt(i).id);
                  if(userThread.elementAt(i).id.equals(roomMember.elementAt(j))){
                     userThread.elementAt(i).sever_chat.buffw.write(myString+"\n");
                     userThread.elementAt(i).sever_chat.buffw.flush();
                  }
               }



               //userThread.elementAt(i).chat.buffw.write(myString+"\n");
               //userThread.elementAt(i).chat.buffw.flush();

            } catch (IOException e) {
               e.printStackTrace();
            }*/
         }
   }

   public void send(File file){
      JSONObject obj=new JSONObject();
      obj.put("type", "image");
      obj.put("content", "http://"+socket.getInetAddress().getHostAddress()+":9090/data/"+file.getName());
      
//      String str="http://"+socket.getInetAddress().getHostAddress()+":9090/data"+file.getName();
//      StringBuffer sb = new StringBuffer();
//      sb.append("{");
//      sb.append(   "\"type\":\"image\",");
//      sb.append("\"contents+\":"+"\""+str+"\"");
//      sb.append("}");
//      String myString = sb.toString();
      
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