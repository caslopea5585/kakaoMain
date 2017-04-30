package chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.KakaoMain;
import util.MyRoundButton;

public class ChattingListPanel extends JPanel{
	KakaoMain main;
	JPanel p_search; //검색 패널
	JPanel p_list; //p_search부분 제외한 아랫부분 전체 패널-그리드
	JTextField t_search;
	JScrollPane scroll;
	public Vector<ChatRoomPanel> chatlist =new Vector<ChatRoomPanel>();

	
	public ChattingListPanel(KakaoMain main){
		setLayout(new BorderLayout());
		this.main=main;
		
		p_search=new JPanel();
		t_search=new JTextField("채팅방검색", 30);
		p_list=new JPanel();
		
		scroll=new JScrollPane(p_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//p_list.setPreferredSize(new Dimension(360, 100));
		p_list.setLayout(new GridLayout(20,1));
		p_list.setBackground(Color.WHITE);
		
		t_search.setPreferredSize(new Dimension(350, 30));
		p_search.setPreferredSize(new Dimension(360, 43));
		//p_search.setBackground(Color.BLUE);
		
		p_search.add(t_search);	
		
		for(int i=0;i<main.roomNumberList.size();i++){
			ChatRoomPanel crp=new ChatRoomPanel("/p1.jpg", "chat", "--", main,main.roomNumberList.get(i));
			chatlist.add(crp);
		}
		for(int i=0;i<chatlist.size();i++){
			p_list.add(chatlist.get(i));
		}

		add(p_search, BorderLayout.NORTH);
		add(scroll);
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(360, 497));
		
	}
	//새로운 방일때 추가
	public void addChattingRoom(String img,String name,String msg,int num){
		ChatRoomPanel crp=new ChatRoomPanel(img, name, msg, main, num);
		chatlist.add(crp);
		p_list.add(crp);
	}
	
	public void addChattingRoom(MyRoundButton button,String name,String msg,int num){
		ChatRoomPanel crp=new ChatRoomPanel("", name, msg, main, num);
		crp.addButton(button);
		chatlist.add(crp);
		p_list.add(crp);
	}
	
}
