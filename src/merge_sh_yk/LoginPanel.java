package merge_sh_yk;



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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import db.DBManager;


public class LoginPanel extends JPanel implements ActionListener{
   KakaoMain kakaoMain;
   JPanel p_north, p_center, p_south, p_id_pw, p_pw;
   HintTextField_FIRST t_email;
   JTextField t_info;
   JPasswordField t_pw;
   JButton bt_login, bt_find_pw;
   Checkbox auto_login;
   JLabel info, link, temp;
   
   Canvas logo;
   BufferedImage image=null;
   URL url=this.getClass().getResource("/icon.png");
   
   Register register;
   Member member;//DBDTO
   
   Thread thread;
   Connection con;
   DBManager manager;
   
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
      p_pw=new JPanel();
            
      p_north.setPreferredSize(new Dimension(360, 250));
      p_center.setPreferredSize(new Dimension(360, 170));
      p_south.setPreferredSize(new Dimension(360, 170));
      p_id_pw.setPreferredSize(new Dimension(250, 100));
      p_pw.setPreferredSize(new Dimension(250, 55));
      
      p_north.setBackground(new Color(255, 235, 051));
      p_center.setBackground(new Color(255, 235, 051));
      p_south.setBackground(new Color(255, 235, 051));
      p_id_pw.setBackground(new Color(255, 235, 051));
      p_pw.setBackground(new Color(255, 235, 051));
      
      p_north.setLayout(new BorderLayout());
      p_center.setLayout(new FlowLayout());
      p_south.setLayout(new FlowLayout());
      p_id_pw.setLayout(new GridLayout(2,1));
      p_pw.setLayout(new FlowLayout());
      
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
      logo.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            register=new Register(kakaoMain);
         }
      });
      
      t_email=new HintTextField_FIRST("카카오계정(이메일)");
      t_info=new JTextField("비밀번호");
      t_info.setForeground(Color.GRAY);
      t_pw=new JPasswordField();
      bt_login=new JButton("로그인");
      auto_login=new Checkbox("잠금모드로 자동로그인");
      
      t_email.setPreferredSize(new Dimension(250, 25));
      t_info.setPreferredSize(new Dimension(250, 45));
      t_pw.setPreferredSize(new Dimension(250, 45));
      bt_login.setPreferredSize(new Dimension(250, 35));
      bt_login.setBackground(new Color(113, 92, 94));
      bt_login.addActionListener(this);
   
      info=new JLabel("모바일에서 카카오계정을 확인할 수 있습니다.");
      link= new JLabel("<HTML><U>카카오계정안내</U></HTML>");
      link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   
      link.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
             JOptionPane.showMessageDialog(LoginPanel.this, "카카오계정이란 모바일 카카오톡에서 등록한 이메일 주소입니다."+"\n" +"모바일 카카오톡의 '더보기>설정>개인/보안>카카오계정을 확인해주세요"); 
         }
      });
      
      temp=new JLabel();
      temp.setPreferredSize(new Dimension(300, 35));
      
      bt_find_pw=new JButton("비밀번호를 잊어버리셨나요?");
      bt_find_pw.setBackground(new Color(255, 235, 051));
      bt_find_pw.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(LoginPanel.this, "모바일 카카오톡에서 '더보기>설정>개인/보안>카카오계정'을 선택한 후"+"\n" +"계정 비밀번호 변경을 실행해주세요."); 
         }
      });
   
      t_info.addFocusListener(new FocusAdapter() {
         @Override
         public void focusGained(FocusEvent e) {
            t_info.setVisible(false);
            t_pw.setVisible(true);
            SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                  t_pw.requestFocus();
               }
             });
            p_pw.updateUI();
         }
         
         @Override
         public void focusLost(FocusEvent e) {
         }
      
      }); 

      t_pw.addFocusListener(new FocusListener() {
         
         @Override
         public void focusLost(FocusEvent e) {
            System.out.println("초점 잃음");
            t_pw.setVisible(false);
            t_info.setVisible(true);
            p_pw.updateUI();
         }
         
         @Override
         public void focusGained(FocusEvent e) {
            t_pw.setText("");
         }
      });
      
      p_north.add(logo);
      p_pw.add(t_info);
      p_pw.add(t_pw);
      p_id_pw.add(t_email);
      p_id_pw.add(p_pw);
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
      
       kakaoMain.dragMouse(p_north);
      //pack();
      //setVisible(true);
      //setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   

   public void login(){
      PreparedStatement pstmt=null;
      ResultSet rs=null;
      String sql="select * from member where e_mail=? and password=?";
      String input_email=t_email.getText();
      String input_pw=t_pw.getText();
      String ori_email=null;
      String ori_pw=null;
      
      try {
         pstmt=con.prepareStatement(sql);
         pstmt.setString(1, input_email); //내가 입력한 값
         pstmt.setString(2, input_pw); 
         rs=pstmt.executeQuery();
         
         while(rs.next()){
            member=new Member();//인스턴스 한건 생성
            member.setEmail(rs.getString("e_mail"));
            member.setName(rs.getString("nik_id"));
            member.setPw(rs.getString("password"));
         }            
         
         ori_email=member.getEmail();
         ori_pw=member.getPw();

         System.out.println("input_email="+input_email);
         System.out.println("ori_email"+ori_email);
         System.out.println("input_pw"+input_pw);
         System.out.println("ori_pw"+ori_pw);
         if((input_email.equals(ori_email))&&(input_pw.equals(ori_pw))){
            JOptionPane.showMessageDialog(this, "로그인성공");
            
         } else{
            JOptionPane.showMessageDialog(this, "로그인실패");
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
         
         if(pstmt!=null){
            try {
               pstmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }//finally
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      login();
       kakaoMain.panel[0].setVisible(false);
       kakaoMain.panel[1].setVisible(true);
         
       System.out.println("버튼");
   }

}
