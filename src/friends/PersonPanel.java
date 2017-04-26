package friends;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

<<<<<<< HEAD

import profile.Profile;
import util.Util;
=======
import Profile.Profile;
import client.chat.ChatMain;
>>>>>>> 909311fda51f31ca484f8756fba4587896396e15
import db.DBManager;
import main.KakaoMain;
import main.MemberList;

public class PersonPanel extends JPanel {
	Canvas can=null;
	BufferedImage image=null; //프로필 사진
	BufferedImage bgimage=null; //프로필 사진 원형처리 위한 이미지
	URL url=null;
	URL bgurl=null;

	JPanel p_info, p_status; //이름, 상메 묶을 그리드 패널
	//JPanel p_name, p_statusMsg; 
	JLabel la_name, la_statusMsg;
	JButton bt_statusMsg;
	
	String photoPath;
	String name;
	String statusMsg;
	
	AddFriend pop; //내 프로필 변경을 위한 임시창.
	DBManager manager;
	Connection con;
	ArrayList<MemberList> memberList = new ArrayList<MemberList>();
	KakaoMain kakaoMain;
	boolean flag=false;
	Profile profile;
	
	public PersonPanel(KakaoMain kakaoMain, boolean flag,String photoPath, String name, String statusMsg){
		this.kakaoMain=kakaoMain;
		this.photoPath=photoPath;
		this.name=name;
		this.statusMsg=statusMsg;
		this.flag = flag; //나이면 false, 나 아니면 true
		
		//p_img=new JPanel();
		p_info=new JPanel();
		p_status=new JPanel();
		p_status.setBackground(Color.WHITE);
		p_status.setLayout(new FlowLayout(FlowLayout.LEFT));
		la_name=new JLabel(name);
		la_statusMsg=new JLabel(statusMsg);
		//bt_statusMsg=new JButton(statusMsg);
		
		//p_info.setPreferredSize(new Dimension(310, 50));
		la_name.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		//la_name.setPreferredSize(new Dimension(310, 20));
		
		la_statusMsg.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		la_statusMsg.setBorder(new RoundedBorder(10));
		//bt_statusMsg.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		//p_status.add(bt_statusMsg);
		
		//LineBorder line = new LineBorder
		//la_statusMsg.setBorder(line);
		
		setLayout(new BorderLayout());
		
		p_info.setLayout(new GridLayout(2, 1));
		p_info.setBackground(Color.WHITE);
		url=this.getClass().getResource(photoPath); //"/jeju2.jpg"
		bgurl=this.getClass().getResource("/emptyCircle.png");
		
		try {
			image=ImageIO.read(url);
			bgimage=ImageIO.read(bgurl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		can=new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 60,60, this);
				g.drawImage(bgimage, 0, 0, 60,60, this);
			}
		};
		
		can.setPreferredSize(new Dimension(60,60));
		p_status.add(la_statusMsg);
		
		p_info.add(la_name);
		p_info.add(p_status);

		//p_info.add(la_statusMsg);
		
		//p_img.setPreferredSize(new Dimension(50, 50));
		//p_img.add(can);
		//p_info.add(bt_statusMsg);
		//p_info.add(p_status);
		add(can, BorderLayout.WEST);

		add(p_info, BorderLayout.CENTER);

		
		/*bt_statusMsg.setBorderPainted(true); //버튼 경계선 없애기
		bt_statusMsg.setBorder(new RoundedBorder(10));
		bt_statusMsg.setContentAreaFilled(false); //색 채우기(파란색) 없애기
		bt_statusMsg.setFocusPainted(false); //포커스에 의한 경계 없애기
		bt_statusMsg.setOpaque(false); //투명도-투명해야되니까 false(불투명-true)
		*/
		la_statusMsg.setBorder(new RoundedBorder(10));
		this.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				ChatMain chat = new ChatMain(kakaoMain);
				chat.setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
				chat.setVisible(true);//화면 교체
				
				kakaoMain.chat.add(chat);
			}
		});

		/*setPreferredSize(new Dimension(340, 60));
		setBorder(BorderFactory.createEmptyBorder(5,15,5,5));
*/
		setPreferredSize(new Dimension(360, 60));
		//setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setBackground(Color.WHITE);
		
		System.out.println("퍼슨 패널 생성 완료.");
	}
	
	public void getFriendList(){
		manager=DBManager.getInstance();
		con=manager.getConnection();
		String sql="select * from members";
		PreparedStatement pstmt;
		ResultSet rs =null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				MemberList memberListDto = new MemberList();
				memberListDto.setE_mail(rs.getString("e_mail"));
				memberListDto.setNik_id(rs.getString("nik_id"));
				memberListDto.setPassword(rs.getString("password"));
				memberListDto.setProfile_img(rs.getString("profile_img"));
				memberListDto.setProfile_Back_Img(rs.getString("profilebackimg"));
				memberListDto.setStatus_msg(rs.getString("status_msg"));
				
				memberList.add(memberListDto);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(memberList.size()+" 사이즈");
		System.out.println(memberList.get(0).getE_mail()+" 있는 값");
	}
	
	private static class RoundedBorder implements Border {
	    private int radius;

	    RoundedBorder(int radius) {
	        this.radius = radius;
	    }
	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius-7, this.radius+1, this.radius-7, this.radius);
	    }
	    public boolean isBorderOpaque() {
	        return true;
	    }
	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	    }
	}

}
