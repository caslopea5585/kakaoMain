package client.chat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import login.LoginPanel;
import main.KakaoMain;
import util.Join3Image;
import util.MyRoundButton;
import util.Util;


public class ChatMain extends JDialog implements ActionListener{
	JPanel p_north;//북쪽에 붙여질 패널
	JPanel p_center; //가운데 붙여질 패널
	JPanel p_south;////남쪽에 붙여질 패널
	JPanel p_chat;//채팅방 붙일 패널
	JPanel p_bt; //버튼 붙일 패널
	public MyModel model = new MyModel();//테이블 모델
	
	//북쪽 영역
	MyRoundButton bt_profil;//북쪽 패널 서쪽의 프로필 사진
	JLabel la_user; //북쪽 패널 센터의 채팅방의 대화상대
	JButton bt_list, bt_totalview, bt_serch;//북쪽 패널 남쪽의 게시판, 모아보기, 검색기능
	JComboBox cmb;// 임시적인 사람 선택창
	JButton bt_option; //북쪽 패널의 동쪽에 설정버튼
	
	//센터영역
	JScrollPane scroll;//가운데 붙여질 스크롤
	public JTable table; //가운데 들어갈 테이블
	JButton bt_imo; //이모티콘 버튼
	JButton bt_file; //파일전송 버튼
	JButton bt_img;//이미지 전송버튼	
	
	//남쪽영역
	JButton bt_send;
	JTextPane area;
	JFileChooser chooser;
	
	KakaoMain main;
	//Chat chatDto;
	public String myId;
	public String yourId;
	Connection con;
	int roomNumber =0; //내이메일, 상대방 이메일이 공통으로 가지고 있는 방번호를 저장하는 변수
	Vector<String> chatMember ;
	public String yourPhotoPath;
	
	boolean act=false;
	boolean act_ready=false;//읽음/안읽음 상태등 상대가 정보를 읽을 준비가 되어있는가
	
	public ChatMain(KakaoMain main,String myId,String yourId,Vector chatMember) {

		this.main=main;
		this.myId=myId;
		this.yourId=yourId;
		
		this.chatMember = chatMember;
		//getRoomNumber();
		
		findYourPhoto();
		initGUI();
	}
	
	public void findYourPhoto(){
		con = main.con;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=null;
		
		sql="select profile_img from members where e_mail=?";

		Vector<String> yourPhoto = new Vector<String>();
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i=0; i<chatMember.size();i++){
				pstmt.setString(1, chatMember.get(i));
				rs = pstmt.executeQuery();
				rs.next();
				yourPhoto.add(rs.getString("profile_img"));	//내 대화방에 있는 모든사람(나포함)의 이미지경로 추가
			}
			
			if(yourPhoto.size()==1){
				yourPhotoPath = yourPhoto.get(0);
				bt_profil = new MyRoundButton(Util.createRoundIcon(yourPhotoPath, 70));
			}else if(yourPhoto.size()==3){
				
				Icon icon = Join3Image.createJoin3Image(yourPhoto.get(0),yourPhoto.get(1),yourPhoto.get(2));
				bt_profil = new MyRoundButton(icon);
			}else{
				yourPhotoPath = yourPhoto.get(0);
				bt_profil = new MyRoundButton(Util.createRoundIcon(yourPhotoPath, 70));
				
			}
			
