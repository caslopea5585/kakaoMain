package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client_chat extends JDialog implements ActionListener{
	JTextField t_input;
	JTextArea area;
	JScrollPane scroll;
	JButton bt;
	JPanel p_south;
	KakaoMain main;
	JFileChooser chooser;
	
	public Client_chat(KakaoMain main) {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		t_input=new JTextField(20);
		area=new JTextArea();
		scroll=new JScrollPane(area);
		bt=new JButton("열기");
		p_south=new JPanel();
		chooser=new JFileChooser();
		this.main=main;
		
		p_south.add(t_input);
		p_south.add(bt);
		add(scroll);
		add(p_south,BorderLayout.SOUTH);
		
		t_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				int key=e.getKeyCode();
				
				if(key==KeyEvent.VK_ENTER){
					String msg=t_input.getText();
					LoginPanel log=(LoginPanel)main.panel[0];
					log.ct.send(msg);
					t_input.setText("");
				}
			}
		});
		
		bt.addActionListener(this);
		setVisible(false);
		setSize(360,590);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			File file=chooser.getSelectedFile();
			//image 데이터 넘겨주기
			LoginPanel log=(LoginPanel)main.panel[0];
			log.ct.send(file);
		}
	}
}