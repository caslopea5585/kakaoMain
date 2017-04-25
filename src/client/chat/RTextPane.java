package client.chat;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JTextArea;



public class RTextPane extends JTextArea{
	public Icon mBgIcon;
	
	public RTextPane() {
		setOpaque(false);
		setLineWrap(true);
		setWrapStyleWord(true);
	}
	
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
