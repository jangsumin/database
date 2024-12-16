package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

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
		String insertQuery = "insert into orders values (null, ?, ?, ?, ?, ?)";
		String selectQuery = "select last_insert_id() from dual";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(insertQuery);
				Statement stmt = conn.createStatement();) {
			pstmt.setString(1, vo.getNumber());
			pstmt.setInt(2, vo.getPayment());
			pstmt.setString(3, vo.getShipping());
			pstmt.setString(4, vo.getStatus());
			pstmt.setInt(5, vo.getUserNo());
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

	// insert books
	public void insertBook(OrderBookVo vo) {
		String sql = "insert into orders_book values (?, ?, ?, ?, ?)";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			setBookTitle(vo);

			pstmt.setLong(1, vo.getOrderNo());
			pstmt.setInt(2, vo.getBookNo());
			pstmt.setInt(3, vo.getPrice());
			pstmt.setInt(4, vo.getQuantity());
			pstmt.setString(5, vo.getBookTitle());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

	}

	public void setBookTitle(OrderBookVo vo) {
		String sql = "select title from book where no = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, vo.getBookNo());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String bookTitle = rs.getString(1);

				vo.setBookTitle(bookTitle);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// find order
	public OrderVo findByNoAndUserNo(long no, int userNo) {
		OrderVo vo = null;
		String sql = "select number, payment, shipping, status from orders where no = ? and user_no = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setLong(1, no);
			pstmt.setInt(2, userNo);

			ResultSet rs = pstmt.executeQuery();
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
		}

		return vo;
	}

	// find books
	public List<OrderBookVo> findBooksByNoAndUserNo(long no, int userNo) {
		List<OrderBookVo> list = new ArrayList<>();
		String sql = "select o.book_no, o.price, o.quantity, o.book_title from orders_book as o, user as u where o.order_no = ? and u.no = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setLong(1, no);
			pstmt.setInt(2, userNo);

			ResultSet rs = pstmt.executeQuery();
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
		}

		return list;
	}

	// delete order
	public void deleteByNo(long no) {
		String sql = "delete from orders where no = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setLong(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
	}

	// delete books
	public void deleteBooksByNo(long no) {
		String sql = "delete from orders_book where order_no = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setLong(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
	}
}
