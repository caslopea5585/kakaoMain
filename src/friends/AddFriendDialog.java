package friends;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBManager;
import main.KakaoMain;
import main.MemberList;
import profile.Profile;
import util.HintedTextField;

public class AddFriendDialog extends JDialog{
	Point mouseDownCompCoords = null;
	
	JPanel p_north, p_center, p_search;
	JLabel la_add, la_des;
	JButton bt_close;
	JTextField t_search;
	
	Connection con;
	DBManager manager;
	KakaoMain kakaoMain;
	//Vector<MemberList> member= new Vector<MemberList>(); //dto
	
	
	//email검색으로 찾은 친구 프로필사진과 닉네임 보여주기 위한 객체들
	Canvas can=null;
	BufferedImage image=null; //프로필 사진
	BufferedImage bgimage=null; //프로필 사진 원형처리 위한 이미지
	URL url=null;
	URL bgurl=null;
	
	
	JPanel p_friend, p_img, p_add, p_margin;
	JButton bt_add;
	Friends friend;
	
	MemberList memberList;
	PersonPanel personPanel;
	
	JLabel la_nick, la_status;

	Profile profile;
	int q=0;

	public AddFriendDialog(Connection con, KakaoMain kakaoMain){
		getRootPane().setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY) );
		this.kakaoMain=kakaoMain;
		this.con=con;
		 manager=DBManager.getInstance();
	      con=manager.getConnection();
	      
		setLayout(new BorderLayout());
		//getContentPane().setBackground(Color.WHITE);
		
		p_north=new JPanel(); //border
		p_center=new JPanel(); //border
		p_search=new JPanel();
		
		//p_friend.setLayout(new BorderLayout());
		
		p_north.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);
		
		
		p_north.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		
		la_add=new JLabel("친구 추가", JLabel.CENTER);
		
		la_nick=new JLabel("a", JLabel.CENTER);
		la_status=new JLabel("b", JLabel.CENTER);
		la_status.setFont(new Font("돋움",Font.PLAIN,15));
		la_nick.setPreferredSize(new Dimension(240, 25));
		la_status.setPreferredSize(new Dimension(240, 20));
		//t_search=new HintTextField_FIRST("email 검색");

		t_search=new HintedTextField("email 검색");
		t_search.setPreferredSize(new Dimension(250, 25));
		t_search.setBorder(BorderFactory.createLineBorder(new Color(30,170,170)));
		
		la_des=new JLabel("이메일로 친구를 추가하세요", JLabel.CENTER);
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
		
		setUndecorated(true);
		setSize(300,320);
        setModal(true);
        setVisible(true);
	}
	
	//member데이터 가져와서 찾는 친구가 있는지 여부 검사
	public void getDB(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from members where e_mail=?";
		String input_id=t_search.getText();

		String ori_nik=null;	
		String ori_email="";
		//String ori_pw="";
		String ori_img="";
		//String ori_bgimg="";
		//String ori_status="";
		
		bt_add=new JButton("친구 추가");
		
		p_friend=new JPanel(); //친구 이미지,닉네임 붙일 패널
		p_friend.setBackground(Color.WHITE);
		p_friend.setLayout(new BorderLayout());
		
		//p_margin=new JPanel();
		//p_margin.setPreferredSize(new Dimension(230, 100));
		p_img=new JPanel();
		p_img.setBackground(Color.WHITE);
		p_img.setPreferredSize(new Dimension(250, 200));
		p_add=new JPanel();
		p_add.setBackground(Color.WHITE);
		p_add.add(bt_add);
		
		
		
		//p_friend.add(p_margin);
		p_friend.add(p_img);
		p_friend.add(p_add, BorderLayout.SOUTH);
		
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, input_id);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				 memberList=new MemberList();
				memberList.setE_mail(rs.getString("e_mail"));
				memberList.setNik_id(rs.getString("nik_id"));
				//member.setPassword(rs.getString("password"));
				memberList.setProfile_img(rs.getString("profile_img"));
				//member.setProfileBackImg(rs.getString("profilebackimg"));
				memberList.setStatus_msg(rs.getString("status_msg"));
				showFriend(memberList,p_friend);
				
			}else{
				la_des.setVisible(true);
				p_friend.setVisible(false);
				p_add.setVisible(false);
				bt_add.setVisible(false);
				bt_add.setEnabled(false);
				la_des.setText("\'"+t_search.getText()+"\'"+"를 찾을 수 없습니다.");
			}

			
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
	public void showFriend(MemberList member, JPanel p_friend){
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
				g.drawImage(image, 0, 20, 100,100, this);
				g.drawImage(bgimage, 0, 20, 100,100, this);
			}
		};
		//can.repaint();
		
		
		can.setPreferredSize(new Dimension(120,120));
		p_img.add(can);
		//System.out.println(member.getNik_id());
		la_nick.setText(member.getNik_id());
		p_img.add(la_nick);
		//System.out.println(member.getStatus_msg());
		la_status.setText(member.getStatus_msg());
		p_img.add(la_status);
		//p_add.add(bt_add);
		//p_friend.add(bt_add);
		la_des.setVisible(false);
		bt_add.setVisible(true);
		bt_add.setEnabled(true);
		p_center.add(p_friend);
		p_friend.setVisible(true);
		p_friend.updateUI();
		System.out.println(member.getNik_id());
		
		bt_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj=e.getSource();
				if(obj==bt_add){
					validateFriend();
						
				}
			}
		});
	}

	public void validateFriend(){
		int friendsSize = kakaoMain.friendsList.size();
		String getText = t_search.getText();
		boolean flag =true;
		if(kakaoMain.friendsList.size()!=0){
			if(t_search.getText().equals(kakaoMain.loginEmail)){
				JOptionPane.showMessageDialog(this, "자신을 친구등록할 수 없습니다.");
				flag=!flag;
			}
			
			for(int i=0;i<friendsSize;i++){
				if(t_search.getText().equals(kakaoMain.friendsList.get(i).getYour_email())){
					JOptionPane.showMessageDialog(this, "이미등록된 친구입니다.");
					flag=!flag;
				}
			}
			
			if(flag){
				addFriend();
			}
		}else if(kakaoMain.friendsList.size()==0){
			 
			 if(t_search.getText().equals(kakaoMain.loginEmail )){
				JOptionPane.showMessageDialog(this, "자신의 친구등록할 수 없습니다.");
			}else{
				addFriend();
				JOptionPane.showMessageDialog(this, " 첫 친구등록완료");
				
			}
		}
	}
	
	public void addFriend(){
		String sql="insert into friends(e_mail, your_email) values("+"\'"+kakaoMain.loginEmail+"\'"+","+"\'"+t_search.getText()+"\'"+")";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			friend=new Friends();
			friend.setE_mail(kakaoMain.memberList.get(0).getE_mail()); //내 이메일
			friend.setYour_email(t_search.getText());
			kakaoMain.friendsList.add(friend);
			
			for(int i=0;i<kakaoMain.memberList.size();i++){
				if(kakaoMain.memberList.get(i).getE_mail().equals(t_search.getText())){
					q=i;
					System.out.println("추가 전 mYFriend 의 사이즈는?" + kakaoMain.myFriends.size());
					kakaoMain.myFriends.add(personPanel=new PersonPanel(kakaoMain,true,kakaoMain.memberList.get(i).getProfile_img(), kakaoMain.memberList.get(i).getNik_id(),  kakaoMain.memberList.get(i).getStatus_msg() ));
					kakaoMain.myFriends.get(kakaoMain.myFriends.size()-1).can.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							Object obj = e.getSource();
							for(int i=0;i<kakaoMain.myFriends.size();i++){
								if(obj==kakaoMain.myFriends.get(i).can){
									//프렌즈에 있는 i의 이메일가지고 멤버리스트의 아이가 일치하는 것을 찾아서 그것의 이미지를 불러와야 한다.
									for(int j=0;j<kakaoMain.memberList.size();j++){
										if(kakaoMain.myFriends.get(i).name.equals(kakaoMain.memberList.get(j).getNik_id())){
											profile=new Profile(kakaoMain.memberList.get(j).getProfile_img(),kakaoMain,false,j);
										}
									}
								}
							}
						}
						
					});
					/*personPanel.can.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							profile=new Profile(kakaoMain.memberList.get(q).getProfile_img(),kakaoMain,true); //f
						}
					});*/
					kakaoMain.p_list.add(kakaoMain.myFriends.get(kakaoMain.myFriends.size()-1 ));

				}
			}
			
			kakaoMain.friendsListPanel.updateUI();
			kakaoMain.p_center.updateUI();
			kakaoMain.friends_count=kakaoMain.friendsList.size();
			kakaoMain.la_friends.setText("    친구   "+Integer.toString(kakaoMain.friends_count));
			kakaoMain.la_friends.updateUI();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	//다이얼로그 위치
	public void point(JDialog dialog){
		PointerInfo pointerInfo=MouseInfo.getPointerInfo();
		pointerInfo.getLocation();
		Dimension my=dialog.getSize();
		dialog.setLocation(pointerInfo.getLocation().x-my.width/2, pointerInfo.getLocation().y-my.height/2);
	}
	//버튼 설정
	public void setButton(JButton bt){
		bt.setBorderPainted(false);
		bt.setContentAreaFilled(false);
		bt.setFocusPainted(false);
		bt.setOpaque(false);
	}
	
	//드래그 설정
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
