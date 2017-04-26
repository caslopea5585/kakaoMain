package profile;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import client.chat.ChatMain;
import main.KakaoMain;
import main.MemberList;


public class Profile extends JFrame implements ActionListener{
	Point mouseDownCompCoords = null;
	
	
	Profile profile;
	Canvas can_north_img,can_south_img,can_profile;
	JLabel la_name,la_chat,la_manager;
	URL url_profileBackground,url_profileSouth,url_profileImage;
	JPanel p_status;
	BufferedImage buffr_north,buffr_south,buffr_profile;
	JLayeredPane layeredPane;
	ImageIcon chat,manager;
	RoundButton bt_chat,bt_manager,bt_back_profile;
	String status_msg="상태메시지";
	JFileChooser chooser;
	MemberList memberList;
	
	
	KakaoMain kakaoMain;
	boolean flag; //나인지 아닌지 구별
	int index; //멤버리스트의 인덱스 찾기
	Vector roomNumberArray=new Vector();//룸넘버 정보를 저장하기 위한 벡터
	
	public Profile(String photopath,KakaoMain kakaoMain,boolean flag,int index) {
		
		profile = this;
		this.kakaoMain=kakaoMain;
		this.flag = flag;
		this.index= index;

		layeredPane = new JLayeredPane();
		url_profileBackground = this.getClass().getResource("/bg_north.png");	//상단배경
		url_profileSouth=this.getClass().getResource("/bg_south.png");	//하단
		url_profileImage=this.getClass().getResource(photopath); //프로필사진
		status_msg=kakaoMain.memberList.get(index).getStatus_msg();
		
		try {
			buffr_south = ImageIO.read(url_profileSouth);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(kakaoMain.loginEmail);
		
		can_north_img = new Canvas(){
			public void paint(Graphics g) {
				   
				try {
					buffr_north = ImageIO.read(url_profileBackground);
					buffr_profile= ImageIO.read(url_profileImage);
				} catch (IOException e) {
					e.printStackTrace();
				}		
																					//잘라내는 원의 크기를 결정
		        Ellipse2D.Double ellipse1 = new Ellipse2D.Double(99,181,100,98); 
		        Area circle = new Area(ellipse1);
		        
		        g.drawImage(buffr_north, 0, 0, 400,250,this);  //백그라운드이미지
		        
		        g.setFont(new Font("돋움", Font.PLAIN, 25));
		        g.setColor(Color.BLACK);
		        
		        g.drawString(status_msg, 120, 100);						//상태메시지
		        
		        
		        Graphics2D g2 =(Graphics2D) g;
		        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		        g2.setClip(circle);
		        
		        g2.drawImage(buffr_profile, 100,180,100, 100, null);
		        g2.setColor(Color.WHITE);
		        g2.setClip(null);
		        Stroke s = new BasicStroke(2);
		        g2.setStroke(s);
		        g2.setColor(Color.BLACK);
		        //g2.draw(circle);
		        g2.dispose();
		        
			}

			
		};
		can_south_img = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage((Image)buffr_south, 0, 0, 300, 300,this);
			}
		};
		
		
		la_name = new JLabel("아이디명");
		la_name.setForeground(Color.black);
		la_name.setText(kakaoMain.memberList.get(index).getNik_id());
		
		
		ImageIcon chat = new ImageIcon(this.getClass().getResource("/chat.png"));
		bt_chat = new RoundButton(chat);
		
		
		ImageIcon manager = new ImageIcon(this.getClass().getResource("/manager.png"));
		bt_manager = new RoundButton(manager);
		
		
		ImageIcon back_profile = new ImageIcon(this.getClass().getResource("/back_profile.png"));
		bt_back_profile = new RoundButton(back_profile);
		
		chooser= new JFileChooser("C:/java_workspace2/ExKakaoProject/res");
	
		la_chat = new JLabel("채팅하기");
		la_manager =new JLabel("수정하기");
		
		
		la_chat.setForeground(Color.BLACK);
		la_manager.setForeground(Color.BLACK);
		
		

		//                            x  y   width  height
		can_north_img.setBounds(0, 0, 400, 300);
		la_name.setBounds(145, 280, 70, 50);
		bt_chat.setBounds(65, 340, 50, 50);
		
