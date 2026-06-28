import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Result;

public class BookDAO {
    private  static final Logger logger=LoggerFactory.getLogger(BookDAO.class);
    public static void createTable() throws SQLException {
        try(
        Connection connection=DatabaseManager.getConnectionn();
        Statement s= connection.createStatement();
        ) {
            String sql = "CREATE TABLE if not exists books(id integer primary key autoincrement,title text not null,author text not null,year integer,genre text,isbn text unique)";
            s.executeUpdate(sql);
        }


    }
    public static void createBorrowTable() throws SQLException {
        String sql="""
        CREATE TABLE IF NOT EXISTS borrowed_books (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            book_id INTEGER,
            status TEXT not null,
            foreign key(book_id) references books(id)
        )
    """;
        try(Connection connection=DatabaseManager.getConnectionn();
        Statement st=connection.createStatement()){
            st.executeUpdate(sql);
        }
    }



    public static boolean isBorrowed(int bookId) throws SQLException {
        String sql="Select * from borrowed_books where book_id=? and status='borrowed'";
        try (Connection connection=DatabaseManager.getConnectionn();
             PreparedStatement ps=connection.prepareStatement(sql);
        ){
            ps.setInt(1,bookId);
            ResultSet rs= ps.executeQuery();
            return rs.next();
        }
    }
    public static boolean addBook(Book book) throws SQLException {
        String sql="insert into books(title,author,year,genre,isbn)values (?,?,?,?,?)";
        try (
        Connection connection=DatabaseManager.getConnectionn();
        PreparedStatement ps=connection.prepareStatement(sql);) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.setString(4, book.getGenre());
            ps.setString(5, book.getIsbn());
            int result = ps.executeUpdate();
            if(result>0){
                logger.info("Book added successfully: "+book.getTitle());
                return true;
            }
            else{
                logger.warn("Book NOT added: "+ book.getTitle());
                return false;
            }

        }
        catch (SQLException e){
            logger.error("error while adding book: "+book.getTitle());
            throw  e;
        }
    }
    public static List<Book> showAllBooks() throws SQLException {
        String sql="SELECT *FROM books";
        logger.info("fetching all books from database...");
        try (
        Connection connection=DatabaseManager.getConnectionn();
        Statement s=connection.createStatement();
        ResultSet rs=s.executeQuery(sql);) {
            List<Book> bookList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                String genre = rs.getString("genre");
                String isbn = rs.getString("isbn");
                Book nbook = new Book(id, title, author, year, genre, isbn);
                bookList.add(nbook);


            }
            logger.info("fetched books count: "+bookList.size());
            return bookList;

        }
        catch(SQLException e){
            logger.error("error while fetching books from databas",e);
            throw e;
        }
    }
    public static boolean deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books where id=?";
        try(
        Connection connection = DatabaseManager.getConnectionn();
        PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result > 0) {
                logger.info("Book deleted successfully with id:" + id);
                return true;
            } else {
                logger.warn("no book found to delete with id: " + id);
                return false;
            }
        }
            catch(SQLException e) {
            logger.error("error while deleting book with id: "+id,e);
            throw e;
            }


    }
    public static boolean updateBook(Book book) throws SQLException {
        String sql="update books set title=?,author=?,year=?,genre=?,isbn=? where id=?";
        try (
                Connection connection=DatabaseManager.getConnectionn();
        PreparedStatement ps=connection.prepareStatement(sql);
        ) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            ps.setString(4, book.getGenre());
            ps.setString(5, book.getIsbn());
            ps.setInt(6, book.getId());
            int result = ps.executeUpdate();
           if (result>0){
               logger.info("Book updated successfully: "+book.getId());
               return true;
           }
           else {
               logger.warn("no book found with this id: "+book.getId());
               return false;
           }

        }
        catch (SQLException e){
            logger.error("error while updating book with id: "+book.getId(),e);
            throw e;
        }

    }
    public static List<Book> searchByTitle(String title) throws SQLException {
        String sql = "SELECT * from books where title like ?";
        List<Book> bookList = new ArrayList<>();
        logger.info("Searching books with title like: "+title);
        try (
                Connection connection = DatabaseManager.getConnectionn();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title1 = rs.getString("title");
                    String author = rs.getString("author");
                    int year = rs.getInt("year");
                    String genre = rs.getString("genre");
                    String isbn = rs.getString("isbn");

                    Book book = new Book(id, title1, author, year, genre, isbn);
                    bookList.add(book);
                }
            }
            logger.info("Search completed. found " + bookList.size() + " books for: " + title);
            return bookList;
        }
        catch (SQLException e){
            logger.error("error while searching book with title: "+title,e);
            throw e;
        }
    }
    public static void clearTable() throws SQLException {
        try(Connection connection=DatabaseManager.getConnectionn();
        Statement s=connection.createStatement();) {
            logger.info("Clearing books table...");
            int result=s.executeUpdate("DELETE FROM books");
            logger.info("Books table cleared successfully. rows affacted: "+result);

        }catch(SQLException e){
            logger.info("error while clearing books table",e);
            throw e;
        }
    }
    public static boolean borrowBook(int bookId) throws SQLException {
        if(isBorrowed(bookId)){
            logger.warn("Book is already borrowed: "+bookId);
            return false;
        }
        String sql = "INSERT INTO borrowed_books(book_id, status) VALUES (?, 'borrowed')";

        try (Connection connection = DatabaseManager.getConnectionn();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            int result = ps.executeUpdate();

            if (result > 0) {
                logger.info("Book borrowed successfully: " + bookId);
                return true;
            } else {
                logger.warn("Borrow failed for book: " + bookId);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error while borrowing book: " + bookId, e);
            throw e;
        }
    }
    public static boolean returnBook(int bookId) throws SQLException {
        String sql = "UPDATE borrowed_books SET status = 'returned' WHERE book_id = ? AND status = 'borrowed'";

        try (Connection connection = DatabaseManager.getConnectionn();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            int result = ps.executeUpdate();

            if (result > 0) {
                logger.info("Book returned successfully: " + bookId);
                return true;
            } else {
                logger.warn("Return failed (maybe not borrowed): " + bookId);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Error while returning book: " + bookId, e);
            throw e;
        }
    }


}
