
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        //there are huxley, dostoevsky, wilde and tolstoy
        System.out.println("Enter the surname of the author: ");
        String surname = surname = new Scanner(System.in).nextLine();
        Library lib = LibraryFactory.getLibrary();

        List<Book> books = lib.findBooksByAuthor(surname);
        System.out.println(Serializer.serializeBooks(books));

        //adding an author to a library
        // lib.addAuthor("l. n.", "tolstoy", "09-09-1828");

        //adding a book to a library
        //Author a = lib.findAuthorBySurname("tolstoy");
        //if (a != null)
        //  lib.addBook("war and peace",a,"01-01-1865");
    }
}
