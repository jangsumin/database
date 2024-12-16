package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bookmall.vo.BookVo;

public class BookDao {
	// connect
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.64.3:3306/bookmall";
			conn = DriverManager.getConnection(url, "bookmall", "bookmall");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	// insert book
	public void insert(BookVo vo) {
		String insertQuery = "insert into book values (null, ?, ?, ?)";
		String selectQuery = "select last_insert_id() from dual";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(insertQuery);
				Statement stmt = conn.createStatement();) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setInt(2, vo.getPrice());
			pstmt.setInt(3, vo.getCategoryNo());
			pstmt.executeUpdate();

			ResultSet rs = stmt.executeQuery(selectQuery);
			if (rs.next()) {
				int no = rs.getInt(1);
				vo.setNo(no);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
	}

	// delete book
	public void deleteByNo(int no) {
		String sql = "delete from book where no = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
	}

}
