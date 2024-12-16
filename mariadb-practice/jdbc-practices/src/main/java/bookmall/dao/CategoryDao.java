package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CategoryVo;

public class CategoryDao {

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

	// insert category
	public void insert(CategoryVo vo) {
		String insertQuery = "insert into category values(null, ?)";
		String selectQuery = "select last_insert_id() from dual";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(insertQuery);
				Statement stmt = conn.createStatement();) {

			pstmt.setString(1, vo.getCategoryName());
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

	// find all category
	public List<CategoryVo> findAll() {
		List<CategoryVo> list = new ArrayList<>();
		String sql = "select category_name from category";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String categoryName = rs.getString(1);

				CategoryVo vo = new CategoryVo();
				vo.setCategoryName(categoryName);

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return list;
	}

	// delete category
	public void deleteByNo(int no) {
		String sql = "delete from category where no = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
	}

}
