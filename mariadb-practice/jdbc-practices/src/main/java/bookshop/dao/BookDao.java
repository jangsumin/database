package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.BookVo;

public class BookDao {
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
	
	// insert
	public int insert(BookVo vo) {
		int count = 0;
		String insertQuery = "insert into book(title, author_id) values(?, ?)";
		String selectQuery = "select last_insert_id() from dual";

		try (Connection conn = getConnection(); PreparedStatement pstmt1 = conn.prepareStatement(insertQuery); PreparedStatement pstmt2 = conn.prepareStatement(selectQuery); ){
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setLong(2, vo.getAuthorId());
			count = pstmt1.executeUpdate();
			
			ResultSet rs = pstmt2.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				vo.setAuthorId((long) id);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return count;
	}
	
	// delete
	public void deleteAll() {
		String sql = "delete from book";
		
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
	}

	// update
	public int update(Long id, String status) {
		int count = 0;
		String insertQuery = "update book set status = ? where id = ?";
		
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertQuery);){
			pstmt.setString(1, status);
			pstmt.setLong(2, id);
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return count;
	}

	// find all
	public List<BookVo> findAll() {
		List<BookVo> list = new ArrayList<>();
		String sql = "select a.id, a.title, b.name, a.status from book a join author b on a.author_id = b.id";
		
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String authorName = rs.getString(3);
				String status = rs.getString(4);
				
				BookVo vo = new BookVo();
				vo.setAuthorId(id);
				vo.setTitle(title);
				vo.setAuthorName(authorName);
				vo.setStatus(status);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return list;
	}
}