		la_chat.setBounds(65, 390, 70, 30);
		la_manager.setBounds(165, 390, 70, 30);
		
		
		layeredPane.add(can_north_img, 1);
		layeredPane.add(la_name, 2,1);
		layeredPane.add(bt_chat, 3,2);
		
		layeredPane.add(la_chat, 4,3);
		layeredPane.add(la_manager, 4,3);
		
		bt_manager.setBounds(165, 340, 50, 50);
		bt_back_profile.setBounds(245, 250, 45, 45);
		layeredPane.add(bt_manager, 4,3);
		layeredPane.add(bt_back_profile, 4,3);
		
		
		
		for(int i=0;i<kakaoMain.memberList.size();i++){
			System.out.println("e_mail"+kakaoMain.memberList.get(i).getE_mail());
		}
		
		System.out.println("프로필시"+flag); //나이면 false, 나 아니면 true
		if(!flag){
			//내가 나를 눌렀을때 소스 구현
			bt_manager.setVisible(flag);
			bt_back_profile.setVisible(flag);
			la_manager.setVisible(flag);
			bt_chat.setBounds(120, 340, 50, 50);
			la_chat.setBounds(120, 390, 70, 30);
			
			
		}else{
			//내가 다른사람을 눌렀을때 소스
			
		}
		
		can_north_img.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				Object obj =e.getPoint();
				Point p = (Point)obj;
				//x의 범위 116~172 y의 ㅂ범위 201~163
				int x= (int)p.getX();
				int y =(int)p.getY();
				System.out.println(p.getX()+","+p.getY());
				
