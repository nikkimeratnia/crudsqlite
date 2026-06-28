import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private  static final Logger logger=LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            BookDAO.createTable();
            BookDAO.createBorrowTable();

            LibraryUI ui = new LibraryUI();

            ui.start();


        } catch (SQLException e) {
            logger.error("Database error", e);
            e.printStackTrace();
        }
    }
}