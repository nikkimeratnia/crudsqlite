import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BookDAOTest {
    @BeforeEach
    void setUp() throws SQLException {
        BookDAO.createTable();
        BookDAO.clearTable();
    }
    @Test
    void addBook_should_insert_book() throws SQLException {
        Book book=new Book(0,"farsi","nikki",2024,"test","123");
        BookDAO.addBook(book);
        List<Book> result=BookDAO.searchByTitle("farsi");
        assertFalse(result.isEmpty());
    }
    @Test
    void delete_book_should_remove_book() throws SQLException {
        Book book = new Book(0, "temp", "nikki", 2024, "test", "999");
        BookDAO.addBook(book);
        Book inserted = BookDAO.searchByTitle("temp").get(0);
        boolean deleted = BookDAO.deleteBook(inserted.getId());
        assertTrue(deleted);
    }
    @Test
    void updateBook_should_change_title() throws SQLException {
        Book book = new Book(0, "old", "nikki", 2025, "testi", "888");
        BookDAO.addBook(book);
        Book inserted = BookDAO.searchByTitle("old").get(0);
        Book updated=new Book(inserted.getId(),"new","nikki",2025,"testi","888");
        BookDAO.updateBook(updated);
        List<Book> result=BookDAO.searchByTitle("new");
        assertFalse(result.isEmpty());

    }
    @Test
    void searchByTitle_should_return_matching_books() throws SQLException {
        Book book=new Book(0,"java basic","nikki",2024,"tech","111");
        BookDAO.addBook(book);
        List<Book>result=BookDAO.searchByTitle("java");
        assertFalse(result.isEmpty());
        assertEquals("java basic",result.get(0).getTitle());


    }
}
