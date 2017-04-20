package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;

import Profile.Profile;
import db.DBManager;

public class PersonPanel extends JPanel{
	Canvas can=null;
	BufferedImage image=null; //프로필 사진
	BufferedImage bgimage=null; //프로필 사진 원형처리 위한 이미지
	URL url=null;
	URL bgurl=null;

	JPanel p_info; //이름, 상메 묶을 그리드 패널
	//JPanel p_name, p_statusMsg; 
	JLabel la_name, la_statusMsg;
	
	String photoPath;
	String name;
	String statusMsg;
	
	AddFriend pop; //내 프로필 변경을 위한 임시창.
	DBManager manager;
	Connection con;
	ArrayList<Member> memberList = new ArrayList<Member>();
	KakaoMain kakaoMain;
	
	
	public PersonPanel(KakaoMain kakaoMain, String photoPath, String name, String statusMsg){
		this.kakaoMain=kakaoMain;
		this.photoPath=photoPath;
		this.name=name;
		this.statusMsg=statusMsg;

		
		p_info=new JPanel();
		la_name=new JLabel(name);
		la_statusMsg=new JLabel(statusMsg);
		
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
				g.drawImage(image, 0, 0, 50,50, this);
				g.drawImage(bgimage, 0, 0, 50,50, this);
			}
		};
		
		can.setPreferredSize(new Dimension(50,50));
		
		//p_name.add(la_name);
		//p_statusMsg.add(la_statusMsg);
		
		p_info.add(la_name);
		p_info.add(la_statusMsg);
		
		add(can, BorderLayout.WEST);
		add(p_info);
		
		
		//사진에 마우스 리스너 연결
		can.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("사진클릭");
				//pop=new ChangeProfile();
				Profile profile=new Profile(photoPath,kakaoMain); //"/ryan1.png"
			}
		});
		
		setPreferredSize(new Dimension(360, 60));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setBackground(Color.WHITE);
	}
	
	
	
	
	public void getFriendList(){
		manager=DBManager.getInstance();
		con=manager.getConnection();
		String sql="select * from member";
		PreparedStatement pstmt;
		ResultSet rs =null;
		
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Member memberListDto = new Member();
				memberListDto.setE_mail(rs.getString("e_mail"));
				memberListDto.setNik_id(rs.getString("nik_id"));
				memberListDto.setPassword(rs.getString("password"));
				memberListDto.setProfile_img(rs.getString("profile_img"));
				memberListDto.setProfileBackImg(rs.getString("profilebackimg"));
				memberListDto.setStatus_msg(rs.getString("status_msg"));
				
				memberList.add(memberListDto);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(memberList.size()+" 사이즈");
		System.out.println(memberList.get(0).getE_mail()+" 있는 값");
	}

}
