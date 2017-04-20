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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Profile.Profile;

public class ChatRoomPanel extends JPanel{
	Canvas can=null;
	BufferedImage image=null; //채팅 사진
	BufferedImage bgimage=null; //채팅 사진 원형처리 위한 이미지
	URL url=null;
	URL bgurl=null;

	JPanel p_info; //이름, 마지막 채팅 묶을 그리드 패널
	JLabel la_namelist,la_Msg;
	
	String photoPath;
	String name;
	String statusMsg;
	KakaoMain main;
	
	public ChatRoomPanel(String photoPath, String name, String statusMsg,KakaoMain main){
		this.photoPath=photoPath;
		this.name=name;
		this.statusMsg=statusMsg;
		this.main=main;

		p_info=new JPanel();
		//p_name=new JPanel();
		//p_statusMsg=new JPanel();
		la_namelist=new JLabel(name);
		la_Msg=new JLabel(statusMsg);
		
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
		
		p_info.add(la_namelist);
		p_info.add(la_Msg);
		
		add(can, BorderLayout.WEST);
		add(p_info);
		
		
		//채팅방으로 마우스 리스너 연결
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				main.chat.setLocation(main.getLocation().x+360,main.getLocation().y);
				main.chat.setVisible(true);//화면 교체
			}
		});
		
		setPreferredSize(new Dimension(360, 60));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setBackground(Color.WHITE);
	}
}
