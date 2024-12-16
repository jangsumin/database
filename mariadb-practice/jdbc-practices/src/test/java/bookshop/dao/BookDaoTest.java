package bookshop.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bookshop.vo.AuthorVo;
import bookshop.vo.BookVo;

public class BookDaoTest {
	private static AuthorDao authorDao = new AuthorDao();
	private static BookDao bookDao = new BookDao();
	private static AuthorVo mockAuthorVo = new AuthorVo();
	
	@BeforeAll
	public static void setup() {
		mockAuthorVo.setName("칼세이건");
		authorDao.insert(mockAuthorVo);
	}
	
	@Test
	public void insertTest() {
		BookVo vo = new BookVo();
		vo.setTitle("코스모스");
		vo.setAuthorId(mockAuthorVo.getId());
		
		bookDao.insert(vo);
	}
	
	@AfterAll
	public static void cleanup() {
		authorDao.deleteById(mockAuthorVo.getId());
		bookDao.deleteAll();
	}
}
