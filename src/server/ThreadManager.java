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
		sever_chat=new Server_chat(socket,userThread);//�� �߸������ߴ� �̸� �ٲٱ� �׸��� �� �������� send/listen���� type���� ���� �� �ٸ��� �Ѱ��ְԲ� �ٲ�ߵ� ���� ����~
		
=======

		sever_chat=new Server_chat(socket,userThread);//�� �߸������ߴ� �̸� �ٲٱ� �׸��� �� �������� send/listen���� type���� ���� �� �ٸ��� �Ѱ��ְԲ� �ٲ�ߵ� ���� ����~

		chat=new Server_chat(socket,userThread);
>>>>>>> b7c035aa65167b1d98bfebb1cac37305ac27a449
		
		
		
		
		//�� �߸������ߴ� �̸� �ٲٱ� �׸��� �� �������� send/listen���� type���� ���� �� �ٸ��� �Ѱ��ְԲ� �ٲ�ߵ� ���� ����~
<<<<<<< HEAD
=======

>>>>>>> b7c035aa65167b1d98bfebb1cac37305ac27a449
		//������ �������� �ʿ䰡���� type������ ������ �����ҰŴϱ� ������ ����
		
	}
	
	public void start(){
		sever_chat.start();
	}
}
