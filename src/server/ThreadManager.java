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
		sever_chat=new Server_chat(socket,userThread);//ㅋ 잘못구현했다 이름 바꾸기 그리고 이 스레드의 send/listen에서 type값에 따라서 값 다르게 넘겨주게끔 바꿔야됨 구조 고쳐~

=======
	

		chat=new Server_chat(socket,userThread);
		//chat=new Server_chat(socket,userThread);
>>>>>>> e336f3aa61f0c432e405b1aeb98ac2f7cf895312
		
		
		
		
		//ㅋ 잘못구현했다 이름 바꾸기 그리고 이 스레드의 send/listen에서 type값에 따라서 값 다르게 넘겨주게끔 바꿔야됨 구조 고쳐~
<<<<<<< HEAD
=======
		chat=new Server_chat(socket,userThread);//ㅋ 잘못구현했다 이름 바꾸기 그리고 이 스레드의 send/listen에서 type값에 따라서 값 다르게 넘겨주게끔 바꿔야됨 구조 고쳐~
>>>>>>> e336f3aa61f0c432e405b1aeb98ac2f7cf895312
		//스레드 여러개일 필요가없음 type값으로 나눠서 관리할거니까 괜찮아 ㅇㅇ
	}
	
	public void start(){
		chat.start();
	}
}
