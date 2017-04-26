package client.chat;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.xml.ws.Holder;

public class ChatRenderer implements TableCellRenderer {
	ChatHolder holder;
	LeftViewHolder leftHolder= new LeftViewHolder();
	RightViewHolder rightHolder= new RightViewHolder();
	int count = 0;

	ChatMain main;
	Chat chat;
	
	//Vector<String> dto = new Vector<>();
	
	public ChatRenderer(ChatMain main) {
		this.main=main;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int col) {
		ChatHolder holder = null;
		Chat chat = (Chat)value;



		
		String myId = chat.getMyId();
		String msg = chat.getMsg();
		String timeValue = chat.getTimeValue();
		
		
		
		//멤버리스트 안됨...프렌즈 리스트로해야함..
//		if(main.main.memberList.get(0).getNik_id().equals(chat.getSender())){
		
		if(main.myId.equals(chat.getMyId())){//나랑같으면
			holder=rightHolder;
		}else{
			holder=leftHolder;
		}

		
		
		holder.la_user.setText(myId);
		holder.la_time.setText(timeValue);
		holder.chatbox.setText(msg);
		holder.readCount.setText(Integer.toString(count));
		int iHeight = holder.chatbox.getPreferredSize().height + 20;
		iHeight = iHeight < 80 ? 80 : iHeight;
		
		int iH = table.getRowHeight(row);
		if (iH != iHeight) {
			table.setRowHeight(row, iHeight);
		}
		return holder;
	}

}