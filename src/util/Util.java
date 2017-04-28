package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Util {
	//이미지경로, 출력사이즈를 넣으면 원형으로 잘라주는 함수 
	
	public static JLabel createRoundLabel(String photoPath, int size){
		//이미지 읽어오기
		BufferedImage image=null; 
		try {
			image = ImageIO.read(Util.class.getResource(photoPath));
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
	    Image tmp = output.getScaledInstance(size, size, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    
	    //부착
	    JLabel label = new JLabel(new ImageIcon(dimg));
	    
		return label;
	}
	
	public static Image createRoundImage(String photoPath, int size){
		//이미지 읽어오기
		BufferedImage image=null; 
		try {
			image = ImageIO.read(Util.class.getResource(photoPath));
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
	    Image tmp = output.getScaledInstance(size, size, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    	    
		return dimg;
	}
	
	public static Icon createRoundIcon(String photoPath, int size){
		//이미지 읽어오기
		BufferedImage image=null; 
		try {
			System.out.println("photoPath" + photoPath);
			image = ImageIO.read(Util.class.getResource(photoPath));
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
	    Image tmp = output.getScaledInstance(size, size, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    	    
	    Icon icon = new ImageIcon(dimg);
	    
		return icon;
	}
}
