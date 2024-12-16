package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookshop.vo.AuthorVo;

public class AuthorDao {
	public int insert(AuthorVo vo) {
		int count = 0;
		String insertQuery = "insert into author values(null, ?)";
		String selectQuery = "select last_insert_id() from dual";

		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(insertQuery);
				PreparedStatement pstmt2 = conn.prepareStatement(selectQuery);) {
			pstmt1.setString(1, vo.getName());
			count = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				vo.setId((long) id);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return count;
	}

	// connect
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.64.3:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	// delete
	public void deleteById(long id) {
		String deleteQuery = "delete from author where id = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery);) {
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

	}

	// delete all
	public void deleteAll() {
		String sql = "delete from author";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
	}
}
