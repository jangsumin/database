package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertEx01 {

	public static void main(String[] args) {
		insert();
	}
	
	public static boolean insert() {
		Connection conn = null;
		Statement stmt = null;
		boolean result = false;
		
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기 
			String url = "jdbc:mariadb://192.168.64.3:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			// 3. Statement 생성하기
			stmt = conn.createStatement();
			
			// 4. SQL 실행
			String sql = "insert into member values(null, 'js@gmail.com', password('12345'), '쟈스민', '개발팀')";
			int count = stmt.executeUpdate(sql);
			
			result = count == 1;
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}

