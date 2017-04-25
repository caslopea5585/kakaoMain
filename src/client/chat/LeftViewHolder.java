package client.chat;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.miginfocom.swing.MigLayout;

public class LeftViewHolder extends ChatHolder{
	public final Icon mBgIconLeft= new NinePatchImageIcon(this.getClass().getResource(
			"/msg_bg2.9.png"));
	public final Icon mHeadLeft= new ImageIcon(this.getClass().getResource(
			"/head1.png"));
	public LeftViewHolder() {
		this.setLayout(new MigLayout("ins 0"));
		chatbox.setBackgroundIcon(mBgIconLeft);
		user_info.setIcon(mHeadLeft);

		this.add(user_info, "span 2 3");
		this.add(la_user, "wrap");
		this.add(chatbox,"wmin 200px");
		this.add(readCount,"gapbottom 10,bottom");
		this.add(la_time, "gap left 0,bottom");
	}
}
