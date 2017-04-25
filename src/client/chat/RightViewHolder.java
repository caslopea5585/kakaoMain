package client.chat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class RightViewHolder extends ChatHolder{
	public final Icon mBgIconRgh = new NinePatchImageIcon(this.getClass().getResource(
			"/msg_bg1.9.png"));
	public final Icon mHeadRight = new ImageIcon(this.getClass().getResource(
			"/head2.png"));
	public RightViewHolder() {
		this.setLayout(new MigLayout("ins 0, rtl"));
		chatbox.setBackgroundIcon(mBgIconRgh);
		la_user.setHorizontalAlignment(JLabel.RIGHT);
		this.add(chatbox, "wmin 200px");
		this.add(la_time, "bottom");
	}
}

