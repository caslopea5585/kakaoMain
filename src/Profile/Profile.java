package Profile;

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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import merge_sh_yk.KakaoMain;
import merge_sh_yk.Member;

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
	Member memberList;
	
	KakaoMain kakaoMain;
	
	public Profile(String photopath,KakaoMain kakaoMain) {
		
		profile = this;
		this.kakaoMain=kakaoMain;
		

		layeredPane = new JLayeredPane();
		url_profileBackground = this.getClass().getResource("/bg_north.png");	//상단배경
		url_profileSouth=this.getClass().getResource("/bg_south.png");	//하단
		url_profileImage=this.getClass().getResource(photopath); //프로필사진
		
		
		try {
			buffr_south = ImageIO.read(url_profileSouth);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
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
		        
		        g.drawImage(buffr_north, 0, 0, 300,250,this);  //백그라운드이미지
		        
		        g.setFont(new Font("돋움", Font.PLAIN, 25));
		        g.setColor(Color.BLACK);
		        
		        g.drawString(status_msg, 85, 100);						//상태메시지
		        
		        
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
		        g2.draw(circle);
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
		can_north_img.setBounds(0, 0, 300, 300);
		la_name.setBounds(115, 280, 70, 50);
		bt_chat.setBounds(65, 340, 50, 50);
		bt_manager.setBounds(165, 340, 50, 50);
		la_chat.setBounds(65, 390, 70, 30);
		la_manager.setBounds(165, 390, 70, 30);
		bt_back_profile.setBounds(245, 250, 45, 45);
				
		
		layeredPane.add(can_north_img, 1);
		layeredPane.add(la_name, 2,1);
		layeredPane.add(bt_chat, 3,2);
		layeredPane.add(bt_manager, 4,3);
		layeredPane.add(la_chat, 4,3);
		layeredPane.add(la_manager, 4,3);
		layeredPane.add(bt_back_profile, 4,3);
		
		
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
				System.out.println("mouseDragged2");
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
		}
		
	}
	

/*
	public static void main(String[] args) {
		new Profile();
	}
	*/
}
