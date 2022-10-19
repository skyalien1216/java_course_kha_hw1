import java.util.List;

public class Library {

    private final List<Book> books;
    private final List<Author> authors;

    public List<Book> getBooks() {
        return books;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Library(List<Book> books, List<Author> authors) {
        this.books = books;
        this.authors = authors;
    }

    public Author findAuthorBySurname(String surname){
        return getAuthors().stream().filter(z-> z.getSurname().equals(surname)).findFirst().orElse(null);
    }

    public final List<Book> findBooksByAuthor(String surname){
        return books.stream().filter(x-> x.getAuthor().getSurname().toLowerCase().equals(surname.toLowerCase())).toList();
    }

    public void addBook(String title, Author a, String pubDate){
        Book b = new Book(-1, title, a, pubDate);
        SQLiteConnection.addBook(b);
        b.setId(SQLiteConnection.getBookIDFromDB(b));
        books.add(b);
    }

    public void addAuthor(String name, String surname,String birthday){
        Author a = new Author(-1, name, surname, birthday);
        SQLiteConnection.addAuthor(a);
        a.setId(SQLiteConnection.getAuthorIDFromDB(a));
        authors.add(a);
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                ", authors=" + authors +
                '}';
    }

}
