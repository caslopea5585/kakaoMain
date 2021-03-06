package friends;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Profile.Profile;
import chatting.ChattingListPanel;
import client.chat.ChatMain;
import db.DBManager;
import main.KakaoMain;
import main.MemberList;
import util.Join3Image;
import util.MyRoundButton;
import util.Util;

public class PartyChatDialog extends JDialog {
   Point mouseDownCompCoords = null;

   JPanel p_north, p_center, p_south, p_centerup;
   JLabel la_add;
   JButton bt_close,bt_ok;
   
   Connection con;
   DBManager manager;
   KakaoMain kakaoMain;
   // Vector<MemberList> member= new Vector<MemberList>(); //dto

   // email검색으로 찾은 친구 프로필사진과 닉네임 보여주기 위한 객체들
   Canvas can = null;
   BufferedImage image = null; // 프로필 사진
   BufferedImage bgimage = null; // 프로필 사진 원형처리 위한 이미지
   URL url = null;
   URL bgurl = null;

   JPanel p_friend, p_img, p_add;
   JButton bt_add;
   Friends friend;

   MemberList memberList;
   PersonPanel personPanel;
   Vector<String> ids=new Vector<String>();

   //ArrayList<PersonPanel> myFriends = new ArrayList<PersonPanel>();
   public Vector<Friends> friendsList;
   
   Profile profile;
   int q = 0;
   //CheckboxGroup cg = new CheckboxGroup();
   Vector<JCheckBox> cb=new Vector<JCheckBox>();
   Vector<String> photoPath=new Vector<String>(); //
	MyRoundButton bt_profil;//북쪽 패널 서쪽의 프로필 사진
	  String myPhotoPath;
   
