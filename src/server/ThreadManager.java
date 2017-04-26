package server;

import java.net.Socket;
import java.util.Vector;


public class ThreadManager {
	Server_chat sever_chat;
	Socket socket;
	String id;
	
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();
	
	public ThreadManager(Socket socket,Vector<ThreadManager> userThread,int index) {
		this.socket=socket;
		this.userThread=userThread;
<<<<<<< HEAD

		sever_chat=new Server_chat(socket,userThread);

		//chat=new Server_chat(socket,userThread);


=======
		sever_chat=new Server_chat(socket,userThread,index);
		
>>>>>>> 497950331bdb0bac82aecb9f61f1deb3fdc3f317

	}
	
	public void start(){
		sever_chat.start();
	}
}
