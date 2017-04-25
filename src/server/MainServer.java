package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;

import db.DBContainer;

public class MainServer extends JFrame implements Runnable{
	int port=7777;
	ServerSocket server;
	Thread thread;//accept 스레드
	Socket socket;
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();//각 유저가 스레드매니저를 갖는다.
	DBContainer dbContainer;
	
	public MainServer() {
		dbContainer=new DBContainer();//db획득
		
		thread=new Thread(this);//accept
		thread.start();
		
		setSize(360,590);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void accept(){
		try {
			server=new ServerSocket(port);
			System.out.println("server 생성");
			
			while(true){
				socket=server.accept();
				String ip=socket.getInetAddress().getHostAddress();
				System.out.println(ip+" 접속\n");
				
				ThreadManager tm=new ThreadManager(socket,userThread);
				tm.start();
				userThread.add(tm);

				System.out.println("현재 접속자 숫자는"+userThread.size());
				
				if(server.isClosed()){
					System.out.println("현재 접속자는"+userThread.size()+"\n");
				}
<<<<<<< HEAD
				
=======
>>>>>>> 4919693f2a606d4f6080eb4d3b2b81929b7b85e7

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void run() {
		accept();
	}
	public static void main(String[] args) {
		new MainServer();
	}


}
