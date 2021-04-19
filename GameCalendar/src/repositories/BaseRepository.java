package repositories;

public class BaseRepository {
    protected Database db;
    protected BaseRepository() {
        db = new Database("jdbc:mysql://localhost:3306/?user=root", "root" , "1234", "game-calendar");
    }
}
