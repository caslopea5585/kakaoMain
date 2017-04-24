package settingPanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class makeRoundedPhotoLabel {
	
	public static JLabel makeRoundedPhotoLabel(String photoPath) {

		BufferedImage image = null;
		try {
			URL url = new URL("file:C:/Users/sist117/git/kakaoMain/data"+photoPath);
			image = ImageIO.read(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
       		 
		int w = image.getWidth();
	    int h = image.getHeight();
	    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2 = output.createGraphics();
	    g2.setComposite(AlphaComposite.Src);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, 250, 250));

	    // ... then compositing the image on top,
	    // using the white shape from above as alpha source
	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);

	    g2.dispose();
	    
	    //이미지 크기 줄이기
	    Image tmp = output.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    
	    JLabel lable = new JLabel(new ImageIcon(dimg));
	    return lable;
	}
	/*
	public static void main(String[] args) throws IOException {
	     JOptionPane.showMessageDialog(null, makeRoundedPhotoLabel("/p1.jpg"));
	}*/
}
