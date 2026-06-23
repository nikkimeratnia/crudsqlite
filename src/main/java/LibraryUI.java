import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryUI {
    Scanner input = new Scanner(System.in);
    public void addBook() throws SQLException {

        System.out.println("title: ");
        String title = input.nextLine();
        System.out.println("author: ");
        String author = input.nextLine();
        System.out.println("year: ");
        int year = input.nextInt();
        input.nextLine();
        System.out.println("genre: ");
        String genre = input.nextLine();
        System.out.println("isbn: ");
        String isbn = input.nextLine();
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
            System.out.println("----------------");
        }

    }


    public void searchBook() throws SQLException {
        input.nextLine();
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
            System.out.println(i + ") " + b.getTitle() + " - " + b.getAuthor());
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

        System.out.println("Enter new author:");
        String newAuthor = input.nextLine();

        System.out.println("Enter new year:");
        int newYear = input.nextInt();
        input.nextLine();

        System.out.println("Enter new genre:");
        String newGenre = input.nextLine();

        System.out.println("Enter new isbn:");
        String newIsbn = input.nextLine();


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
            System.out.println(i + ") " + bookList.get(i).getTitle());
        }

        int index = input.nextInt();
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
        }
        for(int i=0;i<bookList.size();i++)
        {
            System.out.println(i+") "+bookList.get(i).getTitle());
        }
        System.out.println("choose book index");
        int index=input.nextInt();
        input.nextLine();
        Book selected=bookList.get(index);
        boolean result=BookDAO.borrowBook(selected.getId());
        if(result)
            System.out.println("book borrowed successfully");
        else System.out.println("borrow failed");

    }
    public void returnBook() throws SQLException {
        System.out.println("enter the book title to return");
        String title=input.nextLine();
        List<Book> bookList=BookDAO.searchByTitle(title);
        if(bookList.isEmpty()){
            System.out.println("no book found");
            return;
        }
        for(int i=0;i<bookList.size();i++){
            System.out.println(i+") "+bookList.get(i).getTitle());
        }
        System.out.println("choose book index");
        int index=input.nextInt();
        input.nextLine();
        Book selected=bookList.get(index);
        boolean result=BookDAO.returnBook(selected.getId());
        if(result)
            System.out.println("book returned succssfully");
        else
            System.out.println("returned failed");
    }


    public void start() throws SQLException {
        while (true) {
            System.out.println("-----Library Manager-----");
            System.out.println("1)Add book");
            System.out.println("2)Show all books");
            System.out.println("3)search book");
            System.out.println("4)update book");
            System.out.println("5)delete book");
            System.out.println("6) borrow book");
            System.out.println("7)return book");
            System.out.println("0)exit");
            System.out.println("please choose the action you want to do by entering the number of the menu");
            int choose = input.nextInt();

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
