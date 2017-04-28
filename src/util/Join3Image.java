package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Join3Image{

    public static Icon createJoin3Image(String p1, String p2, String p3) {
       BufferedImage img1=null; 
       BufferedImage img2=null;
       BufferedImage img3=null; 
      try {
         img1 = ImageIO.read(Join3Image.class.getResource(p1));
         img2 = ImageIO.read(Join3Image.class.getResource(p2));
         img3 = ImageIO.read(Join3Image.class.getResource(p3));
      } catch (MalformedURLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
       
      BufferedImage joinedImg1 = longImage(img1);
        BufferedImage joinedImg2 = join2Image(img2,img3);
        BufferedImage joinedImg3 = join3Image(joinedImg1, joinedImg2);
        
       //이미지 원형으로 자르기
      int w = joinedImg3.getWidth();
       int h = joinedImg3.getHeight();
       BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

       Graphics2D g2 = output.createGraphics();
       g2.setComposite(AlphaComposite.Src);
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       g2.setColor(Color.WHITE);
       g2.fill(new RoundRectangle2D.Float(0, 0, w, h, 250, 250));
       g2.setComposite(AlphaComposite.SrcAtop);
       g2.drawImage(joinedImg3, 0, 0, null);
       g2.dispose();
       
       //이미지 크기 줄이기
       Image tmp = output.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
       BufferedImage dimg = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
       Graphics2D g2d = dimg.createGraphics();
       g2d.drawImage(tmp, 0, 0, null);
       g2d.dispose();
       
        Icon icon = new ImageIcon(dimg);
        
        return icon;
   }
    
    //입력된 모든 사진의 크기를 동일하게 50으로 만듬
    public static BufferedImage resizeImage(BufferedImage image){
       Image tmp = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
      BufferedImage newImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = newImage.createGraphics();
      g2d.drawImage(tmp, 0, 0, null);
      g2d.dispose();
  
       return newImage;
    }
    
    public static BufferedImage longImage(BufferedImage image){
       Image tmp = image.getScaledInstance(50, 100, Image.SCALE_SMOOTH);
      BufferedImage newImage = new BufferedImage(50, 100, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = newImage.createGraphics();
      g2d.drawImage(tmp, 0, 0, null);
      g2d.dispose();
  
       return newImage;
    }
    
    //두개의 이미지를 횡렬로 합침
    public static BufferedImage join2Image(BufferedImage img1,BufferedImage img2) {
        //이미지 크기 조정
       img1=resizeImage(img1);
       img2=resizeImage(img2);
        int wid = Math.max(img1.getWidth(),img2.getWidth());
        int height = img1.getHeight()+img2.getHeight();

        BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();

        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, 0, img1.getHeight());
        g2.dispose();
        return newImage;
    }
    
    //종렬 병합된 두개의 이미지를 횡렬로 병함함
    public static BufferedImage join3Image(BufferedImage img1,BufferedImage img2) {

        int wid = img1.getWidth()+img2.getWidth();
        int height = Math.max(img1.getHeight(),img2.getHeight());
    
        BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
      
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth(), 0);
        g2.dispose();
        return newImage;
    }
    


    
}