   public PartyChatDialog(Connection con, KakaoMain kakaoMain) {
      
      getRootPane().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      this.kakaoMain = kakaoMain;
      this.con = con;
      manager = DBManager.getInstance();
      con = manager.getConnection();
      //myFriends=kakaoMain.myFriends;
      //myFriendsCopy=kakaoMain.myFriendsCopy;
      friendsList=kakaoMain.friendsList;
      
      String myemail = kakaoMain.friendsList.get(0).getE_mail();
      myPhotoPath="";
      for(int i=0; i<kakaoMain.memberList.size();i++){
    	  if(myemail.equals(kakaoMain.memberList.get(i).getE_mail())){
    		  myPhotoPath = kakaoMain.memberList.get(i).getProfile_img();
    	  }
      }
      
      kakaoMain.memberList.get(0).getProfile_img();
      
      /*for(int i=0; i<myFriendsCopy.size(); i++){
         myFriendsCopy.add(kakaoMain.myFriendsCopy.get(i));
         //System.out.println("myFriendCopy에 들어있는 사람:"+kakaoMain.myFriendsCopy.get(i));
      }*/
      

      setLayout(new BorderLayout());
      // getContentPane().setBackground(Color.WHITE);

      p_north = new JPanel(); // border
      p_center = new JPanel(); // border
      p_south=new JPanel();
      p_centerup=new JPanel();
      p_centerup.setBackground(Color.WHITE);
      // p_friend.setLayout(new BorderLayout());
      p_centerup.setLayout(new GridLayout(6,1));
      p_north.setBackground(Color.WHITE);
      p_center.setBackground(Color.WHITE);

      p_north.setLayout(new BorderLayout());
      p_center.setLayout(new BorderLayout());

      la_add = new JLabel("초대하기", JLabel.CENTER);
      bt_close = new JButton("X");
      bt_ok=new JButton("확인");
      
      p_north.add(la_add);
      p_north.add(bt_close, BorderLayout.EAST);
      p_south.add(bt_ok);
      
      //CheckboxGroup cg = new CheckboxGroup();
      //체크박스 생성
      for(int i=0; i<friendsList.size();i++){
         
         JCheckBox c=new JCheckBox(friendsList.get(i).getYour_email(), false); //내 친구 id얻어오기
         JPanel p=new JPanel();
         p.setBackground(Color.WHITE);
         c.setBackground(Color.WHITE);
         //cb.add(sc);
         cb.add(c);
         p_centerup.add(cb.get(i)); //화면에 출력
         for(int j=0; j<kakaoMain.memberList.size(); j++){
            if(kakaoMain.memberList.get(j).getE_mail().equals(friendsList.get(i).getYour_email())){
               photoPath.add(kakaoMain.memberList.get(j).getProfile_img());
            }
         }
         p.add(cb.get(i));
         p.add(Util.createRoundLabel(photoPath.get(i),50));
         p_centerup.add(p);
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
               if(cb.get(i).isSelected()){ //선택 되었으면
                  //ids.add(friendsList.get(i).getYour_email());
                  ids.add(cb.get(i).getText());
                  
                  System.out.println("선택된 친구들:"+cb.get(i).getText());
               }   
            }
            startChat();
         }
      });

      dragMouse(p_north);
      point(this);
      setButton(bt_close);

      setUndecorated(true);
      setSize(250, 500);
      setModal(true);
      setVisible(true);
   }

   // 다이얼로그 위치
   public void point(JDialog dialog) {
      PointerInfo pointerInfo = MouseInfo.getPointerInfo();
      pointerInfo.getLocation();
      Dimension my = dialog.getSize();
      dialog.setLocation(pointerInfo.getLocation().x - my.width / 2, pointerInfo.getLocation().y - my.height / 2);
   }

   // 버튼 설정
   public void setButton(JButton bt) {
      bt.setBorderPainted(false);
      bt.setContentAreaFilled(false);
      bt.setFocusPainted(false);
      bt.setOpaque(false);
   }

   // 드래그 설정
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
      
      //향후 여기다가 추가로 넣어주자... chatMember만큼 사이즈가 돌수 잇도록.
      
      ChatMain chat=new ChatMain(kakaoMain,kakaoMain.loginEmail,kakaoMain.memberList.get(0).getE_mail(),chatMember);
      chat.setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
      chat.setVisible(true);//화면 교체
      kakaoMain.chat.add(chat);
      
      StringBuffer sb=new StringBuffer();
      for(int i=0;i<chatMember.size();i++){
         if((chatMember.size()-1)==i){
            sb.append(chatMember.get(i)+" : "+chatMember.size());
         }
         else{
            sb.append(chatMember.get(i)+",");
         }
      }
      
      Vector<String> yourPhoto = new Vector<String>();
      String yourPhotoPath;

      yourPhoto.add(myPhotoPath);
      for(int i=0; i<friendsList.size();i++){
	      for(int j=0; j<kakaoMain.memberList.size(); j++){
	          if(kakaoMain.memberList.get(j).getE_mail().equals(friendsList.get(i).getYour_email())){
	        	  yourPhoto.add(kakaoMain.memberList.get(j).getProfile_img());

	            	System.out.println("포토"+yourPhoto.get(i));
	          }
	       }
      }
		if(yourPhoto.size()==1){
			yourPhotoPath = yourPhoto.get(0);
			bt_profil = new MyRoundButton(Util.createRoundIcon(yourPhotoPath, 40));
		}else if(yourPhoto.size()==3){
			
			Icon icon = Join3Image.createJoin3Image(yourPhoto.get(0),yourPhoto.get(1),yourPhoto.get(2));
			bt_profil = new MyRoundButton(icon);
		}else{
			yourPhotoPath = yourPhoto.get(0);
			bt_profil = new MyRoundButton(Util.createRoundIcon(yourPhotoPath, 40));
			
		}
      
      kakaoMain.roomNumberList.add(kakaoMain.roomNumberList.size());
      ChattingListPanel clp=(ChattingListPanel)kakaoMain.chattingListPanel;
      clp.addChattingRoom(bt_profil,sb.toString(),"",kakaoMain.roomNumberList.size()-1);
   }

}