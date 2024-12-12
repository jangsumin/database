package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectEx02 {

	public static void main(String[] args) {
		List<MemberVo> result = search("팀");
		for (MemberVo vo : result) {
			System.out.println(vo);
		}
	}
	
	public static List<MemberVo> search(String keyword) {
		List<MemberVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 1. JDBC Driver 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기 
			String url = "jdbc:mariadb://192.168.64.3:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			// 3. Statement 준비하기 
			String sql = "select id, dept from member where dept like ?";
			pstmt = conn.prepareStatement(sql);
			
			// 4. Parameter Binding
			pstmt.setString(1, "%" + keyword + "%");
			
			// 5. SQL 실행
			rs = pstmt.executeQuery(); // insert, update, delete와 다른 메서드를 써야 함
			
			// 6. 결과 처리
			while (rs.next()) {
				Long id = rs.getLong(1);
				String deptName = rs.getString(2);
				
				MemberVo vo = new MemberVo(id, deptName);
				result.add(vo);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (conn != null) conn.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}

