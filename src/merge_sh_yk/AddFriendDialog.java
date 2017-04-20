package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	
	Member member; //dto
	
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
		p_friend=new JPanel();
		
		p_north.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);
		
		p_north.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		
		la_add=new JLabel("친구 추가", JLabel.CENTER);

		t_search=new HintTextField_FIRST("ID검색");
		t_search.setPreferredSize(new Dimension(250, 25));
		t_search.setBorder(BorderFactory.createLineBorder(new Color(30,170,170)));
		
		la_des=new JLabel("아이디로 친구를 추가하세요", JLabel.CENTER);
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
					System.out.println("엔터");
					getDB();
				}
			}
		});
		
		dragMouse(p_north);
		point(this);
		setButton(bt_close);
		
		setUndecorated(true);
		setSize(300,350);
        setModal(true);
        setVisible(true);
	}
	
	//member데이터 가져와서 찾는 친구가 있는지 여부 검사
	public void getDB(){
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from member where nik_id=?";
		String input_id=t_search.getText();
		String ori_nik=null;
		//System.out.println(input_id);
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, input_id);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				member=new Member();
				member.setName(rs.getString("nik_id"));
			}
			if(member!=null){
				ori_nik=member.getName();
			}else{
				ori_nik=null;
			}
			
			if(input_id.equalsIgnoreCase(ori_nik)){
				JOptionPane.showMessageDialog(this, "친구가 있습니다");
			}else{
				//JOptionPane.showMessageDialog(this, "친구가 없습니다");
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
