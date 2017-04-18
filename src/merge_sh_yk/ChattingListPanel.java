package merge_sh_yk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ChattingListPanel extends JPanel implements ActionListener{
	KakaoMain main;
	JButton bt_test;
	public ChattingListPanel(KakaoMain main){
		this.main=main;
		bt_test=new JButton("입장");
		
		add(bt_test);
		bt_test.addActionListener(this);
		setVisible(false);
		setPreferredSize(new Dimension(360, 497));
		setBackground(Color.PINK);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//여기선 연결하면 바로 채팅창을 띄워주었지만 채팅목록-> 채팅방 선택하면 아래 채팅방을 띄워주는걸로 변경하기
		main.chat.setLocation(main.getLocation().x+360,main.getLocation().y);
		main.chat.setVisible(true);//화면 교체
	}
}
