import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryUI {
    Scanner input = new Scanner(System.in);
    public void addBook() throws SQLException {

        System.out.println("title: ");
        String title = input.nextLine();
        if (title.isBlank()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        System.out.println("author: ");
        String author = input.nextLine();
        if (author.isBlank()) {
            System.out.println("author cannot be empty.");
            return;
        }
        System.out.println("year: ");
        String yearTxt=input.nextLine();
        if(yearTxt.isBlank()) {
            System.out.println("year cannot be empty");
            return;
        }
        int year;
            try {
                 year=Integer.parseInt(yearTxt);
            }
            catch (NumberFormatException e){
                System.out.println("year must be a number .");
                return;
            }



        System.out.println("genre: ");
        String genre = input.nextLine();
        if (genre.isBlank()) {
            System.out.println("genre cannot be empty.");
            return;
        }
        System.out.println("isbn: ");
        String isbn = input.nextLine();
        if (isbn.isBlank()) {
            System.out.println("isbn cannot be empty.");
            return;
        }
        Book book = new Book(0, title, author, year, genre, isbn);
        boolean result = BookDAO.addBook(book);
        if (result)
            System.out.println("book added successfully");
        else System.out.println("failed to add book");
    }
    public void showAllBooksss() throws SQLException {

        List<Book> bookList=BookDAO.showAllBooks();
        for(Book book:bookList){
            System.out.println("ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Year: " + book.getYear());
            System.out.println("Genre: " + book.getGenre());
            System.out.println("ISBN: " + book.getIsbn());
            boolean borrowed=BookDAO.isBorrowed(book.getId());
            if(borrowed)
                System.out.println("Status: Borrowed");
            else
                System.out.println("Status: Available");
            System.out.println("----------------");
        }

    }


    public void searchBook() throws SQLException {
        System.out.println("enter title to search");
        String title=input.nextLine();
        List<Book>bookList=BookDAO.searchByTitle(title);
        if(bookList.isEmpty()){
            System.out.println("no book found");
            return;
        }
        for(Book book:bookList){
            System.out.println("ID: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Year: " + book.getYear());
            System.out.println("Genre: " + book.getGenre());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("----------------");
        }


    }


    public void updateBook() throws SQLException {

        System.out.println("enter title of book to update:");
        String title = input.nextLine();


        List<Book> bookList = BookDAO.searchByTitle(title);

        if (bookList.isEmpty()) {
            System.out.println("no book found");
            return;
        }


        for (int i = 0; i < bookList.size(); i++) {
            Book b = bookList.get(i);
            System.out.println(i + ") "
                    + b.getTitle()
                    + " - "
                    + b.getAuthor()
                    + " ("
                    + b.getYear()
                    + ")");
        }


        System.out.println("choose book index to update:");
        int index = input.nextInt();
        input.nextLine();
        if(index < 0 || index >= bookList.size()) {
            System.out.println("invalid index");
            return;
        }

        Book oldBook = bookList.get(index);


        System.out.println("Enter new title:");
        String newTitle = input.nextLine();
        if (newTitle.isBlank()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.println("Enter new author:");
        String newAuthor = input.nextLine();
        if (newAuthor.isBlank()) {
            System.out.println("author cannot be empty.");
            return;
        }

        System.out.println("Enter new year:");
        String yearTxt = input.nextLine();

        if (yearTxt.isBlank()) {
            System.out.println("Year cannot be empty.");
            return;
        }

        int newYear;

        try {
            newYear = Integer.parseInt(yearTxt);
        } catch (NumberFormatException e) {
            System.out.println("Year must be a number.");
            return;
        }


        System.out.println("Enter new genre:");
        String newGenre = input.nextLine();
        if (newGenre.isBlank()) {
            System.out.println("genre cannot be empty.");
            return;
        }

        System.out.println("Enter new isbn:");
        String newIsbn = input.nextLine();
        if (newIsbn.isBlank()) {
            System.out.println("isbn cannot be empty.");
            return;
        }


        Book updatedBook = new Book(oldBook.getId(), newTitle, newAuthor, newYear, newGenre, newIsbn);

        boolean result = BookDAO.updateBook(updatedBook);

        if (result)
            System.out.println("Book updated successfully");
        else
            System.out.println("update failed");
    }


    public void deleteBook() throws SQLException {
        System.out.println("enter the title of the book you want to delete");
        String title= input.nextLine();
        List<Book>bookList=BookDAO.searchByTitle(title);
        if(bookList.isEmpty()) {
            System.out.println("no book found");
            return;
        }
        for (int i = 0; i < bookList.size(); i++) {
            Book b = bookList.get(i);
            System.out.println(i + ") "
                    + b.getTitle()
                    + " - "
                    + b.getAuthor()
                    + " ("
                    + b.getYear()
                    + ")");
        }

        int index = input.nextInt();
        input.nextLine();
        if(index < 0 || index >= bookList.size()) {
            System.out.println("invalid index");
            return;
        }
        Book selectedBook = bookList.get(index);
        System.out.println("deleting: "+selectedBook.getTitle());
        boolean result=BookDAO.deleteBook(selectedBook.getId());
        if(result)
            System.out.println("Book deleted successfully");
        else
            System.out.println("delete failed");

    }

    public void borrowBook() throws SQLException {
        System.out.println("enter the book title you want to borrow");
        String title=input.nextLine();
        List<Book> bookList=BookDAO.searchByTitle(title);
        if(bookList.isEmpty()) {
            System.out.println("no book found");
            return;
        }
        for (int i = 0; i < bookList.size(); i++) {
            Book b = bookList.get(i);
            System.out.println(i + ") "
                    + b.getTitle()
                    + " - "
                    + b.getAuthor()
                    + " ("
                    + b.getYear()
                    + ")");
        }
        System.out.println("choose book index");
        int index=input.nextInt();
        input.nextLine();
        if(index < 0 || index >= bookList.size()) {
            System.out.println("invalid index");
            return;
        }
        Book selected=bookList.get(index);
        boolean result=BookDAO.borrowBook(selected.getId());
        if(result)
            System.out.println("book borrowed successfully");
        else System.out.println("this book is already borrowed");

    }
    public void returnBook() throws SQLException {
        System.out.println("enter the book title to return");
        String title=input.nextLine();
        List<Book> bookList=BookDAO.searchByTitle(title);
        if(bookList.isEmpty()){
            System.out.println("no book found");
            return;
        }
        for (int i = 0; i < bookList.size(); i++) {
            Book b = bookList.get(i);
            System.out.println(i + ") "
                    + b.getTitle()
                    + " - "
                    + b.getAuthor()
                    + " ("
                    + b.getYear()
                    + ")");
        }
        System.out.println("choose book index");
        int index=input.nextInt();
        input.nextLine();
        if(index < 0 || index >= bookList.size()) {
            System.out.println("invalid index");
            return;
        }
        Book selected=bookList.get(index);
        if(!BookDAO.isBorrowed(selected.getId())){
            System.out.println("this book is currently borrowed");
            return;
        }
        boolean result=BookDAO.returnBook(selected.getId());
        if(result)
            System.out.println("book returned successfully");
        else
            System.out.println("this book is not currently borrowed");
    }


    public void start() throws SQLException {
        while (true) {
            System.out.println("-----Library Manager-----");
            System.out.println("1)Add book");
            System.out.println("2)Show all books");
            System.out.println("3)search book");
            System.out.println("4)update book");
            System.out.println("5)delete book");
            System.out.println("6)borrow book");
            System.out.println("7)return book");
            System.out.println("0)exit");
            System.out.println("please choose the action you want to do by entering the number of the menu");
            int choose = input.nextInt();
            input.nextLine();

            System.out.println("you choose: " + choose);
            if (choose == 0) {
                System.out.println("goodbye");
                break;
            }
            switch (choose) {
                case 1:
                    addBook();
                    break;

                case 2:
                    showAllBooksss();
                    break;

                case 3:
                    searchBook();
                    break;

                case 4:
                    updateBook();
                    break;

                case 5:
                   deleteBook();
                    break;
                case 6:
                    borrowBook();
                    break;
                case 7:
                    returnBook();
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
