package Profile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RoundButton extends JButton {
	ImageIcon icon;
	
	public RoundButton(ImageIcon icon) {
		super(icon);
		
		setContentAreaFilled(false);
	    setOpaque(false);
		setBorderPainted(true);
		
	}
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
	}
	  protected void paintBorder(Graphics g) {
		    g.setColor(new Color(233, 233, 233));
		    g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
		 }


	Shape shape;
	
	public boolean contains(int x, int y) {
		//원을 만들고 그 크기를 정하는 부분.
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
			
		}
		return shape.contains(x, y);
		
	}
	

}
