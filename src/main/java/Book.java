public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private String genre;
    private String isbn;
    public Book(int id,String title,String author,int year,String genre,String isbn){
        this.id=id;
        this.title=title;
        this.author=author;
        this.year=year;
        this.genre=genre;
        this.isbn=isbn;

    }
    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public int getYear(){
        return year;
    }
    public String getGenre(){
        return genre;
    }
    public String getIsbn(){
        return isbn;
    }
    public void setTitle(String title){
        this.title=title;

    }
    public void setAuthor(String author){
        this.author=author;
    }
    public void setYear(int year){
        this.year=year;
    }
    public void setGenre(String genre){
        this.genre=genre;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
