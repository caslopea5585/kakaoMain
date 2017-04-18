package merge_sh_yk;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class ChangeProfile extends JDialog implements ActionListener{
	JTextField t_photo, t_status, t_name;
	JButton bt_change;
	
	String photo="1", status="2", name="3";
	FriendsListPanel friendsPanel;
	
	public ChangeProfile(FriendsListPanel friendsPanel){
		this.friendsPanel=friendsPanel;
		
		setLayout(new FlowLayout());
		t_name=new JTextField(10);
		t_photo=new JTextField(10);
		t_status=new JTextField(10);
		bt_change=new JButton("º¯°æ");
		
		add(t_name);
		add(t_photo);
		add(t_status);
		add(bt_change);
		
		bt_change.addActionListener(this);
		
		 this.setSize(150,200);
         this.setModal(true);
         this.setVisible(true);
	}
	public void addFriends(){
		name=t_name.getText();
		photo=t_photo.getText();
		status=t_status.getText();
		System.out.println(name+","+photo+","+status);
		PersonPanel personPanel=new PersonPanel(photo, name, status);
		friendsPanel.p_list.add(personPanel);
		friendsPanel.p_list.updateUI();
		//friendsPanel.updateUI();
	}

	public void actionPerformed(ActionEvent e) {
		addFriends();
	}
}