			System.out.println("너의 사진 경로는?"+yourPhoto.get(0));			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void initGUI() {
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		p_chat = new JPanel();
		
		
		
		//북쪽영역의 패널
		
		la_user = new JLabel(yourId + " 님과 대화중입니다.");
		bt_list = new JButton("게시판");
		bt_totalview = new JButton("모아보기");
		bt_serch = new JButton("검색");
		bt_option = new JButton("설정");
		chooser=new JFileChooser();
		
		
		la_user.setFont(new Font("돋움", Font.BOLD, 15));
		p_north.setLayout(new FlowLayout(FlowLayout.LEADING));
		p_north.setBackground(new Color(160, 192, 215));
		p_north.setPreferredSize(new Dimension(500, 80));
		p_north.add(bt_profil, "span 2 2"); //유저 프로필
		p_north.add(la_user, "span 3,wrap"); //대화방 유저목록
		//p_north.add(bt_list); //게시판
		//p_north.add(bt_totalview); //모아보기
		//p_north.add(bt_serch); //검색
		
		//p_north.add(bt_option,"gapleft 50"); //설정	
		add(p_north, BorderLayout.NORTH);
		
		//센터영역
		table = new JTable();
		scroll = new JScrollPane();
		bt_send = new JButton("전송");
		area = new JTextPane();
		
		table.setTableHeader(null);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(260);
		table.getColumnModel().getColumn(0).setCellRenderer(new ChatRenderer(this));
		table.setBackground(new Color(160, 192, 215));
		table.setOpaque(true);
		table.setShowHorizontalLines(false);
		
		scroll.setViewportView(table);
		scroll.getViewport().setBackground(new Color(160, 192, 215));
		//스크롤 자동으로 내려가게함
		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		    public void adjustmentValueChanged(AdjustmentEvent e) {  
		        e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
		    }
		});
		bt_send.setBackground(new Color(255, 235, 051));
		area.setPreferredSize(new Dimension(250, 50));
		
		p_center.setLayout(new BorderLayout());
		p_center.add(scroll, BorderLayout.CENTER);
		
		p_chat.setBackground(Color.WHITE);
		p_chat.add(area, "hmin 50px,growx,pushx");
		p_chat.add(bt_send, "growy,pushy, wrap");
		p_center.add(p_chat, BorderLayout.SOUTH);
		add(p_center);
		
		//남쪽영역
		bt_imo = new JButton("이모티콘");
		bt_file = new JButton("파일전송");
		bt_img = new JButton("이미지전송");
		
		p_south.setLayout(new FlowLayout(FlowLayout.LEADING));
		p_south.setBackground(Color.WHITE);
		//p_south.add(bt_imo, "split 3");
		//p_south.add(bt_file);
		//p_south.add(bt_img);
		
		add(p_south, BorderLayout.SOUTH);
		
		setBounds(100, 100, 360, 550);
		setVisible(false);
	
		
		bt_send.addActionListener(this);
		bt_file.addActionListener(this);
		bt_profil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("눌렀어");
			}
		});
		
		//텍스트 area에 내용 엔터키로 보내기
		area.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key= e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					sendsMsg();
				}
			}
		});
			
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				if(act_ready){
					if(!act){
						act=true;
						//활성화되어 읽음을 알림
						sendAct();
					}
				}
			}
			public void windowDeactivated(WindowEvent e) {
				if(act_ready){
					if(act){
						act=false;
					}
				}
			}
		});
	}
	//textpane 영역의 메세지를 보내자!!
	public void sendsMsg(){
		
		
		String msg = area.getText().trim();
		myId = myId;
		yourId = yourId;
		
		LoginPanel log = (LoginPanel)main.panel[0];
		log.ct.sendMsg(msg, myId, yourId,chatMember);
		
		
		
		
		area.setText("");
		
	}

	public void sendAct(){//활성화됨을 알리는 메세지
		LoginPanel log=(LoginPanel)main.panel[0];
		log.ct.sendAct(act,main.loginEmail,chatMember);
	}
	
	
	static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return sdf.format(new Date());
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_send){
			sendsMsg();
		}
		else if(obj==bt_file){
			int result=chooser.showOpenDialog(this);
			if(result==JFileChooser.APPROVE_OPTION){
				File file=chooser.getSelectedFile();
				//image 데이터 넘겨주기
				LoginPanel log=(LoginPanel)main.panel[0];
				log.ct.sendFile(file);
			}
		}
		
	}

}
