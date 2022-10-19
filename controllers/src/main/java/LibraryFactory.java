public class LibraryFactory {
    public static Library getLibrary(){
        return SQLiteConnection.getLibrary();
    }
}
