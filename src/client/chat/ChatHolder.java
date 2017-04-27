package client.chat;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatHolder extends JPanel{
	JLabel la_user; //유저아이디
	JLabel la_time; //시간정보
	JLabel readCount; //읽은 정보
	JLabel user_info; //대화상대 사진
	RTextPane chatbox; //채팅박스
	
	
	
	public ChatHolder(){
		
		la_user = new JLabel();
		la_time = new JLabel();
		readCount = new JLabel();
		user_info = new JLabel();
		chatbox = new RTextPane();
		setBackground(new Color(160, 192, 215));
	}
}


