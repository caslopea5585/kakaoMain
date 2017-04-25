 package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import main.KakaoMain;

public class MyPanelRenderer implements TableCellRenderer{
	KakaoMain kakaoMain;
	int j=0; //나를 검색하기 위한 변수
	
	public MyPanelRenderer(KakaoMain kakaoMain) {
		this.kakaoMain=kakaoMain;
	}
	
	
	public JPanel createLabelPanel(String name, String email){
		JPanel panel = new JPanel();
		JLabel la_name = new JLabel(name);
		JLabel la_email = new JLabel("계정 : "+email);
		
		la_name.setPreferredSize(new Dimension(280, 20));
		la_email.setPreferredSize(new Dimension(280, 20));
		
		panel.setPreferredSize(new Dimension(300, 70));
		panel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		panel.add(la_name);
		panel.add(la_email);
		return panel;
	}
	
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
    	JPanel panel = null; 
    	
    	while( !(kakaoMain.loginEmail.equals(kakaoMain.memberList.get(j).getE_mail())) ){
			j++;
		}
    	
	    if(col==0){
	    	JLabel label=Util.createRoundLabel(kakaoMain.memberList.get(j).getProfile_img(), 50);
	    	panel = new JPanel();
	    	panel.setPreferredSize(new Dimension(70, 70));
	    	panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    	panel.add(label);
	    }else if(col==1){
	    	panel = createLabelPanel(kakaoMain.memberList.get(j).getNik_id(), kakaoMain.memberList.get(j).getE_mail());
	    }
        
	    table.setRowHeight(80);
        return panel;
    }
}
