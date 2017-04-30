package client.chat;

import java.awt.Component;
import java.util.Vector;

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
		holder = null;
		chat = (Chat)value;
		
		//쳇dto의 멤버리스트는!! 0번쨰는 자기 자신!!
		Vector<String>chatMember = chat.getChatMember();

		String myId = chat.getMyId();
		String msg = chat.getMsg();
		
		String timeValue = chat.getTimeValue();
	/*	System.out.println("괄호밖!!"+ main.main.loginEmail);
		System.out.println("괄호안!! + " + main.main.chatMember.get(0));
		System.out.println("처음 값은?" + main.main.senderId);*/
		String yourId = chat.getYourId();
		String yourPhotoPath=main.main.memberList.get(0).getProfile_img();
		int count=chat.getCount();
		
		int index = chat.getIndex();
		
		//그 인덱스의 경로를 가져온다.
		
		for(int i=1; i<main.main.chatMember.size() ;i++){
			
			
		}
/*		
		for(int i=0; i<main.main.memberList.size();i++){
			if(yourId.equals(main.main.memberList.get(i).getE_mail())){
				yourPhotoPath = main.main.memberList.get(i).getProfile_img();
				yourId = main.main.memberList.get(i).getE_mail();
				System.out.println("포토 패스 "+ yourPhotoPath+"유저 아이디 "+yourId);
			}
		}*/
		
			System.out.println(chat.getMyId()+"들어오는 값은??");
			String id = chat.getMyId();
			
			for(int i=0;i<main.main.memberList.size();i++){
				if(id.equals(main.main.memberList.get(i).getE_mail())){
					yourPhotoPath = main.main.memberList.get(i).getProfile_img();
				}
			}
			
			if(main.main.loginEmail.equals(chat.getMyId() )){//나랑같으면
				holder=rightHolder;
				
			}else{
				holder=leftHolder;

				holder.user_info.setIcon(Util.createRoundIcon(yourPhotoPath, 50));
				holder.la_user.setText(id);
				holder.add(holder.user_info);
				holder.add(holder.la_user);
				
				
			}
			holder.la_time.setText(timeValue);
			holder.chatbox.setText(msg);
			if(count!=0){
				holder.readCount.setText(Integer.toString(count));
			}
			else{
				holder.readCount.setText("");
			}
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