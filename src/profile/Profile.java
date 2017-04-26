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
	String status_msg="���¸޽���";
	JFileChooser chooser;
	MemberList memberList;
	
	
	KakaoMain kakaoMain;
	boolean flag; //������ �ƴ��� ����
	int index; //�������Ʈ�� �ε��� ã��
	Vector roomNumberArray=new Vector();//��ѹ� ������ �����ϱ� ���� ����
	
	public Profile(String photopath,KakaoMain kakaoMain,boolean flag,int index) {
		
		profile = this;
		this.kakaoMain=kakaoMain;
		this.flag = flag;
		this.index= index;

		layeredPane = new JLayeredPane();
		url_profileBackground = this.getClass().getResource("/bg_north.png");	//��ܹ��
		url_profileSouth=this.getClass().getResource("/bg_south.png");	//�ϴ�
		url_profileImage=this.getClass().getResource(photopath); //�����ʻ���
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
																					//�߶󳻴� ���� ũ�⸦ ����
		        Ellipse2D.Double ellipse1 = new Ellipse2D.Double(99,181,100,98); 
		        Area circle = new Area(ellipse1);
		        
		        g.drawImage(buffr_north, 0, 0, 400,250,this);  //��׶����̹���
		        
		        g.setFont(new Font("����", Font.PLAIN, 25));
		        g.setColor(Color.BLACK);
		        
		        g.drawString(status_msg, 120, 100);						//���¸޽���
		        
		        
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
		
		
		la_name = new JLabel("���̵��");
		la_name.setForeground(Color.black);
		la_name.setText(kakaoMain.memberList.get(index).getNik_id());
		
		
		ImageIcon chat = new ImageIcon(this.getClass().getResource("/chat.png"));
		bt_chat = new RoundButton(chat);
		
		
		ImageIcon manager = new ImageIcon(this.getClass().getResource("/manager.png"));
		bt_manager = new RoundButton(manager);
		
		
		ImageIcon back_profile = new ImageIcon(this.getClass().getResource("/back_profile.png"));
		bt_back_profile = new RoundButton(back_profile);
		
		chooser= new JFileChooser("C:/java_workspace2/ExKakaoProject/res");
	
		la_chat = new JLabel("ä���ϱ�");
		la_manager =new JLabel("�����ϱ�");
		
		
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
		
		System.out.println("�����ʽ�"+flag); //���̸� false, �� �ƴϸ� true
		if(!flag){
			//���� ���� �������� �ҽ� ����
			bt_manager.setVisible(flag);
			bt_back_profile.setVisible(flag);
			la_manager.setVisible(flag);
			bt_chat.setBounds(120, 340, 50, 50);
			la_chat.setBounds(120, 390, 70, 30);
			
			
		}else{
			//���� �ٸ������ �������� �ҽ�
			
		}
		
		can_north_img.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				Object obj =e.getPoint();
				Point p = (Point)obj;
				//x�� ���� 116~172 y�� ������ 201~163
				int x= (int)p.getX();
				int y =(int)p.getY();
				System.out.println(p.getX()+","+p.getY());
				
				if(x>116 && x<172 && y>200&& y<260){
					//�ش������ Ŭ���������� ������ �̹����� Ȯ��ǵ��� ��.
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
		setUndecorated(true); //Ÿ��Ʋ�� ����
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
				url_profileBackground=this.getClass().getResource("/"+file.getName()+""); //�����ʻ���
				kakaoMain.memberList.get(0).setProfile_Back_Img(file.getName());
				can_north_img.repaint();
			}
		}else if(obj==bt_chat){
			//ChatMain chat=new ChatMain(kakaoMain);
			//ê������ ���⼭ ������. (���⼭ ��ȭ�� ����)
			chat();
			
			
			//System.out.println(kakaoMain.loginEmail + kakaoMain.memberList.get(index).getE_mail());
			//�α��� �� ����� �̸���  && ä�� ������� �̸���(���ȣ ��ȸ�� ���)
			
			
		}
		
	}
	
	public void chat(){
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql="select roomNumber from Chats where e_mail=?";
		boolean findChat=true; //ä�ù��� ã���� ��ã������ �÷��װ�.
		try {
			pstmt = kakaoMain.con.prepareStatement(sql);
			pstmt.setString(1, kakaoMain.loginEmail);
			rs = pstmt.executeQuery();
			
			roomNumberArray.removeAll(roomNumberArray);
			System.out.println(roomNumberArray.size() + "�����ʿ��� ��ѹ� ������");
			
			while(rs.next()){
				roomNumberArray.add(rs.getInt("roomNumber"));
			}
			if(roomNumberArray.size()==0){
				//�ƹ��͵� ������ ������
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
				chat.setVisible(true);//ȭ�� ��ü
				
				kakaoMain.chat.add(chat);
				System.out.println("���λ���");
				System.out.println("���� ���� �� ������ ê �������?"  +kakaoMain.chat.size());
				
			}else{
				//��ȸ�ʿ�
				
				
				//1. ���̸��Ϸ� ������ �� ��ѹ��� ��ȸ.
				//2. ��ȭ�� ���� ���� ��ѹ��� �ִ��� �˻�
				//    2-1) �ִٸ�
				//			- �׹��� visible(true)
				//	  2-2) ���ٸ�
				//			-- chatMain �� ���� ����.
				
				
				sql="select roomnumber from chats where e_mail=?";
				pstmt = kakaoMain.con.prepareStatement(sql);
				pstmt.setString(1, kakaoMain.loginEmail);
				rs = pstmt.executeQuery();
				
				System.out.println("��ѹ� ��������???");
				Vector myRoomNum = new Vector<>();
				
				while(rs.next()){
					int room = rs.getInt("roomnumber");
					//myRoomNum.add(rs.getInt("roomnumber"));
					System.out.println("�������� ���� ��ȣ��? " + room);
					myRoomNum.add(room);
					
				}
				
				System.out.println("��ѹ��� �������????? " + myRoomNum.size());
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
									System.out.println("��������");
									//������ 0�� �ƴϰ� ���� ��ȭ�ϰ� ���� ����̶� �̹� ��ȭâ�� ��������� & �� ��ȭâ�� �����ش�.
									kakaoMain.chat.get(j).setVisible(true);
									kakaoMain.chat.get(j).setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
									findChat=false;
								}
							}
						}
					}
				}
				
				if(findChat){ //�������� 0�� �ƴѵ� ���� ��ȭ�ϰ��������̶� ��ȭâ�� ������!! ��ȭâ�� ���� ����
					System.out.println("�� ���µ� ������??????" );
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
					chat.setVisible(true);//ȭ�� ��ü
					kakaoMain.chat.add(chat);
					
				}
/*				
				for(int i=0; i<kakaoMain.chat.size();i++){
					System.out.println(kakaoMain.loginEmail +kakaoMain.chat.get(i).loginEmail );
					System.out.println(kakaoMain.memberList.get(index).getE_mail()+(kakaoMain.chat.get(i).yourEmail));
					if(kakaoMain.loginEmail.equals(kakaoMain.chat.get(i).loginEmail) && kakaoMain.memberList.get(index).getE_mail().equals(kakaoMain.chat.get(i).yourEmail)){
						System.out.println("��������");
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
