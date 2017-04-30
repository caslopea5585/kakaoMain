package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//정보 한곳에 두기. 데이터 베이스 계정 정보를 중복해서 기재하지 않기 위해
//인스턴스의 갯수를 한개만. 어플리케이션 가동 중 생성되는 커넥션 객체를 하나로 통일
public class DBManager {
	static private DBManager instance;

	private String driver="oracle.jdbc.driver.OracleDriver";
	private String url="jdbc:oracle:thin:@211.238.142.121:1521:XE";
	private String user="bread";
	private String pass="bread";
/*	
	private String driver="org.mariadb.jdbc.Driver";
	private String url="jdbc:mariadb://localhost:3306/kakao";
	private String user="root";
	private String pass="";
*/		
	
	private Connection con;
	
	private DBManager(){
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	static public DBManager getInstance(){
		if(instance==null){
			instance=new DBManager();
		}
		return instance;
	}
	
	public Connection getConnection(){
		return con;
	}
	
	public void disConnect(Connection con){

		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}