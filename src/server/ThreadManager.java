package server;

import java.net.Socket;
import java.util.Vector;


public class ThreadManager {
	Server_chat sever_chat;
	Socket socket;
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();
	
	public ThreadManager(Socket socket,Vector<ThreadManager> userThread) {
		this.socket=socket;
		this.userThread=userThread;
<<<<<<< HEAD

		sever_chat=new Server_chat(socket,userThread);

		//chat=new Server_chat(socket,userThread);
=======
		sever_chat=new Server_chat(socket,userThread);
>>>>>>> origin/master
		
		
		
		
		//�� �߸������ߴ� �̸� �ٲٱ� �׸��� �� �������� send/listen���� type���� ���� �� �ٸ��� �Ѱ��ְԲ� �ٲ�ߵ� ���� ����~
		
		//������ �������� �ʿ䰡���� type������ ������ �����ҰŴϱ� ������ ����
	}
	
	public void start(){
		sever_chat.start();
	}
}
