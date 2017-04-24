package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import db.DBManager;

public class FriendsListPanel extends JPanel{
	JPanel p_search; //�˻� �г�
	JPanel p_list; //p_search�κ� ������ �Ʒ��κ� ��ü �г�-�׸���
	JPanel p_myProfile;
	JTextField t_search;
	JLabel la_myProfile, la_friends;
	JScrollPane scroll;
	int friends_count=0; //ģ�� ��

	
	//PersonPanel[] people=new PersonPanel[10];
	
	//ArrayList<PersonPanel> people = new ArrayList<PersonPanel>(); //��ü member���̺� ���ڵ� ����
	public ArrayList<PersonPanel> myFriends = new ArrayList<PersonPanel>(); //friends ���̺� ���ڵ� ����

	KakaoMain kakaoMain;
	
	
	String myPhotoPath, myName, myStatusMsg;
	int j=0; //�α����ѻ���� ������ �������Ʈ���� ã������ ����.

	
	public FriendsListPanel(KakaoMain kakaoMain){
		this.kakaoMain= kakaoMain;
		
		p_search=new JPanel();
		t_search=new JTextField("�̸��˻�", 30);
		p_list=new JPanel();
		p_myProfile=new JPanel();
		la_myProfile=new JLabel("    �� ������ ");
	
		
		scroll=new JScrollPane(p_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//p_list.setPreferredSize(new Dimension(360, 100));
		p_list.setLayout(new GridLayout(10,1));
		p_list.setBackground(Color.WHITE);
		
		t_search.setPreferredSize(new Dimension(350, 30));
		p_search.setPreferredSize(new Dimension(360, 43));
		//p_search.setBackground(Color.BLUE);
		
		p_search.add(t_search);	
		
		//friends_count=kakaoMain.memberList.size()-1;
		friends_count=kakaoMain.friendsList.size();
		
		p_list.add(la_myProfile);
		la_friends=new JLabel("    ģ��   "+friends_count);
		
		
		/*//������ ����� ��ã��..
		while( !(kakaoMain.loginEmail.equals(kakaoMain.memberList.get(j).getE_mail())) ){
			j++;
		}
		people.add(new PersonPanel(kakaoMain,kakaoMain.memberList.get(j).getProfile_img(), kakaoMain.memberList.get(j).getNik_id(),  kakaoMain.memberList.get(j).getStatus_msg() ));
		System.out.println("��: "+kakaoMain.memberList.get(j).getNik_id());
		p_list.add(people.get(0));
		p_list.add(la_friends);
		
		int b=1; //�߰���� ī��Ʈ ���� �ϱ�����
		//������ �������� ģ���� ����ϱ�
<<<<<<< HEAD
		for(int i=0;i<kakaoMain.memberList.size();i++){
			if(kakaoMain.memberList.get(i).getE_mail().equals(kakaoMain.memberList.get(j).getE_mail()     )){
				i--;
				continue;
				
				
			}else{
				people.add(new PersonPanel(kakaoMain,kakaoMain.memberList.get(i).getProfile_img(), kakaoMain.memberList.get(i).getNik_id(),  kakaoMain.memberList.get(i).getStatus_msg() ));
				p_list.add(people.get(b));
				b++;
				System.out.println("������"+people.size());
=======
		int cnt=0;
		for(int i=0;i<kakaoMain.memberList.size();i++){//a�� �α����� ���(j=1)
			System.out.println("�α����ѻ��(j):"+j+"��°");
			if(!(kakaoMain.memberList.get(i).getE_mail().equals(kakaoMain.memberList.get(j).getE_mail()))){
				cnt++;
				people.add(new PersonPanel(kakaoMain,kakaoMain.memberList.get(i).getProfile_img(), kakaoMain.memberList.get(i).getNik_id(),  kakaoMain.memberList.get(i).getStatus_msg() ));
				p_list.add(people.get(cnt));

				System.out.println("ģ��:"+people.get(cnt).name);
				System.out.println("������:"+people.size());
			}
		}*/
		
		//���� �гο� ���̱�
		while( !(kakaoMain.loginEmail.equals(kakaoMain.memberList.get(j).getE_mail())) ){
			j++;
		}
		myFriends.add(new PersonPanel(kakaoMain,kakaoMain.memberList.get(j).getProfile_img(), kakaoMain.memberList.get(j).getNik_id(),  kakaoMain.memberList.get(j).getStatus_msg() ));
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
					System.out.println("j");
					cnt++;
					myFriends.add(new PersonPanel(kakaoMain,kakaoMain.memberList.get(j).getProfile_img(), kakaoMain.memberList.get(j).getNik_id(),  kakaoMain.memberList.get(j).getStatus_msg() ));
					p_list.add(myFriends.get(cnt));
					
					System.out.println("ģ��:"+myFriends.get(cnt).name);
					System.out.println(" ����Ʈ �г� ������:"+myFriends.size());
					
					kakaoMain.myFriends=myFriends;
					kakaoMain.p_list=p_list;
					
				}
			}
		}
		
		
		add(p_search, BorderLayout.NORTH);
		add(scroll);
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(360, 497));
		//setPreferredSize(new Dimension(360, 450));
		
	}
	
}