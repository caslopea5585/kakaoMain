package client.chat;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

import com.android.ninepatch.NinePatch;

public class NinePatchImageIcon extends ImageIcon {

	public NinePatch mNinePatch;
	
	public NinePatchImageIcon(URL urlRes) {
		try {
			mNinePatch = NinePatch.load(urlRes, true);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		int iCompWidth = c.getWidth();
		int iCompHeigth = c.getHeight();
		mNinePatch.draw((Graphics2D) g, 0, 0,iCompWidth, iCompHeigth);
	}

}
