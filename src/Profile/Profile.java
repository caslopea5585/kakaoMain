package Profile;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import chatting.ChattingListPanel;
import client.chat.Chat;
import client.chat.ChatMain;
import main.KakaoMain;
import main.MemberList;


public class Profile extends JFrame implements ActionListener{
   Point mouseDownCompCoords = null;
   
   
   Profile profile;
   Canvas can_north_img,can_south_img,can_profile;
   JLabel la_name,la_chat,la_manager;
   URL url_profileBackground,url_profileSouth,url_profileImage,url_close;
   JPanel p_status;
   BufferedImage buffr_north,buffr_south,buffr_profile,buffr_close;
   JLayeredPane layeredPane;
   ImageIcon chat,manager;
   RoundButton bt_chat,bt_manager,bt_back_profile,bt_close;
   String status_msg="상태메시지";
   JFileChooser chooser;
   MemberList memberList;
   
   
   
   KakaoMain kakaoMain;
   boolean flag; //나인지 아닌지 구별
   int index; //멤버리스트의 인덱스 찾기
   Vector roomNumberArray=new Vector();//룸넘버 정보를 저장하기 위한 벡터

	String path;
   public Profile(String photopath,KakaoMain kakaoMain,boolean flag,int index) {
      
      profile = this;
      this.kakaoMain=kakaoMain;
      this.flag = flag;
      this.index= index;
		path=photopath;
      
      layeredPane = new JLayeredPane();
      url_profileBackground = this.getClass().getResource("/bg_north.png");   //상단배경
      url_profileSouth=this.getClass().getResource("/bg_south.png");   //하단
      url_profileImage=this.getClass().getResource(photopath); //프로필사진
      url_close = this.getClass().getResource("/close3.png");
      status_msg=kakaoMain.memberList.get(index).getStatus_msg();
      
      this.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyCode();
            if(keycode==KeyEvent.VK_ESCAPE){
               System.out.println("나눌렀어?");
               dispose();
            }else if(keycode ==KeyEvent.VK_ENTER){
               System.out.println("나눌렀어");
               dispose();
            }
         }
         
      });
      try {
         buffr_south = ImageIO.read(url_profileSouth);
         buffr_close = ImageIO.read(url_close);
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      System.out.println(kakaoMain.loginEmail);
      
      can_north_img = new Canvas(){
         public void paint(Graphics g) {
               
            try {
               buffr_north = ImageIO.read(url_profileBackground);
               buffr_profile= ImageIO.read(url_profileImage);
               
            } catch (IOException e) {
               e.printStackTrace();
            }      
                                                               //잘라내는 원의 크기를 결정
              Ellipse2D.Double ellipse1 = new Ellipse2D.Double(99,181,100,98); 
              Ellipse2D.Double ellipse2 = new Ellipse2D.Double(30,20,30,30);
              
              Area circle = new Area(ellipse1);
              Area circle2 = new Area(ellipse2);
              
              g.drawImage(buffr_north, 0, 0, 400,250,this);  //백그라운드이미지
              g.drawImage(buffr_close, 270, 10, 30,30,this);  //백그라운드이미지
            
              
              g.setFont(new Font("돋움", Font.PLAIN, 25));
              g.setColor(Color.BLACK);
              
              g.drawString(status_msg, 120, 100);                  //상태메시지
              
              
              Graphics2D g2 =(Graphics2D) g;
              g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
              g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
              g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
              g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
              g2.setClip(circle);
              
              g2.drawImage(buffr_profile, 100,180,100,100, null);
              g2.drawImage(buffr_close, 30, 25, 25, 25, null);
              
              g2.setColor(Color.WHITE);
              g2.setClip(null);
              Stroke s = new BasicStroke(2);
              g2.setStroke(s);
              g2.setColor(Color.BLACK);
              //g2.draw(circle);
              g2.dispose();
              
         }

         
      };
      can_south_img = new Canvas(){
         public void paint(Graphics g) {
            g.drawImage((Image)buffr_south, 0, 0, 300, 300,this);
         }
      };
      
      
      la_name = new JLabel("아이디명");
      la_name.setForeground(Color.black);
      la_name.setText(kakaoMain.memberList.get(index).getNik_id());
      
      
      ImageIcon chat = new ImageIcon(this.getClass().getResource("/chat.png"));
      bt_chat = new RoundButton(chat);
      
      
      ImageIcon manager = new ImageIcon(this.getClass().getResource("/manager.png"));
      bt_manager = new RoundButton(manager);
      
      
      ImageIcon back_profile = new ImageIcon(this.getClass().getResource("/back_profile.png"));
      bt_back_profile = new RoundButton(back_profile);
      
      
      ImageIcon close = new ImageIcon(this.getClass().getResource("/close2.png"));
      bt_close = new RoundButton(close);
      
      chooser= new JFileChooser("C:/java_workspace2/ExKakaoProject/res");
   
      la_chat = new JLabel("채팅하기");
      la_manager =new JLabel("수정하기");
      
      
      la_chat.setForeground(Color.BLACK);
      la_manager.setForeground(Color.BLACK);
      
      

      //                            x  y   width  height
      can_north_img.setBounds(0, 0, 400, 300);
      la_name.setBounds(145, 280, 70, 50);
      bt_chat.setBounds(65, 340, 50, 50);
      
      la_chat.setBounds(65, 390, 70, 30);
      la_manager.setBounds(165, 390, 70, 30);
      
      bt_close.setBounds(165, 250, 50, 50);
      
      
      layeredPane.add(can_north_img, 1);
      layeredPane.add(la_name, 2,1);
      layeredPane.add(bt_chat, 3,2);
      
      layeredPane.add(la_chat, 4,3);
      layeredPane.add(la_manager, 4,3);
      
      bt_manager.setBounds(165, 340, 50, 50);
      bt_back_profile.setBounds(245, 250, 45, 45);
      layeredPane.add(bt_manager, 4,3);
      layeredPane.add(bt_back_profile, 4,3);
      
      
      
      
      for(int i=0;i<kakaoMain.memberList.size();i++){
         System.out.println("e_mail"+kakaoMain.memberList.get(i).getE_mail());
      }
      
      System.out.println("프로필시"+flag); //나이면 false, 나 아니면 true
      if(!flag){
         //내가 나를 눌렀을때 소스 구현
         bt_manager.setVisible(flag);
         bt_back_profile.setVisible(flag);
         la_manager.setVisible(flag);
         bt_chat.setBounds(120, 340, 50, 50);
         la_chat.setBounds(120, 390, 70, 30);
         
         
      }else{
         //내가 다른사람을 눌렀을때 소스
         
      }
      
      can_north_img.addMouseListener(new MouseAdapter() {
         
         public void mouseClicked(MouseEvent e) {
            
            Object obj =e.getPoint();
            Point p = (Point)obj;
            //x의 범위 116~172 y의 ㅂ범위 201~163
            int x= (int)p.getX();
            int y =(int)p.getY();
            System.out.println(p.getX()+","+p.getY());
            
            if(x>116 && x<172 && y>200&& y<260){
               //해당범위를 클릭했을때만 프로필 이미지가 확대되도록 뜸.
               ProfileImage profileImage = new ProfileImage(profile);
            }else if(x>275&&x<300 && y>10 && y<36){
               System.out.println("dddddddddddd");
               dispose();
            }
         }
      });
      
      
      bt_manager.addActionListener(this);
      bt_back_profile.addActionListener(this);
      bt_chat.addActionListener(this);
      
      
      add(layeredPane);
      dragMouse(this);
      setUndecorated(true); //타이틀바 제거
      setBackground(Color.WHITE);
      setSize(310, 460);
      setVisible(true);
      
      
   }
   
   public void dragMouse(JFrame panel){
      panel.addMouseListener(new MouseAdapter() {      
         @Override
         public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
         }
         @Override
         public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
         }
      });
      panel.addMouseMotionListener(new MouseAdapter() {
         @Override
         public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
              setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
         }
      });
   }
   
   
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if(obj==bt_manager){
         EditProfile editProfile = new EditProfile(this,kakaoMain);
         
      }else if(obj==bt_back_profile){
         int result = chooser.showOpenDialog(this);
         if(result == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            url_profileBackground=this.getClass().getResource("/"+file.getName()+""); //프로필사진
            kakaoMain.memberList.get(0).setProfile_Back_Img(file.getName());
            can_north_img.repaint();
         }
      }else if(obj==bt_chat){
         //ChatMain chat=new ChatMain(kakaoMain);
         //챗메인을 여기서 만들자. (여기서 대화방 생성)
         chat();
         
         
         //System.out.println(kakaoMain.loginEmail + kakaoMain.memberList.get(index).getE_mail());
         //로그인 한 사람의 이메일  && 채팅 대상자의 이메일(방번호 조회의 대상)
         
         
      }
      
   }
   
   public void chat(){
      //1. 로그인한 아이디 가져오기 
      //2. 대화상대의 아이디 가져오기
      //3. ChatMain New할때 (로그인한 아이디, 대화상대 아이디) 보내주기.
      //4. 선택한 값 배열을 보내줘야 함.. 0째는 무조건 나... 나머지는 대화상대
      
      Vector<String> chatMember =new Vector<String>();
      String myId=kakaoMain.loginEmail;
      String yourId=kakaoMain.memberList.get(index).getE_mail();
      
      //쳇을 하는사람의 정보를...담아두자...이부분은 1:1채팅, 

      chatMember.add(myId);
      chatMember.add(yourId);
      
      //향후 여기다가 추가로 넣어주자... chatMember만큼 사이즈가 돌수 잇도록.
      
      ChatMain chat=new ChatMain(kakaoMain,kakaoMain.loginEmail,kakaoMain.memberList.get(index).getE_mail(),chatMember);
      chat.setLocation(kakaoMain.getLocation().x+360,kakaoMain.getLocation().y);
      chat.setVisible(true);//화면 교체
      kakaoMain.chat.add(chat);
		kakaoMain.roomNumberList.add(kakaoMain.roomNumberList.size());
		ChattingListPanel clp=(ChattingListPanel)kakaoMain.chattingListPanel;
		clp.addChattingRoom(path,kakaoMain.memberList.get(index).getNik_id(),"",kakaoMain.roomNumberList.size()-1);
		
		ArrayList<String> sender = new ArrayList<String>();
		ArrayList<String> listener = new ArrayList<String>();
		ArrayList<String> roomNumber = new ArrayList<String>();

		
		for (int j = 0; j < kakaoMain.keyMap.size(); j++) {
			if (j % 3 == 0) {
				sender.add(kakaoMain.keyMap.elementAt(j));
				System.out.println("SENDER I의 값은???"+kakaoMain.keyMap.elementAt(j));
			} else if(j%3==1){
				listener.add(kakaoMain.keyMap.elementAt(j));
				System.out.println("LISTENER I의 값은???"+kakaoMain.keyMap.elementAt(j));
			}else if(j%3==2){
				roomNumber.add(kakaoMain.keyMap.elementAt(j));
				System.out.println("ROOMNUMBER I의 값은???"+kakaoMain.keyMap.elementAt(j));
			}
		}
		
			if(kakaoMain.backChatMsg.size()!=0){
			for (int i = 0; i < kakaoMain.backChatMsg.size(); i++) {
				for(int j=0; j<roomNumber.size(); j++){
					if ((sender.get(i).equals(myId)||sender.get(i).equals(yourId))&&(listener.get(i).equals(yourId)||listener.get(i).equals(myId))&&roomNumber.get(i).equals(Integer.toString(j))) {
						System.out.println("방번호는?? :" + kakaoMain.backChatMsg.get(i).getRoomNumber());
						Chat chatDto = new Chat();
						chatDto.setMyId(kakaoMain.backChatMsg.get(i).getSender());
						chatDto.setYourId(kakaoMain.backChatMsg.get(i).getListener());
						chatDto.setMsg(kakaoMain.backChatMsg.get(i).getMsg());
						chatDto.setTimeValue(kakaoMain.backChatMsg.get(i).getTime());
						System.out.println("현재의 i의 값은????" + i);
						kakaoMain.chat.get(kakaoMain.chat.size()-1).model.addRow(chatDto);
					}
				}
			}
		
      dispose();
      
   }
   }

}