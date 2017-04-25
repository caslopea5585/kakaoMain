package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chatting.ChattingListPanel;
import client.chat.ChatMain;
import db.DBManager;
import friends.Friends;
import friends.FriendsListPanel;
import friends.PersonPanel;
import login.LoginPanel;
import setting.SettingPanel;

public class KakaoMain extends JFrame implements Runnable{
	Point mouseDownCompCoords = null;
	public JPanel[] panel;
	public JPanel p_list=new JPanel(); //p_search부분 제외한 아랫부분 전체 패널-그리드
	public JPanel menuPanel, friendsListPanel, chattingListPanel, settingPanel, p_center;
	DBManager manager;
	public Connection con;
	public String loginEmail;
	public Vector<MemberList> memberList;
	public Vector<Friends> friendsList;
	//Thread updateUIThread;
	public ArrayList<PersonPanel> myFriends = new ArrayList<PersonPanel>(); //friends 테이블 레코드 저장
	
	public ChatMain chat;//채널 새창*채팅목록에서 새로열기 가능하게 바꾸기

	public int friends_count;
	public JLabel la_friends=new JLabel();
	public KakaoMain(){
		getRootPane().setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY) );
		DBConn();
		panel=new JPanel[2];
		panel[0]=new LoginPanel(this);
		chat=new ChatMain(this);/////////채팅
		
		
		add(panel[0]);
		
		
		//setUndecorated(true); //타이틀바 제거
		setBounds(100,100,360,590);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	public void seeMain(String loginEmail,Vector<MemberList> memberList, Vector<Friends> friendsList){
		
		this.loginEmail=loginEmail;
		this.memberList=memberList;
		this.friendsList=friendsList;
		
		System.out.println("로그인 성공한 아이디는?? " + loginEmail);
		System.out.println("멤버안에들어있는 사람의 주소는??" + memberList);
		System.out.println("멤버안에들어있는 사람의 주소는??" + friendsList);
		/*System.out.println("첫번째 안에 있는 사람의 e_mail은? "+ memberList.get(0).getE_mail());
		System.out.println("두번째 안에 있는 사람의 e_mail은? "+ memberList.get(1).getE_mail());
		System.out.println("두번째 안에 있는 사람의 e_mail은? "+ memberList.get(2).getE_mail());*/
		
		for(int i=0; i<friendsList.size(); i++){
			System.out.println("내 이메일: "+ friendsList.get(i).getE_mail()+", 친구 이메일: "+friendsList.get(i).getYour_email());
		}
		
		p_list=new JPanel();
		
		p_center=new JPanel();
		
		
		menuPanel=new MenuPanel(this);
		friendsListPanel=new FriendsListPanel(this);
		chattingListPanel=new ChattingListPanel(this);
<<<<<<< HEAD
		settingPanel=new SettingPanel(this);
		
		
=======
>>>>>>> e336f3aa61f0c432e405b1aeb98ac2f7cf895312
		
		settingPanel=new SettingPanel(this);
		
		panel[1]=new JPanel();	
		
		//updateUIThread=new Thread(this);
				//updateUIThread.start();
		
		panel[1].setLayout(new BorderLayout()); //panel[1]의 북쪽에 메뉴바, 센터에 패널3개
	
		panel[1].add(menuPanel, BorderLayout.NORTH);
		p_center.add(friendsListPanel);
		p_center.add(chattingListPanel);
		p_center.add(settingPanel);
		panel[1].add(p_center);
		
		add(panel[1]);

		panel[0].setBackground(new Color(255, 235, 051));
		panel[0].setSize(360,590);
		
		panel[0].setVisible(false);
		panel[1].setVisible(true);

		dragMouse(panel[0]);
		
	}
	
	public void run() {
		while(true){
			try {
				Thread.sleep(500);
				//p_center.updateUI();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//윈도우안에 어떤 패널이 올지를 결정해주는 메서드 정의
	public void setPage(int index){
		for(int i=0; i<panel.length; i++){
			if(i==index){
				panel[i].setVisible(true);
			}else{
				panel[i].setVisible(false);
			}
			//pack(); //내용물의 크기만큼 윈도우의 크기 설정
			//setLocationRelativeTo(null); //화면 중앙
		}
	}
	
	public void dragMouse(JPanel panel){
		panel.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseDownCompCoords = null;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				mouseDownCompCoords = e.getPoint();
			}
		});

		panel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point currCoords = e.getLocationOnScreen();
		        setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
			}
		});
	}
	
	public void DBConn(){
		manager = DBManager.getInstance();
		con=manager.getConnection();
		//DB연결 객체 생성.	
	}
	
	public static void main(String[] args) {
		new KakaoMain();
	}

	
	
}
