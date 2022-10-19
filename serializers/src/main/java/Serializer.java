import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {
    public static String serializeBooks(List<Book> books)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Author.class, new AuthorSerializer())
                .registerTypeAdapter(Book.class, new BookSerializer()).create();
        return gson.toJson(books);
    }
}
