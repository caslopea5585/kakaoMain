package merge_sh_yk;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextField;

public class HintedTextField extends JTextField {
	public final JTextField hintField;

    public HintedTextField (String hint){
        hintField = new JTextField (hint);
    }

    public void paintComponent (Graphics g){
        super.paintComponent (g);

        if(getText().isEmpty()){
            hintField.setBounds(getBounds());
            hintField.setForeground(Color.LIGHT_GRAY);
            hintField.setOpaque(false);
            hintField.paint(g);
        }
    }
}
