package server;

import java.net.Socket;
import java.util.Vector;


public class ThreadManager {
	Server_chat chat;
	Socket socket;
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();
	
	public ThreadManager(Socket socket,Vector<ThreadManager> userThread) {
		this.socket=socket;
		this.userThread=userThread;
<<<<<<< HEAD
<<<<<<< HEAD
		sever_chat=new Server_chat(socket,userThread);//ㅋ 잘못구현했다 이름 바꾸기 그리고 이 스레드의 send/listen에서 type값에 따라서 값 다르게 넘겨주게끔 바꿔야됨 구조 고쳐~
		
=======

		sever_chat=new Server_chat(socket,userThread);//ㅋ 잘못구현했다 이름 바꾸기 그리고 이 스레드의 send/listen에서 type값에 따라서 값 다르게 넘겨주게끔 바꿔야됨 구조 고쳐~

		chat=new Server_chat(socket,userThread);
>>>>>>> b7c035aa65167b1d98bfebb1cac37305ac27a449
		
		
		
		
		//ㅋ 잘못구현했다 이름 바꾸기 그리고 이 스레드의 send/listen에서 type값에 따라서 값 다르게 넘겨주게끔 바꿔야됨 구조 고쳐~
<<<<<<< HEAD
=======

>>>>>>> b7c035aa65167b1d98bfebb1cac37305ac27a449
=======
		chat=new Server_chat(socket,userThread);//ㅋ 잘못구현했다 이름 바꾸기 그리고 이 스레드의 send/listen에서 type값에 따라서 값 다르게 넘겨주게끔 바꿔야됨 구조 고쳐~
>>>>>>> parent of 2f22038... 梨앹씠 1:1
		//스레드 여러개일 필요가없음 type값으로 나눠서 관리할거니까 괜찮아 ㅇㅇ
	}
	
	public void start(){
		chat.start();
	}
}
