package server;

import java.net.Socket;
import java.util.Vector;


public class ThreadManager {
	Server_chat sever_chat;
	Socket socket;
	String id=null;
	
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();
	
	public ThreadManager(Socket socket,Vector<ThreadManager> userThread) {
		this.socket=socket;
		this.userThread=userThread;
<<<<<<< HEAD
		sever_chat=new Server_chat(socket,userThread);
=======
<<<<<<< HEAD

		sever_chat=new Server_chat(socket,userThread);

		//chat=new Server_chat(socket,userThread);


=======
		sever_chat=new Server_chat(socket,userThread,index);
>>>>>>> ddf4b2c874c83a1a0b4ec43ddd1bdfff3e318a3b
		
>>>>>>> 497950331bdb0bac82aecb9f61f1deb3fdc3f317

	}
	
	public void start(){
		sever_chat.start();
	}
}
