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
		sever_chat=new Server_chat(socket,userThread);//�� �߸������ߴ� �̸� �ٲٱ� �׸��� �� �������� send/listen���� type���� ���� �� �ٸ��� �Ѱ��ְԲ� �ٲ�ߵ� ���� ����~

=======
	

		chat=new Server_chat(socket,userThread);
		//chat=new Server_chat(socket,userThread);
>>>>>>> e336f3aa61f0c432e405b1aeb98ac2f7cf895312
		
		
		
		
		//�� �߸������ߴ� �̸� �ٲٱ� �׸��� �� �������� send/listen���� type���� ���� �� �ٸ��� �Ѱ��ְԲ� �ٲ�ߵ� ���� ����~
<<<<<<< HEAD
=======
		chat=new Server_chat(socket,userThread);//�� �߸������ߴ� �̸� �ٲٱ� �׸��� �� �������� send/listen���� type���� ���� �� �ٸ��� �Ѱ��ְԲ� �ٲ�ߵ� ���� ����~
>>>>>>> e336f3aa61f0c432e405b1aeb98ac2f7cf895312
		//������ �������� �ʿ䰡���� type������ ������ �����ҰŴϱ� ������ ����
	}
	
	public void start(){
		chat.start();
	}
}
