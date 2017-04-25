 package merge_sh_yk;

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

public class MyPanelRenderer implements TableCellRenderer{
	
	public JPanel createRoundPanel(String photoPath){
		//이미지 읽어오기
		BufferedImage image=null; 
		try {
			image = ImageIO.read(this.getClass().getResource(photoPath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//이미지 원형으로 자르기
		int w = image.getWidth();
	    int h = image.getHeight();
	    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2 = output.createGraphics();
	    g2.setComposite(AlphaComposite.Src);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, 250, 250));

	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);

	    g2.dispose();
	    
	    //이미지 크기 줄이기
	    Image tmp = output.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    
	    //부착
	    JLabel label = new JLabel(new ImageIcon(dimg));
		JPanel panel = new JPanel();
		panel.setBackground(new Color(247, 247, 247));
		panel.setPreferredSize(new Dimension(70, 70));
		panel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		panel.add(label);
		return panel;
	}
	
	public JPanel createLabelPanel(String name, String email){
		JPanel panel = new JPanel();
		JLabel la_name = new JLabel(name);
		JLabel la_email = new JLabel("계정 "+email);
		
		la_name.setPreferredSize(new Dimension(280, 20));
		la_email.setPreferredSize(new Dimension(280, 20));
		
		//panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(new Color(247, 247, 247));
		panel.setPreferredSize(new Dimension(300, 70));
		panel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
		panel.add(la_name);
		panel.add(la_email);
		return panel;
	}
	
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
    	JPanel panel = null; 

	    if(col==0){
	    	panel = createRoundPanel("/jeju2.jpg");
	    }else if(col==1){
	    	panel = createLabelPanel("조소희", "kirarenctaon@naver.com");
	    }
        
	    table.setRowHeight(80);
        return panel;
    }
}
