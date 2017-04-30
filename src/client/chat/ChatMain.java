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
	JPanel p_north;//���ʿ� �ٿ��� �г�
	JPanel p_center; //��� �ٿ��� �г�
	JPanel p_south;////���ʿ� �ٿ��� �г�
	JPanel p_chat;//ä�ù� ���� �г�
	JPanel p_bt; //��ư ���� �г�
	public MyModel model = new MyModel();//���̺� ��
	
	//���� ����
	MyRoundButton bt_profil;//���� �г� ������ ������ ����
	JLabel la_user; //���� �г� ������ ä�ù��� ��ȭ���
	JButton bt_list, bt_totalview, bt_serch;//���� �г� ������ �Խ���, ��ƺ���, �˻����
	JComboBox cmb;// �ӽ����� ��� ����â
	JButton bt_option; //���� �г��� ���ʿ� ������ư
	
	//���Ϳ���
	JScrollPane scroll;//��� �ٿ��� ��ũ��
	public JTable table; //��� �� ���̺�
	JButton bt_imo; //�̸�Ƽ�� ��ư
	JButton bt_file; //�������� ��ư
	JButton bt_img;//�̹��� ���۹�ư	
	
	//���ʿ���
	JButton bt_send;
	JTextPane area;
	JFileChooser chooser;
	
	KakaoMain main;
	//Chat chatDto;
	public String myId;
	public String yourId;
	Connection con;
	int roomNumber =0; //���̸���, ���� �̸����� �������� ������ �ִ� ���ȣ�� �����ϴ� ����
	Vector<String> chatMember ;
	public String yourPhotoPath;
	
	boolean act=false;
	boolean act_ready=false;//����/������ ���µ� ��밡 ������ ���� �غ� �Ǿ��ִ°�
	
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
				yourPhoto.add(rs.getString("profile_img"));	//�� ��ȭ�濡 �ִ� �����(������)�� �̹������ �߰�
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
			
			System.out.println("���� ���� ��δ�?"+yourPhoto.get(0));			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void initGUI() {
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		p_chat = new JPanel();
		
		
		
		//���ʿ����� �г�
		
		la_user = new JLabel(yourId + " �԰� ��ȭ���Դϴ�.");
		bt_list = new JButton("�Խ���");
		bt_totalview = new JButton("��ƺ���");
		bt_serch = new JButton("�˻�");
		bt_option = new JButton("����");
		chooser=new JFileChooser();
		
		
		la_user.setFont(new Font("����", Font.BOLD, 15));
		p_north.setLayout(new FlowLayout(FlowLayout.LEADING));
		p_north.setBackground(new Color(160, 192, 215));
		p_north.setPreferredSize(new Dimension(500, 80));
		p_north.add(bt_profil, "span 2 2"); //���� ������
		p_north.add(la_user, "span 3,wrap"); //��ȭ�� �������
		//p_north.add(bt_list); //�Խ���
		//p_north.add(bt_totalview); //��ƺ���
		//p_north.add(bt_serch); //�˻�
		
		//p_north.add(bt_option,"gapleft 50"); //����	
		add(p_north, BorderLayout.NORTH);
		
		//���Ϳ���
		table = new JTable();
		scroll = new JScrollPane();
		bt_send = new JButton("����");
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
		//��ũ�� �ڵ����� ����������
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
		
		//���ʿ���
		bt_imo = new JButton("�̸�Ƽ��");
		bt_file = new JButton("��������");
		bt_img = new JButton("�̹�������");
		
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
				System.out.println("������");
			}
		});
		
		//�ؽ�Ʈ area�� ���� ����Ű�� ������
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
						//Ȱ��ȭ�Ǿ� ������ �˸�
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
	//textpane ������ �޼����� ������!!
	public void sendsMsg(){
		
		
		String msg = area.getText().trim();
		myId = myId;
		yourId = yourId;
		
		LoginPanel log = (LoginPanel)main.panel[0];
		log.ct.sendMsg(msg, myId, yourId,chatMember);
		
		
		
		
		area.setText("");
		
	}

	public void sendAct(){//Ȱ��ȭ���� �˸��� �޼���
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
				//image ������ �Ѱ��ֱ�
				LoginPanel log=(LoginPanel)main.panel[0];
				log.ct.sendFile(file);
			}
		}
		
	}

}
