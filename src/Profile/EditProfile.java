package Profile;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class EditProfile extends JDialog implements ActionListener{
	Profile Profile;
	JPanel p_north, p_center, p_south;
	ImageIcon icon;
	BufferedImage profile;
	JTextField name;
	JTextArea status_msg;
	JButton bt_ok, bt_cancle;
	URL url;
	Canvas can;
	JFileChooser chooser;

	
	public EditProfile(Profile Profile) {
		this.Profile = Profile;
		this.url = Profile.url_profileImage;
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		
		can = new Canvas(){
			public void paint(Graphics g) {
		        Ellipse2D.Double ellipse1 = new Ellipse2D.Double(99,181,100,98); 
		        Area circle = new Area(ellipse1);
				try {
					profile = ImageIO.read(Profile.url_profileImage);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		        g.drawImage(profile, 0, 0, 100,100,this);  //백그라운드이미지
		        Profile.can_north_img.repaint();
		        g.setFont(new Font("돋움", Font.BOLD, 15));
		        g.setColor(Color.BLACK);
		       
		        g.drawString("편집하기", 23, 95);						//상태메시지

			}
		};
		
		name = new JTextField(Profile.la_name.getText());
		status_msg = new JTextArea(Profile.status_msg);
		System.out.println();
		
		
		bt_ok = new JButton("확인");
		bt_cancle = new JButton("취소");
		chooser = new JFileChooser("C:/java_workspace2/ExKakaoProject/res");
		
		p_north.add(can);
		
		p_center.add(name);
		p_center.add(status_msg);
		
		p_south.add(bt_ok);
		p_south.add(bt_cancle);
		
		
		
		can.setPreferredSize(new Dimension(100,100));
		name.setPreferredSize(new Dimension(280, 30));
		status_msg.setPreferredSize(new Dimension(280, 50));
		
		can.setSize(new Dimension(300, 100));
		p_center.setSize(new Dimension(300, 100));
		p_south.setSize(new Dimension(300, 50));
		
		
		
		add(p_north,BorderLayout.NORTH);
		add(p_center,BorderLayout.CENTER);
		add(p_south,BorderLayout.SOUTH);
		
		can.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result = chooser.showOpenDialog(can);
				if(result == JFileChooser.APPROVE_OPTION){
					File file = chooser.getSelectedFile();
					url=this.getClass().getResource("/"+file.getName()+""); //프로필사진
					Profile.url_profileImage=this.getClass().getResource("/"+file.getName()+""); //프로필사진
					can.repaint();
					can.imageUpdate(profile, can.PROPERTIES, 0, 0, 100, 100);
				}
				can.repaint();
				update(can.getGraphics());
			}
			
		});
		bt_cancle.addActionListener(this);
		bt_ok.addActionListener(this);
		p_north.setBackground(Color.WHITE);
		p_center.setBackground(Color.WHITE);
		p_south.setBackground(Color.WHITE);
		setVisible(true);
		setLocationRelativeTo(Profile);
		setSize(300,300);
		
		
	}
	public void dataSet(){
		Profile.la_name.setText(name.getText());
		Profile.status_msg=status_msg.getText();
		Profile.can_north_img.repaint();
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == bt_cancle){
			
		}else if(obj==bt_ok){
			dataSet();
			
		}
		dispose();
		
	}
	
}
