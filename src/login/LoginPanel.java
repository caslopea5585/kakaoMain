package login;
 
 
 
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.simple.JSONObject;

import client.chat.ClientThread;
import db.DBManager;
import friends.Friends;
import main.KakaoMain;
import main.MemberList;
import util.HintedPasswordField;
import util.HintedTextField;
 
 
public class LoginPanel extends JPanel{
   KakaoMain kakaoMain;
   JPanel p_north, p_center, p_south, p_id_pw;
   HintedTextField t_email;
   HintedPasswordField t_pw;
   JButton bt_login, bt_find_pw;
   Checkbox auto_login;
   JLabel info, link, temp;
   
   Canvas logo;
   BufferedImage image=null;
   URL url=this.getClass().getResource("/icon.png");
   
   Register register;
   
   
   Thread thread;
   Connection con;
   DBManager manager;
   
 

   String ip="211.238.142.113";
  //String ip="211.238.142.102";//////////////////임시 아이피

   Socket socket;
   public ClientThread ct;
   int port=7777;
   
   public LoginPanel(KakaoMain kakaoMain) {
       this.kakaoMain=kakaoMain; 
       setLayout(new BorderLayout());
 
      
      //db와 연동
      manager=DBManager.getInstance();
      con=manager.getConnection();
      
      //패널
      p_north=new JPanel();
      p_center=new JPanel();
      p_south=new JPanel();
      p_id_pw=new JPanel();
            
      p_north.setPreferredSize(new Dimension(360, 250));
      p_center.setPreferredSize(new Dimension(360, 170));
      p_south.setPreferredSize(new Dimension(360, 170));
      p_id_pw.setPreferredSize(new Dimension(250, 70));
      
      p_north.setBackground(new Color(255, 235, 051));
      p_center.setBackground(new Color(255, 235, 051));
      p_south.setBackground(new Color(255, 235, 051));
      p_id_pw.setBackground(new Color(255, 235, 051));
      
      p_north.setLayout(new BorderLayout());
      p_center.setLayout(new FlowLayout());
      p_south.setLayout(new FlowLayout());
      p_id_pw.setLayout(new GridLayout(2,1));

      //캔버스
      try {
         image=ImageIO.read(url);
      } catch (IOException e) {
         e.printStackTrace();
      }
      logo=new Canvas(){
         @Override
         public void paint(Graphics g) {
            g.drawImage(image, 125, 75, 110, 110, this);
         }
      };
      logo.setPreferredSize(new Dimension(150, 150));
      
      t_email=new HintedTextField("카카오계정(이메일)");
      t_pw=new HintedPasswordField("비밀번호");
      bt_login=new JButton("로그인");
      auto_login=new Checkbox("잠금모드로 자동로그인");
      
      bt_login.setPreferredSize(new Dimension(250, 30));
      bt_login.setBackground(new Color(113, 92, 94));
      
      info=new JLabel("모바일에서 카카오계정을 확인할 수 있습니다.");
      link= new JLabel("<HTML><U>카카오계정안내</U></HTML>");
      link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   

      temp=new JLabel();
      temp.setPreferredSize(new Dimension(300, 35));
      
      bt_find_pw=new JButton("비밀번호를 잊어버리셨나요?");
      bt_find_pw.setBackground(new Color(255, 235, 051));

      p_north.add(logo);
      p_id_pw.add(t_email);
      p_id_pw.add(t_pw);
      p_center.add(p_id_pw);
      p_center.add(bt_login);
      p_center.add(auto_login);
      
      p_south.add(info);
      p_south.add(link);
      p_south.add(temp);
      p_south.add(bt_find_pw);
      
      add(p_north, BorderLayout.NORTH);
      add(p_center, BorderLayout.CENTER);
      add(p_south, BorderLayout.SOUTH);
      
      logo.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
             register=new Register(kakaoMain);
          }
       });
      t_email.addKeyListener(new KeyAdapter() {
    	  public void keyPressed(KeyEvent e) {
    		if(e.getKeyCode()==KeyEvent.VK_ENTER){
    			t_pw.requestFocus();
    		}
    	} 
      });
      t_pw.addKeyListener(new KeyAdapter() {
    	  public void keyPressed(KeyEvent e) {
    		if(e.getKeyCode()==KeyEvent.VK_ENTER){
    			bt_login.requestFocus();
    			login();
    		}
    	} 
      });
      bt_login.addActionListener(new ActionListener() {
  		public void actionPerformed(ActionEvent e) {
  			login();
  		}
      });
	  bt_login.addKeyListener(new KeyAdapter() {
	  	public void keyPressed(KeyEvent e) {
	  		if(e.getKeyCode()==e.VK_ENTER){ 
	  			login();
	  		}	
	  	}
	   });
	   link.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            JOptionPane.showMessageDialog(LoginPanel.this, "카카오계정이란 모바일 카카오톡에서 등록한 이메일 주소입니다."+"\n" +"모바일 카카오톡의 '더보기>설정>개인/보안>카카오계정을 확인해주세요"); 
	        }
	  });
	  bt_find_pw.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
           JOptionPane.showMessageDialog(LoginPanel.this, "모바일 카카오톡에서 '더보기>설정>개인/보안>카카오계정'을 선택한 후"+"\n" +"계정 비밀번호 변경을 실행해주세요."); 
        }
	   });
	  
      kakaoMain.dragMouse(p_north);
      //pack();
      //setVisible(true);
      //setDefaultCloseOperation(EXIT_ON_CLOSE);
   }   
 
   public void login(){
	   ///////////서버연결//////////////
	 //  connect();
	   ///////////////////////////////
      PreparedStatement pstmt=null;
      ResultSet rs=null;
      ResultSet rs2=null;
     // String sql="select * from members where e_mail=? and password=?";
      //회원로그인시 필요한 정보 확인을 위한정보...
      
      String sql ="select * from members";
      //멤버테이블에 전체를 가져오기 위한 테이블
      Vector<MemberList> memberList=new Vector<MemberList>();
      
      //나와 내 친구 레코드 들어있는 테이블
      Vector<Friends> friendsList=new Vector<Friends>();
      
      try {
         pstmt=con.prepareStatement(sql);
         rs = pstmt.executeQuery();
         /* 회원 로그인시 필요한..정보...임시로막아둠..
         pstmt.setString(1, t_email.getText()); //내가 입력한 값
         pstmt.setString(2, t_pw.getText());
         
          */
         boolean loginFlag=false;
         
         while(rs.next()){
 			MemberList memberListDto = new MemberList();
			memberListDto.setE_mail(rs.getString("e_mail"));
			memberListDto.setNik_id(rs.getString("nik_id"));
			memberListDto.setPassword(rs.getString("password"));
			memberListDto.setProfile_img(rs.getString("profile_img"));
			memberListDto.setProfile_Back_Img(rs.getString("profile_back_img"));
			memberListDto.setStatus_msg(rs.getString("status_msg"));
			
			memberList.add(memberListDto);
			
			if(t_email.getText().equals(memberListDto.getE_mail()) && t_pw.getText().equals(memberListDto.getPassword())){
				loginFlag=true;
			}
         }            
         System.out.println("memberList에 들어 있는 사람 수: "+memberList.size()+"명");
         
         if(loginFlag){
        	 JOptionPane.showMessageDialog(this, "로그인성공");  
        	 connect();
        	 ct.sendID(t_email.getText());
    		
    		//내 친구정보(friends 테이블) 찾기
        	 //밑에 seeMain의 memberList자리에 
        	 //로그인 정보의 e_mail정보를 가져온다.
        	 String sql2="select * from friends where e_mail="+"\'"+t_email.getText()+"\'";
        	 System.out.println(sql2);
        	 pstmt=con.prepareStatement(sql2);
        	 rs2=pstmt.executeQuery();
        	 
        	 int cnt=0;
        	 while(rs2.next()){
	        	 Friends friendsListDto=new Friends();
	        	 friendsListDto.setE_mail(rs2.getString("e_mail"));
	        	 friendsListDto.setYour_email(rs2.getString("your_email"));
	        	 friendsList.add(friendsListDto);
	        	 cnt++;
	        	 System.out.println("내 친구수:"+cnt);
        	 }
        	 System.out.println("loginpanel 내친구수:"+friendsList.size());
        	 kakaoMain.seeMain(t_email.getText(),memberList, friendsList);
         }	else {
        	 JOptionPane.showMessageDialog(this, "아이디나 비밀번호를 확인해주세요.");
        	 t_pw.setText("");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         if(rs!=null){
            try {
               rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if(rs2!=null){
             try {
                rs2.close();
             } catch (SQLException e) {
                e.printStackTrace();
             }
          }     
         if(pstmt!=null){
            try {
               pstmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }//finally
   }
   public void getFriends(){
	  
   }
   
	public void connect(){
		
		try {
			socket=new Socket(ip, port);
			
			ct=new ClientThread(socket,kakaoMain);//chat 리스트를 넣어서 각 채팅방의 번호에 따라 대화를 넘겨주기
			//채팅방 리스트는 각 계정이 미리 알고 있어야하므로 db를 넣는다. 채팅방이 생성된 순서에따라 db에 넣어주고 그 순서대로 chat 리스트 생성후 저장
			//각 방의 리스트는 db(서버)에서 관리하며 각 프라이머리키를 갖고 있다. 서버에서 클라로 해당하는 사용자가 있을 경우 접속시 방의 정보를 넘겨준다.
			//넘겨준 정보를 바탕으로 접속전에 chat 리스트를 생성한다.
			ct.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
 
}

