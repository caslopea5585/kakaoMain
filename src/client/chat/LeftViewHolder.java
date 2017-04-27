package client.chat;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class LeftViewHolder extends ChatHolder{
	public final Icon mBgIconLeft= new NinePatchImageIcon(this.getClass().getResource(
			"/msg_bg2.9.png"));

	public LeftViewHolder() {
		this.setLayout(new GridLayout(2, 1));
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		chatbox.setBackgroundIcon(mBgIconLeft);
/*		user_info.setIcon(mHeadLeft);

		this.add(user_info, "span 2 3");
		this.add(la_user, "wrap");
		this.add(chatbox,"wmin 200px");
		this.add(readCount,"gapbottom 10,bottom");
		this.add(la_time, "gap left 0,bottom");*/
	}
}
