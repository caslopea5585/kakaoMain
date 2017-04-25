package client.chat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
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
import net.miginfocom.swing.MigLayout;


public class ChatMain extends JDialog implements ActionListener{
	JPanel p_north;//북쪽에 붙여질 패널
	JPanel p_center; //가운데 붙여질 패널
	JPanel p_south;////남쪽에 붙여질 패널
	public MyModel model = new MyModel();//테이블 모델
	
	//북쪽 영역
	RButton bt_profil;//북쪽 패널 서쪽의 프로필 사진
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
	
	
	
	
	public ChatMain(KakaoMain main) {
		this.main=main;
		initGUI();
	}
	
	public void initGUI() {
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		
		//북쪽영역의 패널
		bt_profil = new RButton(new ImageIcon(this.getClass().getResource("/head2.png")));
		la_user = new JLabel("유저");
		bt_list = new JButton("게시판");
		bt_totalview = new JButton("모아보기");
		bt_serch = new JButton("검색");
		bt_option = new JButton("설정");
		chooser=new JFileChooser();
		
		p_north.setLayout(new MigLayout());
		p_north.setBackground(Color.RED);
		p_north.setPreferredSize(new Dimension(500, 80));
		p_north.add(bt_profil, "span 2 2"); //유저 프로필
		p_north.add(la_user, "span 3,wrap"); //대화방 유저목록
		p_north.add(bt_list); //게시판
		p_north.add(bt_totalview); //모아보기
		p_north.add(bt_serch); //검색
		
		p_north.add(bt_option,"gapleft 50"); //설정	
		add(p_north, BorderLayout.NORTH);
		
		//센터영역
		table = new JTable();
		scroll = new JScrollPane();
		System.out.println("테이블이 만들어지나?");
		
		table.setTableHeader(null);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(260);
		table.getColumnModel().getColumn(0).setCellRenderer(new ChatRenderer(this));
		table.setBackground(Color.PINK);
		table.setOpaque(true);
		table.setShowHorizontalLines(false);
		
		scroll.setViewportView(table);
		scroll.getViewport().setBackground(Color.WHITE);
		
		p_center.setLayout(new BorderLayout());
		p_center.add(scroll, BorderLayout.CENTER);
		add(p_center);
		
		//남쪽영역
		bt_send = new JButton("전송");
		area = new JTextPane();
		bt_imo = new JButton("이모티콘");
		bt_file = new JButton("파일전송");
		bt_img = new JButton("이미지전송");
		
		
		p_south.setLayout(new MigLayout());
		p_south.add(area, "hmin 50px,growx,pushx");
		p_south.add(bt_send, "growy,pushy, wrap");
		p_south.add(bt_imo, "split 3");
		p_south.add(bt_file);
		p_south.add(bt_img);
		p_center.add(p_south, BorderLayout.SOUTH);
		
		setBounds(100, 100, 500, 550);
		setVisible(false);
	
		
		bt_send.addActionListener(this);
		bt_file.addActionListener(this);

		
		//텍스트 area에 내용 엔터키로 보내기
		area.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				int key= e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					sendsMsg();
				}
			}
		});
			
	}
	//textpane 영역의 메세지를 보내자!!
	public void sendsMsg(){
		String msg = area.getText().trim();
		String sender = main.memberList.get(0).getNik_id();
		String time =  getTime();	
		LoginPanel log=(LoginPanel)main.panel[0];
		log.ct.sendMsg(msg,time,sender);
	
		//chatDto=log.ct.chatDto;

		//model.addRow(chatDto);
		area.setText("");

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
