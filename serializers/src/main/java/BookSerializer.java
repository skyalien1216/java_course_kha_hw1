import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BookSerializer implements JsonSerializer<Book> {
    @Override
    public JsonElement serialize(Book src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jObject = new JsonObject();
        jObject.addProperty("title", src.getTitle());
        jObject.addProperty("publication date", src.getPublicationDate());
        jObject.add("Author", new AuthorSerializer().serialize(src.getAuthor(), Author.class, context));
        return jObject;
    }
}

