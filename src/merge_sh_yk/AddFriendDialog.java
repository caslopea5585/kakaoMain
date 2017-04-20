package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBManager;

public class AddFriendDialog extends JDialog{
	Point mouseDownCompCoords = null;
	
	JPanel p_north, p_center, p_search, p_friend;
	JLabel la_add, la_des;
	JButton bt_close;
	JTextField t_search;
	
	Connection con;
	DBManager manager;
	
	//Vector<MemberList> member= new Vector<MemberList>(); //dto
	MemberList member;
	
	//email�˻����� ã�� ģ�� �����ʻ����� �г��� �����ֱ� ���� ��ü��
	Canvas can=null;
	BufferedImage image=null; //������ ����
	BufferedImage bgimage=null; //������ ���� ����ó�� ���� �̹���
	URL url=null;
	URL bgurl=null;
	
	public AddFriendDialog(Connection con){
		getRootPane().setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY) );
		
		this.con=con;
		 manager=DBManager.getInstance();
	      con=manager.getConnection();
	      
		setLayout(new BorderLayout());
		//getContentPane().setBackground(Color.WHITE);
		
		p_north=new JPanel(); //border
		p_center=new JPanel(); //border
		p_search=new JPanel();
		p_friend=new JPanel(); //ģ�� �̹���,�г��� ���� �г�
		
		p_north.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);
		p_friend.setBackground(Color.WHITE);
		
		p_north.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		
		la_add=new JLabel("ģ�� �߰�", JLabel.CENTER);

		t_search=new HintTextField_FIRST("email �˻�");
		t_search.setPreferredSize(new Dimension(250, 25));
		t_search.setBorder(BorderFactory.createLineBorder(new Color(30,170,170)));
		
		la_des=new JLabel("�̸��Ϸ� ģ���� �߰��ϼ���", JLabel.CENTER);
        la_des.setForeground(new Color(30,170,170));
        
		bt_close=new JButton("X");
		
		p_north.add(la_add);
		p_north.add(bt_close, BorderLayout.EAST);
		
		p_search.add(t_search);
		p_center.add(p_search, BorderLayout.NORTH);
		
		p_center.add(la_des);

		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		bt_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj=e.getSource();
				if(obj==bt_close){
					dispose();
				}
			}
		});
		
		t_search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					getDB();
				}
			}
		});
		
		dragMouse(p_north);
		point(this);
		setButton(bt_close);
		
		//setUndecorated(true);
		setSize(300,350);
        setModal(true);
        setVisible(true);
	}
	
	//member������ �����ͼ� ã�� ģ���� �ִ��� ���� �˻�
	public void getDB(){
		
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from members where e_mail=?";
		String input_id=t_search.getText();

		String ori_nik=null;

		
		//String ori_nik=null;
	
		String ori_email="";
		
		//String ori_pw="";
		String ori_img="";
		//String ori_bgimg="";
		//String ori_status="";

		//System.out.println(input_id);
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, input_id);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				member=new MemberList();
				member.setE_mail(rs.getString("e_mail"));
				member.setNik_id(rs.getString("nik_id"));
				member.setPassword(rs.getString("password"));
				member.setProfile_img(rs.getString("profile_img"));
				member.setProfile_Back_Img(rs.getString("profilebackimg"));
				member.setStatus_msg(rs.getString("status_msg"));
			}
			if(member!=null){
				ori_email=member.getE_mail();
				ori_nik=member.getNik_id();
				ori_img=member.getProfile_img();
			}else{
				ori_email=null;
			}
			
			if(input_id.equalsIgnoreCase(ori_email)){
				//JOptionPane.showMessageDialog(this, "ģ���� �ֽ��ϴ�");
				showFriend();
			}else{
				//JOptionPane.showMessageDialog(this, "ģ���� �����ϴ�");
				la_des.setText("\'"+t_search.getText()+"\'"+"�� ã�� �� �����ϴ�.");
			}
			member=null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
	         if(rs!=null){
	             try {
	                rs.close();
	             } catch (SQLException e) {
	                e.printStackTrace();
	             }
	          }
	          
	          if(pstmt!=null){
	             try {
	                pstmt.close();
	             } catch (SQLException e) {
	                e.printStackTrace();
	             }
	         }
		}
	}
	public void showFriend(){
		url=this.getClass().getResource(member.getProfile_img());
		bgurl=this.getClass().getResource("/emptyCircle.png");
		
		try {
			image=ImageIO.read(url);
			bgimage=ImageIO.read(bgurl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		can=new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 100,100, this);
				g.drawImage(bgimage, 0, 0, 100,100, this);
			}
		};
		
		can.setPreferredSize(new Dimension(100,100));
		p_friend.add(can);
		la_des.setVisible(false);
		p_center.add(p_friend);

	}
	
	//���̾�α� ��ġ
	public void point(JDialog dialog){
		PointerInfo pointerInfo=MouseInfo.getPointerInfo();
		pointerInfo.getLocation();
		Dimension my=dialog.getSize();
		dialog.setLocation(pointerInfo.getLocation().x-my.width/2, pointerInfo.getLocation().y-my.height/2);
	}
	//��ư ����
	public void setButton(JButton bt){
		bt.setBorderPainted(false);
		bt.setContentAreaFilled(false);
		bt.setFocusPainted(false);
		bt.setOpaque(false);
	}
	
	//�巡�� ����
	public void dragMouse(JPanel panel){
		panel.addMouseListener(new MouseAdapter() {		
			public void mouseReleased(MouseEvent e) {
				mouseDownCompCoords = null;
			}
			public void mousePressed(MouseEvent e) {
				mouseDownCompCoords = e.getPoint();
			}
		});
		panel.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point currCoords = e.getLocationOnScreen();
		        setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
			}
		});
	}
}
