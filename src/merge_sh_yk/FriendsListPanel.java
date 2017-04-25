package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

import db.DBManager;

public class FriendsListPanel extends JPanel{
	JPanel p_search; //검색 패널
	JPanel p_list; //p_search부분 제외한 아랫부분 전체 패널-그리드
	JPanel p_myProfile;
	JTextField t_search;
	JLabel la_myProfile, la_friends;
	JScrollPane scroll;
	int friends_count=0; //친구 수
	
	//ArrayList<PersonPanel> people = new ArrayList<PersonPanel>(); //전체 member테이블 레코드 저장
	public ArrayList<PersonPanel> myFriends = new ArrayList<PersonPanel>(); //friends 테이블 레코드 저장

	KakaoMain kakaoMain;
	
	
	String myPhotoPath, myName, myStatusMsg;
	int j=0; //로그인한사람의 정보를 멤버리스트에서 찾기위한 변수.
	int x=7, y=1;
	
	public FriendsListPanel(KakaoMain kakaoMain){
		this.kakaoMain= kakaoMain;
		
		p_search=new JPanel();
		t_search=new JTextField("이름검색", 30);
		p_list=new JPanel();
		p_myProfile=new JPanel();
		la_myProfile=new JLabel("내 프로필 ");
		la_myProfile.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		GridLayout grid=new GridLayout(x, y);

		scroll=new JScrollPane(p_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(355, 435));
		scroll.setLayout(new ScrollPaneLayout() {
		      @Override
		      public void layoutContainer(Container parent) {
		        JScrollPane scrollPane = (JScrollPane) parent;

		        Rectangle availR = scrollPane.getBounds();
		        availR.x = availR.y = 0;

		        Insets parentInsets = parent.getInsets();
		        availR.x = parentInsets.left;
		        availR.y = parentInsets.top;
		        availR.width -= parentInsets.left + parentInsets.right;
		        availR.height -= parentInsets.top + parentInsets.bottom;

		        Rectangle vsbR = new Rectangle();
		        vsbR.width = 12;
		        vsbR.height = availR.height;
		        vsbR.x = availR.x + availR.width - vsbR.width;
		        vsbR.y = availR.y;

		        if (viewport != null) {
		          viewport.setBounds(availR);
		        }
		        if (vsb != null) {
		          vsb.setVisible(true);
		          vsb.setBounds(vsbR);
		        }
		      }
		    });
		
		scroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		p_list.setPreferredSize(new Dimension(360, 454));
		p_list.setLayout(grid);
		p_list.setBackground(Color.WHITE);
		
		t_search.setPreferredSize(new Dimension(350, 30));
		p_search.setPreferredSize(new Dimension(360, 43));
		//p_search.setBackground(Color.BLUE);
		
		p_search.add(t_search);	
		
		//friends_count=kakaoMain.memberList.size()-1;
		friends_count=kakaoMain.friendsList.size();
		
		p_list.add(la_myProfile);
		la_friends=new JLabel("친구    "+friends_count);
		la_friends.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		
		//나를 패널에 붙이기
		while( !(kakaoMain.loginEmail.equals(kakaoMain.memberList.get(j).getE_mail())) ){
			j++;
		}
		myFriends.add(new PersonPanel(kakaoMain,kakaoMain.memberList.get(j).getProfile_img(), kakaoMain.memberList.get(j).getNik_id(),  kakaoMain.memberList.get(j).getStatus_msg() ));
		System.out.println("나: "+kakaoMain.memberList.get(j).getNik_id());
		p_list.add(myFriends.get(0)); //myFriends의 첫번째 인덱스에 나를 등록. 
		p_list.add(la_friends);
		
		//친구들을 패널에 붙이기
		int cnt=0;
		System.out.println("friendsList size:"+kakaoMain.friendsList.size());
		System.out.println("memberList size:"+kakaoMain.memberList.size());
		
		for(int i=0; i<kakaoMain.friendsList.size();i ++){
			for(int j=0; j<kakaoMain.memberList.size(); j++){
				if(kakaoMain.friendsList.get(i).getYour_email().equals(kakaoMain.memberList.get(j).getE_mail())){
					System.out.println("j");
					cnt++;
					myFriends.add(new PersonPanel(kakaoMain,kakaoMain.memberList.get(j).getProfile_img(), kakaoMain.memberList.get(j).getNik_id(),  kakaoMain.memberList.get(j).getStatus_msg() ));
					p_list.add(myFriends.get(cnt));
					/*if(cnt>=4){
						x=cnt+1;
						grid=new GridLayout(x, y);
						p_list.setLayout(grid);
						p_list.updateUI();
					}*/
					System.out.println("친구:"+myFriends.get(cnt).name);
					System.out.println(" 리스트 패널 사이즈:"+myFriends.size());
					
				}
			}
		}
		kakaoMain.myFriends=myFriends;
		kakaoMain.p_list=p_list;
		kakaoMain.friendsListPanel=this;
		
		kakaoMain.friends_count=friends_count;
		kakaoMain.la_friends=la_friends;
		
		
		add(p_search, BorderLayout.NORTH);
		add(scroll);
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(360, 497));
		//setPreferredSize(new Dimension(360, 450));
		
	}
	public void gogo(){
		updateUI();
	}
	
}