package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.UserVo;

public class UserDao {
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

	// insert user
	public void insert(UserVo vo) {
		String insertQuery = "insert into user values (null, ?, ?, ?, ?)";
		String selectQuery = "select last_insert_id() from dual";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(insertQuery);
				Statement stmt = conn.createStatement();) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getPhoneNumber());
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

	// find all user
	public List<UserVo> findAll() {
		List<UserVo> list = new ArrayList<>();
		String sql = "select name, email, password, phone_number from user";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String phoneNumber = rs.getString(4);

				UserVo vo = new UserVo();
				vo.setName(name);
				vo.setEmail(email);
				vo.setPassword(password);
				vo.setPhoneNumber(phoneNumber);

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return list;
	}

	// delete user
	public void deleteByNo(int no) {
		String sql = "delete from user where no = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
	}

}
