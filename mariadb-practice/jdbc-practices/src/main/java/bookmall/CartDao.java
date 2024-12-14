package bookmall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
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

	// insert cart
	public void insert(CartVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			
			setBookTitleAndPrice(vo);

			String sql = "insert into cart values (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, vo.getQuantity());
			pstmt.setString(2, vo.getBookTitle());
			pstmt.setInt(3, vo.getPrice());
			pstmt.setInt(4, vo.getUserNo());
			pstmt.setInt(5, vo.getBookNo());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setBookTitleAndPrice(CartVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select title, price from book where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getBookNo());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				String bookTitle = rs.getString(1);
				int price = rs.getInt(2);

				vo.setBookTitle(bookTitle);
				vo.setPrice(price);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// find cart
	public List<CartVo> findByUserNo(int no) {
		List<CartVo> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select book_no, quantity, book_title, price from cart where user_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				int bookNo = rs.getInt(1);
				int quantity = rs.getInt(2);
				String bookTitle = rs.getString(3);
				int price = rs.getInt(4);

				CartVo vo = new CartVo();
				vo.setBookNo(bookNo);
				vo.setQuantity(quantity);
				vo.setBookTitle(bookTitle);
				vo.setPrice(price);

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// delete cart
	public void deleteByUserNoAndBookNo(int userNo, int bookNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from cart where user_no = ? and book_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, userNo);
			pstmt.setInt(2, bookNo);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
