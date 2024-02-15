import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Подключение к базе данных
        String url = "jdbc:postgresql://localhost:5432/cinema_database";
        String user = "your_username";
        String password = "your_password";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Создание таблиц, если они еще не существуют
            createTables(connection);

            // Пример использования CRUD-операций
            User user1 = new User(1, "john_doe");
            Movie movie1 = new Movie(1, "Inception");
            Ticket ticket1 = new Ticket(1, user1.getUserId(), movie1.getMovieId());

            // Добавление пользователя, фильма и билета
            user1.addUser(connection);
            movie1.addMovie(connection);
            ticket1.addTicket(connection);

            // Просмотр пользователей, фильмов и билетов
            System.out.println("Users: " + User.getAllUsers(connection));
            System.out.println("Movies: " + Movie.getAllMovies(connection));
            System.out.println("Tickets: " + Ticket.getAllTickets(connection));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для создания таблиц, если они еще не существуют
    private static void createTables(Connection connection) throws SQLException {
        String createUserTable = "CREATE TABLE IF NOT EXISTS users (user_id SERIAL PRIMARY KEY, username VARCHAR(255));";
        String createMovieTable = "CREATE TABLE IF NOT EXISTS movies (movie_id SERIAL PRIMARY KEY, title VARCHAR(255));";
        String createTicketTable = "CREATE TABLE IF NOT EXISTS tickets (ticket_id SERIAL PRIMARY KEY, user_id INT REFERENCES users(user_id), movie_id INT REFERENCES movies(movie_id));";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUserTable);
            statement.executeUpdate(createMovieTable);
            statement.executeUpdate(createTicketTable);
        }
    }
}
