package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateEx02 {

	public static void main(String[] args) {
		update(new MemberVo(8L, "경영지원팀"));
	}
	
	public static boolean update(MemberVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기 
			String url = "jdbc:mariadb://192.168.64.3:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			// 3. Statement 준비하기 
			String sql = "update member set dept = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. Parameter Binding
			pstmt.setString(1, vo.getDeptName());
			pstmt.setLong(2, vo.getId());
			
			// 5. SQL 실행
			int count = pstmt.executeUpdate();
			
			result = count == 1;
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (conn != null) conn.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}

