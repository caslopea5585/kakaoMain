package friends;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Profile.Profile;
import client.chat.ChatMain;
import db.DBManager;
import main.KakaoMain;
import main.MemberList;

public class PartyChatDialog extends JDialog {
	Point mouseDownCompCoords = null;

	JPanel p_north, p_center, p_south, p_centerup;
	JLabel la_add;
	JButton bt_close,bt_ok;
	
	Connection con;
	DBManager manager;
	KakaoMain kakaoMain;
	// Vector<MemberList> member= new Vector<MemberList>(); //dto

	// email�˻����� ã�� ģ�� �����ʻ����� �г��� �����ֱ� ���� ��ü��
	Canvas can = null;
	BufferedImage image = null; // ������ ����
	BufferedImage bgimage = null; // ������ ���� ����ó�� ���� �̹���
	URL url = null;
	URL bgurl = null;

	JPanel p_friend, p_img, p_add;
	JButton bt_add;
	Friends friend;

	MemberList memberList;
	PersonPanel personPanel;

	//ArrayList<PersonPanel> myFriends = new ArrayList<PersonPanel>();
	public Vector<Friends> friendsList;
	
	Profile profile;
	int q = 0;
	//CheckboxGroup cg = new CheckboxGroup();
	Vector<JCheckBox> cb=new Vector<JCheckBox>();
	Vector<String> ids=new Vector<String>(); //
	
	public PartyChatDialog(Connection con, KakaoMain kakaoMain) {
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.kakaoMain = kakaoMain;
		this.con = con;
		manager = DBManager.getInstance();
		con = manager.getConnection();
		//myFriends=kakaoMain.myFriends;
		//myFriendsCopy=kakaoMain.myFriendsCopy;
		friendsList=kakaoMain.friendsList;
		
		/*for(int i=0; i<myFriendsCopy.size(); i++){
			myFriendsCopy.add(kakaoMain.myFriendsCopy.get(i));
			//System.out.println("myFriendCopy�� ����ִ� ���:"+kakaoMain.myFriendsCopy.get(i));
		}*/
		

		setLayout(new BorderLayout());
		// getContentPane().setBackground(Color.WHITE);

		p_north = new JPanel(); // border
		p_center = new JPanel(); // border
		p_south=new JPanel();
		p_centerup=new JPanel();
		// p_friend.setLayout(new BorderLayout());

		p_north.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);

		p_north.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());

		la_add = new JLabel("�ʴ��ϱ�", JLabel.CENTER);
		bt_close = new JButton("X");
		bt_ok=new JButton("Ȯ��");
		
		p_north.add(la_add);
		p_north.add(bt_close, BorderLayout.EAST);
		p_south.add(bt_ok);
		
		//CheckboxGroup cg = new CheckboxGroup();
		//üũ�ڽ� ����
		for(int i=0; i<friendsList.size();i++){
			JCheckBox c=new JCheckBox(friendsList.get(i).getYour_email(), false); //�� ģ�� id������
			cb.add(c);
			p_centerup.add(cb.get(i)); //ȭ�鿡 ���
		}
		p_center.add(p_centerup);
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		bt_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == bt_close) {
					dispose();
				}
			}
		});

		bt_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<cb.size();i++){
					if(cb.get(i).isSelected()){ //���� �Ǿ�����
						//ids.add(friendsList.get(i).getYour_email());
						ids.add(cb.get(i).getText());
						System.out.println("���õ� ģ����:"+cb.get(i).getText());
					}	
				}
				startChat();
			}
		});

		dragMouse(p_north);
		point(this);
		setButton(bt_close);

		setUndecorated(true);
		setSize(400, 500);
		setModal(true);
		setVisible(true);
	}

	// ���̾�α� ��ġ
	public void point(JDialog dialog) {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		pointerInfo.getLocation();
		Dimension my = dialog.getSize();
		dialog.setLocation(pointerInfo.getLocation().x - my.width / 2, pointerInfo.getLocation().y - my.height / 2);
	}

	// ��ư ����
	public void setButton(JButton bt) {
		bt.setBorderPainted(false);
		bt.setContentAreaFilled(false);
		bt.setFocusPainted(false);
		bt.setOpaque(false);
	}

	// �巡�� ����
	public void dragMouse(JPanel panel) {
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
	
	
	
	public void startChat(){
		
		
		Vector<String> chatMember =new Vector<String>();
		
		chatMember.add(kakaoMain.loginEmail);
		
		for(int i=0;i<ids.size();i++){
			chatMember.add(ids.get(i));
			
		}
		
		//���� ����ٰ� �߰��� �־�����... chatMember��ŭ ����� ���� �յ���.
		
		ChatMain chat=new ChatMain(kakaoMain,kakaoMain.loginEmail,kakaoMain.memberList.get(0).getE_mail(),chatMember);
		chat.setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
		chat.setVisible(true);//ȭ�� ��ü
		kakaoMain.chat.add(chat);
		
		
	}

}
