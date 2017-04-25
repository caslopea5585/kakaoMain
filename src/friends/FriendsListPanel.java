package friends;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

import profile.Profile;
import main.KakaoMain;
import merge_sh_yk.MyScrollBarUI;

public class FriendsListPanel extends JPanel {
	JPanel p_search; //�˻� �г�
	JPanel p_list; //p_search�κ� ������ �Ʒ��κ� ��ü �г�-�׸���
	JPanel p_myProfile;
	JTextField t_search;
	JLabel la_myProfile, la_friends;
	JScrollPane scroll;
	int friends_count=0; //ģ�� ��
	
	//ArrayList<PersonPanel> people = new ArrayList<PersonPanel>(); //��ü member���̺� ���ڵ� ����
	public ArrayList<PersonPanel> myFriends = new ArrayList<PersonPanel>(); //friends ���̺� ���ڵ� ����

	KakaoMain kakaoMain;
	PersonPanel pp;
	Profile profile;
	
	String myPhotoPath, myName, myStatusMsg;
	int j=0; //�α����ѻ���� ������ �������Ʈ���� ã������ ����.
	int x=7, y=1;
	int q=0; //�����͸��� ó��ī��Ʈ.
	
	public FriendsListPanel(KakaoMain kakaoMain){
		this.kakaoMain= kakaoMain;
		
		p_search=new JPanel();
		t_search=new JTextField("�̸��˻�", 30);
		p_list=new JPanel();
		p_myProfile=new JPanel();
		la_myProfile=new JLabel("�� ������ ");
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
		la_friends=new JLabel("ģ��    "+friends_count);
		la_friends.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		
		//���� �гο� ���̱�
		while( !(kakaoMain.loginEmail.equals(kakaoMain.memberList.get(j).getE_mail())) ){
			j++;
		}
		myFriends.add(pp=new PersonPanel(kakaoMain,false,kakaoMain.memberList.get(j).getProfile_img(), kakaoMain.memberList.get(j).getNik_id(),  kakaoMain.memberList.get(j).getStatus_msg() ));
		
		pp.can.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				profile=new Profile(kakaoMain.memberList.get(j).getProfile_img(),kakaoMain,true,j); //f
			}
			
		});
		System.out.println("��: "+kakaoMain.memberList.get(j).getNik_id());
		p_list.add(myFriends.get(0)); //myFriends�� ù��° �ε����� ���� ���. 
		p_list.add(la_friends);
		
		//ģ������ �гο� ���̱�
		int cnt=0;
		System.out.println("friendsList size:"+kakaoMain.friendsList.size());
		System.out.println("memberList size:"+kakaoMain.memberList.size());
		
		for(int i=0; i<kakaoMain.friendsList.size();i ++){
			for(int j=0; j<kakaoMain.memberList.size(); j++){
				if(kakaoMain.friendsList.get(i).getYour_email().equals(kakaoMain.memberList.get(j).getE_mail())){
					
					System.out.println("�������"+j);
					cnt++;
					myFriends.add(new PersonPanel(kakaoMain,true,kakaoMain.memberList.get(j).getProfile_img(), kakaoMain.memberList.get(j).getNik_id(),  kakaoMain.memberList.get(j).getStatus_msg() ));
					q=j;
					
					
					
					myFriends.get(myFriends.size()-1).can.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							Object obj = e.getSource();
							System.out.println(myFriends.get(1).can + "ĵ�����ּ�");
							System.out.println(obj + "�̺�Ʈ �߻���ü");
							for(int i=0;i<myFriends.size();i++){
								if(obj==myFriends.get(i).can){
									System.out.println(i+"�����ʼ� ���̴�?");
									//����� �ִ� i�� �̸��ϰ����� �������Ʈ�� ���̰� ��ġ�ϴ� ���� ã�Ƽ� �װ��� �̹����� �ҷ��;� �Ѵ�.
									System.out.println(myFriends.get(i) + "�̸��� �����;���");
									System.out.println(myFriends.get(i).name);
									for(int j=0;j<kakaoMain.memberList.size();j++){
										if(myFriends.get(i).name.equals(kakaoMain.memberList.get(j).getNik_id())){
											profile=new Profile(kakaoMain.memberList.get(j).getProfile_img(),kakaoMain,false,j);
											
										}
									}
								}
							}
							
						}
					});
					
				/*	myFriends.get(myFriends.size()-1).can.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							profile=new Profile(kakaoMain.memberList.get(q).getProfile_img(),kakaoMain,true); //f
							
						}
						
					});*/
					
					p_list.add(myFriends.get(cnt));
					/*if(cnt>=4){
						x=cnt+1;
						grid=new GridLayout(x, y);
						p_list.setLayout(grid);
						p_list.updateUI();
					}*/
					System.out.println("ģ��:"+myFriends.get(cnt).name);
					System.out.println(" ����Ʈ �г� ������:"+myFriends.size());
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