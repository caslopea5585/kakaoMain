package merge_sh_yk;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class HintedPasswordField extends JPasswordField{
    private final JTextField hintField;

    public HintedPasswordField (String hint)
    {
        hintField = new JTextField (hint);
    }

    public void paintComponent (Graphics g){
        super.paintComponent (g);
        if (getPassword().length == 0){
            hintField.setBounds(getBounds());
            hintField.setForeground(Color.LIGHT_GRAY);
            hintField.setOpaque(false);
            hintField.paint(g);
        }
    }
}
