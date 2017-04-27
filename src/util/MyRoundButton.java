package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class MyRoundButton extends JButton{
	  
	   public MyRoundButton(Icon icon) {
	      super(icon);
	      
	      setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	      setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	      setContentAreaFilled(false);
		  setOpaque(false);
	   }
	   
	   protected void paintComponent(Graphics g) {
	      super.paintComponent(g);
	   }

	   @Override 
	    protected void paintBorder(Graphics g) {
	        Graphics2D g2 = (Graphics2D) g.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setPaint(getBackground());
	        g2.dispose();
	   }

	    @Override 
	    public boolean contains(int x, int y) {
	    	Shape shape = new Ellipse2D.Double(0, 0, getSize().width, getSize().height);
	    	return Objects.nonNull(shape) && shape.contains(x, y);
	    }

}
