package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import javax.swing.JFrame;

import db.DBContainer;
import db.DBManager;

public class MainServer extends JFrame implements Runnable{
	int port=7777;
	ServerSocket server;
	Thread thread;//accept ������
	Socket socket;
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();//�� ������ ������Ŵ����� ���´�.
	DBContainer dbContainer;
	
	Connection con;
	DBManager manager;
	int count =0;
	
	public MainServer() {
		dbContainer=new DBContainer();//dbȹ��
		
		thread=new Thread(this);//accept
		thread.start();
		
		manager  = DBManager.getInstance();
		con = manager.getConnection();
		
		setSize(360,590);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void accept(){
		try {
			server=new ServerSocket(port);
			System.out.println("server ����");
			
			while(true){
				socket=server.accept();
				String ip=socket.getInetAddress().getHostAddress();
				System.out.println(ip+" ����\n");
				insertIp();
				ThreadManager tm=new ThreadManager(socket,userThread,count++);
				tm.start();
				userThread.add(tm);

				System.out.println("���� ������ ���ڴ�"+userThread.size());
				
				if(server.isClosed()){
					System.out.println("���� �����ڴ�"+userThread.size()+"\n");
				}


			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void insertIp(){
/*		PreparedStatement pstmt=null;
		ResultSet rs =null;
		
		String sql="insert into member()";
		*/
		
		
		
		
	}

	public void run() {
		accept();
	}
	public static void main(String[] args) {
		new MainServer();
	}


}
