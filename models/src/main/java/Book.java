import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
@AllArgsConstructor
@Data
public class Book {

    private int id;
    private String title;
    private Author author;
    private String publicationDate;

}
