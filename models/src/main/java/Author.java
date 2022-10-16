import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Builder
@Data
public class Author {
    private int id;
    private String name;
    @NotNull
    private String surname;
    private String birthday;
}
