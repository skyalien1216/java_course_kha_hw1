import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class AuthorSerializer implements JsonSerializer<Author> {
    @Override
    public JsonElement serialize(Author src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jObject = new JsonObject();
        if (!src.getName().equals(""))
            jObject.addProperty("name", src.getName());
        jObject.addProperty("surname", src.getSurname());
        jObject.addProperty("date of birth", src.getBirthday());
        return jObject;
    }
}
