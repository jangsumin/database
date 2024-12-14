package bookmall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
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

	// insert order
	public void insert(OrderVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String insertQuery = "insert into orders values (null, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(insertQuery);
			pstmt.setString(1, vo.getNumber());
			pstmt.setInt(2, vo.getPayment());
			pstmt.setString(3, vo.getShipping());
			pstmt.setString(4, vo.getStatus());
			pstmt.setInt(5, vo.getUserNo());
			pstmt.executeUpdate();

			String selectQuery = "select last_insert_id() from dual";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectQuery);
			if (rs.next()) {
				int no = rs.getInt(1);
				vo.setNo(no);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// insert books
	public void insertBook(OrderBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			
			setBookTitle(vo);

			String sql = "insert into orders_book values (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getOrderNo());
			pstmt.setInt(2, vo.getBookNo());
			pstmt.setInt(3, vo.getPrice());
			pstmt.setInt(4, vo.getQuantity());
			pstmt.setString(5, vo.getBookTitle());

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
	
	public void setBookTitle(OrderBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select title from book where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getBookNo());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				String bookTitle = rs.getString(1);

				vo.setBookTitle(bookTitle);
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

	// find order
	public OrderVo findByNoAndUserNo(long no, int userNo) {
		OrderVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select number, payment, shipping, status from orders where no = ? and user_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.setInt(2, userNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				String number = rs.getString(1);
				int payment = rs.getInt(2);
				String shipping = rs.getString(3);
				String status = rs.getString(4);
				
				vo = new OrderVo();
				vo.setNumber(number);
				vo.setPayment(payment);
				vo.setShipping(shipping);
				vo.setStatus(status);
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

		return vo;
	}

	// find books
	public List<OrderBookVo> findBooksByNoAndUserNo(long no, int userNo) {
		List<OrderBookVo> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select o.book_no, o.price, o.quantity, o.book_title from orders_book as o, user as u where o.order_no = ? and u.no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.setInt(2, userNo);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				int bookNo = rs.getInt(1);
				int price = rs.getInt(2);
				int quantity = rs.getInt(3);
				String bookTitle = rs.getString(4);

				OrderBookVo vo = new OrderBookVo();
				vo.setBookNo(bookNo);
				vo.setPrice(price);
				vo.setQuantity(quantity);
				vo.setBookTitle(bookTitle);
				vo.setOrderNo(no);

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

	// delete order
	public void deleteByNo(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from orders where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

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

	// delete books
	public void deleteBooksByNo(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from orders_book where order_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

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
