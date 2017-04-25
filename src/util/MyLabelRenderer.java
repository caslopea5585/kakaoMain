 package util;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import db.DBManager;
import profile.Profile;

public class MyLabelRenderer implements TableCellRenderer{

	String[] list={
			"/setting_1.png", "/setting_2.png", "/setting_3.png", "/setting_4.png", "/setting_5.png", "/setting_6.png", "/setting_7.png" 
	};
	
	public JLabel createLabel(String photoPath){
		BufferedImage image=null; 

		try {
			image = ImageIO.read(this.getClass().getResource(photoPath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel label = new JLabel(new ImageIcon(image));
		return label;
	}
	
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, final int row, final int column){
    	JLabel label = null; 

        for(int i=0; i<list.length; i++){
	        if(row==i){
	        	label = createLabel(list[i]);
	        }
        }
 
        table.setRowHeight(50);  
        return label;
    }
}
