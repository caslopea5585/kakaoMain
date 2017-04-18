package merge_sh_yk;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HintTextField extends JTextField implements PropertyChangeListener, DocumentListener, KeyListener, FocusListener{
	   boolean isEmpty= true;
	   String hint;
	   
	   public HintTextField(String hint) {
	      this.hint=hint;
	      this.addKeyListener(this);
	      this.addFocusListener(this);
	      updateState();
	      setText(hint);
	        setForeground(Color.GRAY);
	   }
	   
	   public void updateState() {
	      isEmpty = (this.getText().length() == 0) ? true : false;
	    }

	   @Override
	   public void focusGained(FocusEvent e) {
	      this.setCaretPosition(0);
	      if(this.getText().equals(hint)){
	         //리스너삭제
	         this.getDocument().removeDocumentListener(this);
	         this.removePropertyChangeListener("foreground", this);
	         //텍스트박스설정
	         this.setText("");
	          this.setForeground(Color.BLACK);
	          //리스너 등록
	          this.getDocument().addDocumentListener(this);
	          this.addPropertyChangeListener("foreground", this);
	      }
	   }
	   
	   @Override
	   public void focusLost(FocusEvent e) {
	      if(isEmpty){
	         //기존에 적용된 리스너 해제
	         this.getDocument().removeDocumentListener(this);
	          this.removePropertyChangeListener("foreground", this);
	          //텍스트 박스 설정
	            this.setText(hint);
	            this.setForeground(Color.GRAY);
	            //리스너 적용
	            this.getDocument().addDocumentListener(this);
	            this.addPropertyChangeListener("foreground", this);
	      }
	   }
	   
	   @Override
	   public void propertyChange(PropertyChangeEvent evt) {
	      updateState();   
	   }

	   @Override
	   public void changedUpdate(DocumentEvent e) {
	      updateState();   
	   }

	   @Override
	   public void insertUpdate(DocumentEvent e) {
	      updateState();
	   }

	   @Override
	   public void removeUpdate(DocumentEvent e) {
	      updateState();
	   }

	   @Override
	   public void keyPressed(KeyEvent e) {
	      if(this.getText().equals(hint)){
	         //리스너삭제
	         this.getDocument().removeDocumentListener(this);
	         this.removePropertyChangeListener("foreground", this);
	         //텍스트박스설정
	         this.setText("");
	          this.setForeground(Color.BLACK);
	          //리스너 등록
	          this.getDocument().addDocumentListener(this);
	          this.addPropertyChangeListener("foreground", this);
	      }
	   }

	   @Override
	   public void keyReleased(KeyEvent e) {
	   }

	   @Override
	   public void keyTyped(KeyEvent e) {
	   }

	}
