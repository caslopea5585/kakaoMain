package client.chat;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JLabel;



public class RLabel extends JLabel{
	public Icon mBgIcon;
	
	public void setBackgroundIcon(Icon icon) {
		mBgIcon = icon;
	}
	

	protected void paintComponent(Graphics g) {
		if (mBgIcon != null) {
			mBgIcon.paintIcon(this, g, 0, 0);
		}
		super.paintComponent(g);
	}
	

	public Insets getInsets() {
		return new Insets(10,10,10,10);
	}
	
}
