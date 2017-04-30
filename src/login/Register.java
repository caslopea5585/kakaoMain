package login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.KakaoMain;
import main.MemberList;
import util.HintedPasswordField;
import util.HintedTextField;

//ȸ������
public class Register extends JDialog {
      JLabel la_title, la_checkId, la_checkPw;
      JPanel p_main, p_put;
      HintedTextField t_email, t_name;
      HintedPasswordField t_pw, t_pw_check;
      JButton bt;
      boolean flag=true;
      
      MemberList me;
      KakaoMain kakaoMain;
      private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
      
      public Register(KakaoMain kakaoMain) {
         this.kakaoMain=kakaoMain;
         
         la_title=new JLabel("Kakao Talk ����");
         la_checkId=new JLabel();
         la_checkPw=new JLabel();
         p_main=new JPanel();
         p_put=new JPanel();
         t_email = new HintedTextField("�̸����� �Է��ϼ���"); 
         t_name=new HintedTextField("�̸��� �Է��ϼ���");
         t_pw=new HintedPasswordField("��й�ȣ�� �Է��ϼ���");
         t_pw_check=new HintedPasswordField("��й�ȣ�� �ѹ� �� �Է��ϼ���");
         bt=new JButton("����");
         
         p_main.setLayout(new FlowLayout());
         p_main.setPreferredSize(new Dimension(360, 590));
         p_main.setBackground(new Color(255, 235, 51));
         
         la_title.setPreferredSize(new Dimension(360, 100));
         la_title.setHorizontalAlignment(JLabel.CENTER);
         la_title.setForeground(new Color(113, 92, 150));
         la_title.setFont(new Font("���", Font.BOLD, 20));
         p_put.setPreferredSize(new Dimension(270, 300));
         p_put.setBackground(new Color(255, 235, 51));
         p_put.setLayout(new GridLayout(8, 1));

         t_pw.setPreferredSize(new Dimension(270, 40));
         t_pw_check.setPreferredSize(new Dimension(270, 40));
             
         bt.setBackground(new Color(113, 92, 94));
         
         p_put.add(t_email);
         p_put.add(la_checkId);
         p_put.add(t_pw);
         p_put.add(t_pw_check);
         p_put.add(la_checkPw);
         p_put.add(t_name);
         p_put.add(bt);
         
         p_main.add(la_title);
         p_main.add(p_put);

         add(p_main);
         
         t_email.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
             if(e.getKeyCode()==KeyEvent.VK_ENTER){
                   System.out.println("emailCheck():"+emailCheck());
                   if(emailCheck()){
                      t_pw.requestFocus();
                   }
             }
          } 
         });
         /*
         t_email.addFocusListener(new FocusAdapter() {
           public void focusLost(FocusEvent e) {
            if(emailCheck()){
               if(emailCheck()){
                  t_pw.requestFocus();
                   }else{
                      t_email.requestFocus();
                   }
            }
           }
         });
         */
         t_pw.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                   t_pw_check.requestFocus();
                }
             } 
         });
         t_pw_check.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                   if(pwCheck()){
                      t_name.requestFocus();
                   }
                }
             } 
         });
         
         t_name.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                   bt.requestFocus();
                   regist();
                }
             } 
         });
         
         bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               regist();
            }
         });   
         bt.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
             if(e.getKeyCode()==e.VK_ENTER){
                regist();
             }
          }
         });
         setSize(380, 650);
         setVisible(flag);
      }

      public boolean emailCheck(){
         boolean flag_null=true;
         boolean flag_vali=false;
         boolean flag_redun = false;
         
         String input=t_email.getText();
         
         if(input.isEmpty()){
            JOptionPane.showMessageDialog(kakaoMain, "�̸��� �ּҸ� �Է����ּ���");
            flag_null=false;
         }
         
         //�̸��� ��ȿ�� �˻�
         flag_vali = Pattern.matches(EMAIL_PATTERN, t_email.getText());
         if(flag_vali==false){
            la_checkId.setText("�̸��� ���Ŀ� �����ʰų� �̹� ������Դϴ�.");
         } else if(flag_vali&&flag_null) {
            //�̸��� �ߺ��˻�
            PreparedStatement pstmt=null;
            ResultSet rs=null;
            String sql="select e_mail from members where e_mail=?";
            
            try {
               pstmt=kakaoMain.con.prepareStatement(sql);
               pstmt.setString(1, input);
               rs=pstmt.executeQuery();
               
               if(rs.next()){ //���ڵ尡 �ִٸ�
                  la_checkId.setText("�̸��� ���Ŀ� �����ʰų� �̹� ������Դϴ�.");
                  la_checkId.setForeground(Color.RED);
               } else { 
                  la_checkId.setText("��밡���� ���̵� �Դϴ�.");
                  la_checkId.setForeground(Color.BLUE);   
                  flag_redun=true;
               }
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }  
         
         return flag_vali&&flag_redun;
      }
      
      public boolean pwCheck(){
         boolean flag_pw=false;
         if(t_pw.getText().equals(t_pw_check.getText())){
            la_checkPw.setText("��й�ȣ�� ��ġ�մϴ�.");
            la_checkPw.setForeground(Color.BLUE);
            flag_pw=true;
         } else{
            la_checkPw.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
            la_checkPw.setForeground(Color.RED);
            flag_pw=false;
         }
         return flag_pw;
      }

      public boolean isNull(){  
         boolean flag_null=true;
         
         if(t_email.getText().equals("")){  
            JOptionPane.showMessageDialog(this, "�̸����� �Է����ּ���");
            flag_null=false;
         } else if(t_pw.getText().equals("")){
            JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է����ּ���");
            flag_null=false;
         } else if(t_pw_check.getText().equals("")){
            JOptionPane.showMessageDialog(this, "��й�ȣ�� �ٽ� �ѹ� �Է����ּ���");
            flag_null=false;
         } else if(t_name.getText().equals("")){
            JOptionPane.showMessageDialog(this, "�̸��� �Է����ּ���");
            flag_null=false;
         }
         return flag_null;
      }
      
      public void regist(){  
      if(isNull()){
         System.out.println("isNull()"+isNull());
         if(!emailCheck()){
         JOptionPane.showMessageDialog(this, "�̸��� ���Ŀ� �����ʰų� �̹� ������Դϴ�");
         }else if(!pwCheck()){
         JOptionPane.showMessageDialog(this, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
         }   
         
            if(isNull()&&pwCheck()&&emailCheck()){
               me=new MemberList();//�ν��Ͻ� �Ѱ� ����
               me.setE_mail(t_email.getText());
               me.setNik_id(t_name.getText());
               me.setPassword(t_pw.getText());
               
               PreparedStatement pstmt=null;
               int res;
               String sql="insert into MEMBERs (e_mail,NIK_ID,PASSWORD,PROFILE_IMG,PROFILE_BACK_IMG,STATUS_MSG) values(?,?,?,'/ryan1.png','/bg_north.png','')";
               
                  try {
                  pstmt=kakaoMain.con.prepareStatement(sql);
                  System.out.println("��� ���̵� = " + me.getE_mail());
                  System.out.println("��� �̸� = " + me.getNik_id());
                  System.out.println("��� �佺���� = " + me.getPassword());
                  
                  pstmt.setString(1, me.getE_mail());
                  pstmt.setString(2, me.getNik_id());
                  pstmt.setString(3, me.getPassword());
                  res=pstmt.executeUpdate();
             
                  if(res==1){
                     JOptionPane.showMessageDialog(this, "ȸ�����Լ���!");
                     this.dispose();
                  }
             
               } catch (SQLException e) {
                   e.printStackTrace();
                  JOptionPane.showMessageDialog(this, "ȸ�����Խ���!"); 
               } finally {
                 if(pstmt!=null){
                    try {
                     pstmt.close();
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
                 }
      
               }//finally
            } //try
      }//if
   }
}