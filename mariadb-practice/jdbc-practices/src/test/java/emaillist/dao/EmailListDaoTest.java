package emaillist.dao;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import emaillist.dao.EmailListDao;
import emaillist.vo.EmailListVo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailListDaoTest {
	private static long count = 0;
	
	@BeforeAll
	public static void setup() {
		count = new EmailListDao().count();
	}
	
	@Test
	@Order(1)
	public void insertTest() {
		EmailListVo vo = new EmailListVo();
		vo.setFirstName("둘");
		vo.setLastName("리");
		vo.setEmail("dooly@gmail.com");
		
		Boolean result = new EmailListDao().insert(vo);
		assertTrue(result);
	}
	
	@Test
	@Order(2)
	public void findAllTest() {
		List<EmailListVo> list = new EmailListDao().findAll();
		assertEquals(count + 1, list.size());
	}
	
	@Test
	@Order(3)
	public void deleteByEmailTest() {
		Boolean result = new EmailListDao().deleteByEmail("dooly@gmail.com");
		assertTrue(result);
	}
}
