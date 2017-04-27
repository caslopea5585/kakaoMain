package client.chat;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import util.Util;

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
		System.out.println("°ýÈ£¹Û!!"+ main.main.loginEmail);
		System.out.println("°ýÈ£¾È!! + " + main.main.chatMember.get(0));
		System.out.println("Ã³À½ °ªÀº?" + main.main.senderId);
		
		
			if(main.main.loginEmail.equals(chat.getMyId() )){//³ª¶û°°À¸¸é
				holder=rightHolder;
				
			}else{
				holder=leftHolder;

				holder.user_info.setIcon(Util.createRoundIcon(main.yourPhotoPath, 50));
				holder.la_user.setText(main.yourId);
				holder.add(holder.user_info);
				holder.add(holder.la_user);
				
				
			}
			holder.la_time.setText(timeValue);
			holder.chatbox.setText(msg);
			holder.readCount.setText(Integer.toString(count));
		
			holder.add(holder.chatbox);
			holder.add(holder.la_time);
			holder.add(holder.readCount);
	
		int iHeight = holder.chatbox.getPreferredSize().height + 20;
		iHeight = iHeight < 80 ? 80 : iHeight;
		
		int iH = table.getRowHeight(row);
		if (iH != iHeight) {
			table.setRowHeight(row, iHeight);
		}
		return holder;
	}

}