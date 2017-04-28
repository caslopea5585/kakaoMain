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
	//�̹������, ��»���� ������ �������� �߶��ִ� �Լ� 
	
	public static JLabel createRoundLabel(String photoPath, int size){
		//�̹��� �о����
		BufferedImage image=null; 
		try {
			image = ImageIO.read(Util.class.getResource(photoPath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//�̹��� �������� �ڸ���
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
	    
	    //�̹��� ũ�� ���̱�
	    Image tmp = output.getScaledInstance(size, size, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    
	    //����
	    JLabel label = new JLabel(new ImageIcon(dimg));
	    
		return label;
	}
	
	public static Image createRoundImage(String photoPath, int size){
		//�̹��� �о����
		BufferedImage image=null; 
		try {
			image = ImageIO.read(Util.class.getResource(photoPath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//�̹��� �������� �ڸ���
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
	    
	    //�̹��� ũ�� ���̱�
	    Image tmp = output.getScaledInstance(size, size, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    	    
		return dimg;
	}
	
	public static Icon createRoundIcon(String photoPath, int size){
		//�̹��� �о����
		BufferedImage image=null; 
		try {
			System.out.println("photoPath" + photoPath);
			image = ImageIO.read(Util.class.getResource(photoPath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//�̹��� �������� �ڸ���
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
	    
	    //�̹��� ũ�� ���̱�
	    Image tmp = output.getScaledInstance(size, size, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    	    
	    Icon icon = new ImageIcon(dimg);
	    
		return icon;
	}
}
