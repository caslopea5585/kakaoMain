package settingPanel;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTable;
 
public class SettingPanel extends JFrame{
	
	public SettingPanel(){
        JTable table = new JTable(10, 1);
        
        table. setPreferredSize(new Dimension(360, 720));
        table.setBackground(Color.WHITE);    
        table.setDefaultRenderer(Object.class, new MyTableCellRenderer());
      
        table.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent e){
        		int row=table.rowAtPoint(e.getPoint());
    			
        		if(row==0){
    				System.out.println("0¹øÂïÀ½");
    			} else if(row==1){
    				openWebpage("https://pc.kakao.com/talk/notices/ko");
    			} else if(row==2){
    				openWebpage("https://e.kakao.com/?referer=pc_more");
    			} else if(row==3){
    				System.out.println("¿ÀÇÂÃ¤ÆÃ");
    			} else if(row==4){
    				openWebpage("http://www.kakao.com/helps?category=24&device=15&locale=ko&service=8");
    			} else if(row==5){
    				openWebpage("https://pc.kakao.com/talk/notices/ko");
    			}
        	}	
        });
             
        add( table );
        
        pack();
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLocationRelativeTo( null );
        setVisible(true);
    }
 
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    public static void main(String[] args) {
        new SettingPanel();
    }
}