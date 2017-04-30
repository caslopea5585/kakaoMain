package chatting;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import main.KakaoMain;
import util.MyRoundButton;

public class ChatRoomPanel extends JPanel{
	Canvas can=null;
	BufferedImage image=null; //채팅 사진
	BufferedImage bgimage=null; //채팅 사진 원형처리 위한 이미지
	URL url=null;
	URL bgurl=null;

	JPanel p_info,p_infoCenter,p_infoWest,p_magin,p_maginWest; //이름, 마지막 채팅 묶을 그리드 패널
	JLabel la_namelist,la_Msg,la_time;
	
	String photoPath;
	String name;
	String statusMsg;
	String time;
	KakaoMain main;
	int roomNumber=0;
	JPanel p_button;
	
	public ChatRoomPanel(String photoPath, String name, String statusMsg,KakaoMain main,int roomNumber){
		this.photoPath=photoPath;
		this.name=name;
		this.statusMsg=statusMsg;
		this.main=main;
		this.roomNumber=roomNumber;
		
		p_magin=new JPanel();
		p_maginWest=new JPanel();
		p_info=new JPanel();
		p_infoCenter=new JPanel();
		p_infoWest=new JPanel();
		p_button=new JPanel();
		//p_name=new JPanel();
		//p_statusMsg=new JPanel();
		la_namelist=new JLabel(name);
		la_Msg=new JLabel(statusMsg);
		la_time=new JLabel("");
		
		setLayout(new BorderLayout());
		
		p_magin.setLayout(new BorderLayout());
		p_magin.setBackground(Color.white);
		
		p_maginWest.setBackground(Color.WHITE);
		p_maginWest.setPreferredSize(new Dimension(10, 60));
		
		p_info.setLayout(new BorderLayout());
		p_info.setBackground(Color.WHITE);
		
		p_infoCenter.setLayout(new BorderLayout());
		p_infoCenter.setBackground(Color.WHITE);
		p_infoCenter.setPreferredSize(new Dimension(60, 30));
		
		p_infoWest.setLayout(new BorderLayout());
		p_infoWest.setBackground(Color.WHITE);
		p_infoWest.setPreferredSize(new Dimension(210, 30));
		
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

		p_infoWest.add(la_Msg);
		p_infoCenter.add(la_time);
		p_info.add(la_namelist,BorderLayout.NORTH);
		p_info.add(p_infoCenter);
		p_infoCenter.add(p_infoWest,BorderLayout.WEST);
		
		p_magin.add(p_info);
		p_magin.add(p_maginWest,BorderLayout.WEST);
		

		add(can, BorderLayout.WEST);
		add(p_magin);
		
		
		//채팅방으로 마우스 리스너 연결
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("myroomnum is "+roomNumber);
				if(main.chat.size()>roomNumber){//방이 존재하는지
					main.chat.get(roomNumber).setLocation(main.getLocation().x+360,main.getLocation().y);
					main.chat.get(roomNumber).setVisible(true);
				}
			}
		});
		
		setPreferredSize(new Dimension(360, 60));
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setBackground(Color.WHITE);
	}
	public void setMsgAndTime(String t, String msg){
		la_time.setText(t);
		la_Msg.setText(msg);
	}
	
	public void addButton(MyRoundButton button){
		p_button.setLayout(new FlowLayout(FlowLayout.LEADING));
		p_button.setBackground(Color.white);
		p_button.setPreferredSize(new Dimension(50, 50));
		p_button.add(button, "span 2 2");
		add(p_button, BorderLayout.WEST);
	}
}
