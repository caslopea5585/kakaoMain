package Profile;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JDialog;

import merge_sh_yk.KakaoMain;

public class ProfileImage extends JDialog{
	Profile profile;
	Canvas can;
	public ProfileImage(Profile profile) {
		this.profile = profile;
		
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage((Image)profile.buffr_profile,0, 0, 300, 300, this);
			}
		};
		
		add(can);
		
		setVisible(true);
		setLocationRelativeTo(profile);
		pack();
		setSize(400, 400);
	}
}
