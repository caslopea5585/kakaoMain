package client.chat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel{
	 ArrayList<Chat> list;
	
	public MyModel() {
		list = new ArrayList<Chat>();
	}
	
	public void addRow(Chat chat) {
		list.add(chat);
		
		int len = list.size();
		if(list.size()>3){
			System.out.println(list.get(0).getMyId());
			System.out.println(list.get(1).getMyId());
			System.out.println(list.get(2).getMyId());
		}
		//System.out.println(list.get(0));
		this.fireTableRowsInserted(len-1, len-1);
		System.out.println("fire���Ŀ� len�� ����: "+(len-1));

	}
	
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return 1;
	}

	
	public Object getValueAt(int row, int col) {
		System.out.println("�𵨼� ��" + list.get(row));
		return list.get(row);
	}
	
	
}
