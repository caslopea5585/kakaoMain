 package util;

<<<<<<< HEAD
import java.awt.BorderLayout;

import java.awt.Canvas;
import java.awt.Color;
=======
>>>>>>> 4919693f2a606d4f6080eb4d3b2b81929b7b85e7
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

<<<<<<< HEAD
import db.DBManager;
import Profile.Profile;

=======
>>>>>>> 4919693f2a606d4f6080eb4d3b2b81929b7b85e7
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