				if(x>116 && x<172 && y>200&& y<260){
					//해당범위를 클릭했을때만 프로필 이미지가 확대되도록 뜸.
					ProfileImage profileImage = new ProfileImage(profile);
				}else{
					
				}
			}
		});
		
		
		bt_manager.addActionListener(this);
		bt_back_profile.addActionListener(this);
		bt_chat.addActionListener(this);
		
		
		add(layeredPane);
		dragMouse(this);
		setUndecorated(true); //타이틀바 제거
		setBackground(Color.WHITE);
		setSize(310, 460);
		setVisible(true);
		
		
	}
	
	public void dragMouse(JFrame panel){
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
	
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt_manager){
			EditProfile editProfile = new EditProfile(this,kakaoMain);
			
		}else if(obj==bt_back_profile){
			int result = chooser.showOpenDialog(this);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				url_profileBackground=this.getClass().getResource("/"+file.getName()+""); //프로필사진
				kakaoMain.memberList.get(0).setProfile_Back_Img(file.getName());
				can_north_img.repaint();
			}
		}else if(obj==bt_chat){
			//ChatMain chat=new ChatMain(kakaoMain);
			//챗메인을 여기서 만들자. (여기서 대화방 생성)
			chat();
			
			
			//System.out.println(kakaoMain.loginEmail + kakaoMain.memberList.get(index).getE_mail());
			//로그인 한 사람의 이메일  && 채팅 대상자의 이메일(방번호 조회의 대상)
			
			
		}
		
	}
	
	public void chat(){
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql="select roomNumber from Chats where e_mail=?";
		boolean findChat=true; //채팅방을 찾는지 못찾는지의 플래그값.
		try {
			pstmt = kakaoMain.con.prepareStatement(sql);
			pstmt.setString(1, kakaoMain.loginEmail);
			rs = pstmt.executeQuery();
			
			roomNumberArray.removeAll(roomNumberArray);
			System.out.println(roomNumberArray.size() + "프로필에서 룸넘버 사이즈");
			
			while(rs.next()){
				roomNumberArray.add(rs.getInt("roomNumber"));
			}
			if(roomNumberArray.size()==0){
				//아무것도 없으니 만들자
				sql="insert into Chats(roomNumber, e_mail) values(seq_chats.nextval,?)";
				pstmt = kakaoMain.con.prepareStatement(sql);
				pstmt.setString(1, kakaoMain.loginEmail);
				pstmt.executeUpdate(); 
				
				sql="insert into Chats(roomNumber, e_mail) values(seq_chats.currval,?)";
				pstmt = kakaoMain.con.prepareStatement(sql);
				pstmt.setString(1, kakaoMain.memberList.get(index).getE_mail());
				pstmt.executeUpdate(); 
									
				ChatMain chat=new ChatMain(kakaoMain,kakaoMain.loginEmail,kakaoMain.memberList.get(index).getE_mail());
				chat.setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
				chat.setVisible(true);//화면 교체
				
				kakaoMain.chat.add(chat);
				System.out.println("새로생성");
				System.out.println("새로 생성 후 메인의 챗 사이즈는?"  +kakaoMain.chat.size());
				
			}else{
				//조회필요
				
				
				//1. 내이메일로 가지고 내 룸넘버를 조회.
				//2. 대화할 상대와 만든 룸넘버가 있는지 검사
				//    2-1) 있다면
				//			- 그방을 visible(true)
				//	  2-2) 없다면
				//			-- chatMain 을 새로 생성.
				
				
				sql="select roomnumber from chats where e_mail=?";
				pstmt = kakaoMain.con.prepareStatement(sql);
				pstmt.setString(1, kakaoMain.loginEmail);
				rs = pstmt.executeQuery();
				
				System.out.println("룸넘버 가져오니???");
				Vector myRoomNum = new Vector<>();
				
				while(rs.next()){
					int room = rs.getInt("roomnumber");
					//myRoomNum.add(rs.getInt("roomnumber"));
					System.out.println("가져오는 룸의 번호는? " + room);
					myRoomNum.add(room);
					
				}
				
				System.out.println("룸넘버의 사이즈는????? " + myRoomNum.size());
				for(int i=0;i<myRoomNum.size();i++){
					sql="select e_mail from chats where roomnumber=?";
					pstmt = kakaoMain.con.prepareStatement(sql);
					pstmt.setInt(1, (Integer)myRoomNum.get(i));
					rs = pstmt.executeQuery();
					while(rs.next()){
						if(kakaoMain.memberList.get(index).getE_mail().equals(rs.getString("e_mail"))){
							for(int j=0; j<kakaoMain.chat.size();j++){
								System.out.println(kakaoMain.loginEmail +kakaoMain.chat.get(j).loginEmail );
								System.out.println(kakaoMain.memberList.get(index).getE_mail()+(kakaoMain.chat.get(j).yourEmail));
								if(kakaoMain.loginEmail.equals(kakaoMain.chat.get(j).loginEmail) && kakaoMain.memberList.get(index).getE_mail().equals(kakaoMain.chat.get(j).yourEmail)){
									System.out.println("기존생성");
									//룸사이즈가 0이 아니고 내가 대화하고 싶은 사람이랑 이미 대화창을 만들었을때 & 그 대화창을 보여준다.
									kakaoMain.chat.get(j).setVisible(true);
									kakaoMain.chat.get(j).setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
									findChat=false;
								}
							}
						}
					}
				}
				
				if(findChat){ //룸사이즈는 0이 아닌데 내가 대화하고싶은사람이랑 대화창이 없을떄!! 대화창을 새로 생성
					System.out.println("룸 없는데 나오니??????" );
					sql="insert into Chats(roomNumber, e_mail) values(seq_chats.nextval,?)";
					pstmt = kakaoMain.con.prepareStatement(sql);
					pstmt.setString(1, kakaoMain.loginEmail);
					pstmt.executeUpdate(); 
					
					sql="insert into Chats(roomNumber, e_mail) values(seq_chats.currval,?)";
					pstmt = kakaoMain.con.prepareStatement(sql);
					pstmt.setString(1, kakaoMain.memberList.get(index).getE_mail());
					pstmt.executeUpdate(); 
					
					ChatMain chat=new ChatMain(kakaoMain,kakaoMain.loginEmail,kakaoMain.memberList.get(index).getE_mail());
					chat.setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
					chat.setVisible(true);//화면 교체
					kakaoMain.chat.add(chat);
					
				}
/*				
				for(int i=0; i<kakaoMain.chat.size();i++){
					System.out.println(kakaoMain.loginEmail +kakaoMain.chat.get(i).loginEmail );
					System.out.println(kakaoMain.memberList.get(index).getE_mail()+(kakaoMain.chat.get(i).yourEmail));
					if(kakaoMain.loginEmail.equals(kakaoMain.chat.get(i).loginEmail) && kakaoMain.memberList.get(index).getE_mail().equals(kakaoMain.chat.get(i).yourEmail)){
						System.out.println("기존생성");
						kakaoMain.chat.get(i).setVisible(true);
						kakaoMain.chat.get(i).setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
					}
				}*/
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
