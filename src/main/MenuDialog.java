package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import friends.AddFriendDialog;
import friends.PartyChatDialog;


public class MenuDialog extends JDialog implements ActionListener{
	KakaoMain kakaoMain;
	
	JPanel p_addFriend, p_logout, p_exit, p_party, p_all;
	JButton bt_addFriend, bt_logout, bt_exit, bt_party;
	
	public MenuDialog(KakaoMain kakaoMain){
		this.kakaoMain=kakaoMain;
		
		setLayout(new FlowLayout());
		getRootPane().setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY) );
		getContentPane().setBackground(Color.WHITE);
		
		p_addFriend=new JPanel();
		p_logout=new JPanel();
		p_exit=new JPanel();
		p_party=new JPanel();
		p_all=new JPanel();
	
		bt_addFriend=new JButton("친구 추가");
		bt_logout=new JButton("로그아웃");
		bt_exit=new JButton("종료");
		bt_party=new JButton("새로운 초대");
		
		p_addFriend.add(bt_addFriend);
		p_party.add(bt_party);
		p_logout.add(bt_logout);
		p_exit.add(bt_exit);
		
		p_addFriend.setBackground(Color.WHITE);
		p_logout.setBackground(Color.WHITE);
		p_exit.setBackground(Color.WHITE);
		p_party.setBackground(Color.WHITE);
		p_all.setBackground(Color.WHITE);
		p_all.setPreferredSize(new Dimension(90, 170));
		
		setButton(bt_addFriend);
		setButton(bt_logout);
		setButton(bt_exit);
		setButton(bt_party);
		p_all.add(p_addFriend);
		p_all.add(p_party);
		p_all.add(p_logout);
		p_all.add(p_exit);
		
		add(p_all);
		
		point(this);
		
		
		bt_addFriend.addActionListener(this);
		bt_logout.addActionListener(this);
		bt_exit.addActionListener(this);
		bt_party.addActionListener(this);
		
		setUndecorated(true);
		setSize(100,180);
        setModal(true);
        setVisible(true);
	}
	public void setButton(JButton bt){
		bt.setBorderPainted(false); //버튼 경계선 없애기
		bt.setContentAreaFilled(false); //색 채우기(파란색) 없애기
		bt.setFocusPainted(false); //포커스에 의한 경계 없애기
		bt.setOpaque(false); //투명도-투명해야되니까 false(불투명-true)
	}
	public void point(JDialog dialog){
		PointerInfo pointerInfo=MouseInfo.getPointerInfo();
		pointerInfo.getLocation();
		Dimension my=dialog.getSize();
		dialog.setLocation(pointerInfo.getLocation().x-my.width/2, pointerInfo.getLocation().y-my.height/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if(obj==bt_addFriend){
			dispose(); //dialog 메모리에서 지우기
			System.out.println("현재 내 친구는"+kakaoMain.friendsList.size()+"명");
			AddFriendDialog addFriendDialog=new AddFriendDialog(kakaoMain.con, kakaoMain);//친구 추가 창 생성
			//AddFriendDialog2 addFriendDialog=new AddFriendDialog2(kakaoMain.con, kakaoMain);//친구 추가 창 생성
			//ChangeProfile pop=new ChangeProfile((FriendsListPanel)kakaoMain.friendsListPanel);
		}
		else if(obj==bt_exit){
			System.exit(0);
		}else if(obj==bt_logout){
			dispose();
			//kakaoMain.panel[0]=new LoginPanel(this);
			//kakaoMain.panel[0].setVisible(true);
		}else if(obj==bt_party){
			dispose(); //dialog 메모리에서 지우기
			PartyChatDialog partyChatDialog=new PartyChatDialog(kakaoMain.con, kakaoMain);
		}
	}

}
