import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public final class SQLiteConnection {
    private static Connection conn = null;
    public static Connection getConnection() {
        return conn;
    }

    public static void connect() {
        try {
            if (isConnected() && !conn.isClosed())
                closeConnection();
            Class.forName("org.sqlite.JDBC");
            URL tmp = SQLiteConnection.class.getResource("library.db");
            assert tmp != null;
            String url = "jdbc:sqlite:" + tmp.getPath();
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public static void addAuthor(Author a) {
        if (!isConnected())
            connect();
        try {
            PreparedStatement stmt = getConnection().prepareStatement("insert into author (name, surname, birhtday) values (? , ? , ?);");
            stmt.setString(1,a.getName());
            stmt.setString(2,a.getSurname());
            stmt.setString(3,a.getBirthday());
            stmt.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getAuthorIDFromDB(Author a)
    {
        if (!isConnected())
            connect();
        try {
            PreparedStatement stmt = getConnection().prepareStatement("select id from author where surname = ? and name= ? and birhtday= ?;");
            stmt.setString(1,a.getSurname());
            stmt.setString(2,a.getName());
            stmt.setString(3,a.getBirthday());
            ResultSet rs = stmt.executeQuery();
            int id = rs.getInt("id");
            closeConnection();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getBookIDFromDB(Book b)
    {
        if (!isConnected())
            connect();
        try {
            PreparedStatement stmt = getConnection().prepareStatement("select id from book where title =? and publishing_date=?;");
            stmt.setString(1,b.getTitle());
            stmt.setString(2,b.getPublicationDate());
            ResultSet rs = stmt.executeQuery();
            int id = rs.getInt("id");
            closeConnection();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addBook(Book b) {
        if (!isConnected())
            connect();
        try {
            PreparedStatement stmt = getConnection().prepareStatement("insert into book (title, publishing_date) values (? , ? );");
            stmt.setString(1,b.getTitle());
            stmt.setString(2,b.getPublicationDate());
            stmt.executeUpdate();
            closeConnection();
            int id = getBookIDFromDB(b);
            connect();
            stmt = getConnection().prepareStatement("insert into authors_and_books (author_id, book_id) values (?,?);");
            stmt.setInt(1,b.getAuthor().getId());
            stmt.setInt(2,id);
            stmt.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Library getLibrary()
    {
        if (!isConnected())
            connect();
        ArrayList<Author> a = new ArrayList<>();
        ArrayList<Book> b = new ArrayList<>();
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from author;");
            while(rs.next())
                a.add(new Author( rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("birhtday")));
            closeConnection();
            connect();
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery("select book.id, author_id, title , publishing_date  from book join authors_and_books on book_id = book.id;");
            while(rs.next()){
                int bid =  rs.getInt("author_id");
                Author tmp = a.stream().filter(x-> x.getId() == bid).findFirst().orElse(null);
                b.add(new Book(rs.getInt("id"), rs.getString("title"), tmp , rs.getString("publishing_date") ));
            }
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Library(b, a);
    }

    public static boolean isConnected(){
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
