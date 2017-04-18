package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class DBContainer {
	DBManager manager=DBManager.getInstance();
	
	
	Connection con;
	
	public DBContainer() {
		con=manager.getConnection();
		
	}
	public void getMap(){
		
	}
}
