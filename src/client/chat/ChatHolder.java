package client.chat;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatHolder extends JPanel{
	JLabel la_user; //�������̵�
	JLabel la_time; //�ð�����
	JLabel readCount; //���� ����
	JLabel user_info; //��ȭ��� ����
	RTextPane chatbox; //ä�ùڽ�
	
	
	
	public ChatHolder(){
		
		la_user = new JLabel();
		la_time = new JLabel();
		readCount = new JLabel();
		user_info = new JLabel();
		chatbox = new RTextPane();
		setBackground(new Color(160, 192, 215));
	}
}


