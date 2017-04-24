package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SettingPanel extends JPanel{
	
	public SettingPanel(){
		JTable table1 = new JTable(1, 2);
        JTable table2 = new JTable(7, 1);
        
        MyPanelRenderer renderer1 = new MyPanelRenderer();
        MyLabelRenderer renderer2 = new MyLabelRenderer();
        
        //내 프로필 테이블 설정
        table1.setDefaultRenderer(Object.class, renderer1);
        table1.getColumnModel().getColumn(0).setCellRenderer(renderer1);
        table1.getColumnModel().getColumn(0).setPreferredWidth(80);
        table1.getColumnModel().getColumn(1).setCellRenderer(renderer1);
        table1.getColumnModel().getColumn(1).setPreferredWidth(300);
        table1.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent e){
        		int col=table1.columnAtPoint(e.getPoint());
        		if(col==0){
    				System.out.println("사진누름");
    			} 
        	}	
        });
        table1.setShowVerticalLines(false);
        table1.setShowHorizontalLines(false);
        table1.setBackground(new Color(247, 247, 247));
        
        //세팅패널테이블 설정
        table2.setDefaultRenderer(Object.class, renderer2);  
        table2.getColumnModel().getColumn(0).setCellRenderer(renderer2);
        table2.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent e){
        		int row=table2.rowAtPoint(e.getPoint());
        		if(row==0){
    				openWebpage("https://pc.kakao.com/talk/notices/ko");
    			} else if(row==1){
    				openWebpage("https://e.kakao.com/?referer=pc_more");
    			} else if(row==2){
    				JOptionPane.showMessageDialog(null, "서비스 준비중입니다.");
    			} else if(row==3){
    				openWebpage("http://www.kakao.com/helps?category=24&device=15&locale=ko&service=8");
    			} else if(row==4){
    				JOptionPane.showMessageDialog(null, "서비스 준비중입니다.");
    			} else if(row==5){
    				JLabel la=renderer2.createLabel("/ver_info.png");
    				JOptionPane.showMessageDialog(null, la);
    			} else if(row==6){
    				JOptionPane.showMessageDialog(null, "서비스 준비중입니다.");
       			} 
        	}	
        });
        table2.setBackground(Color.WHITE);
        table2.setTableHeader(null);
        
        setLayout(new BorderLayout());
        add(table1, BorderLayout.NORTH);
        add(table2);
        
		setPreferredSize(new Dimension(360, 497));
		setBackground(Color.WHITE);
		//setPage(2);
	}
	
	public void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
