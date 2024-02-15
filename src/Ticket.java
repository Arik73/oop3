import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Ticket {
    private int ticketId;
    private int userId;
    private int movieId;

    public Ticket(int ticketId, int userId, int movieId) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.movieId = movieId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void addTicket(Connection connection) throws SQLException {
        String query = "INSERT INTO tickets (ticket_id, user_id, movie_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, getTicketId());
            statement.setInt(2, getUserId());
            statement.setInt(3, getMovieId());
            statement.executeUpdate();
        }
    }

    public static String getAllTickets(Connection connection) throws SQLException {
        String query = "SELECT * FROM tickets";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            StringBuilder result = new StringBuilder();
            while (resultSet.next()) {
                result.append(resultSet.getInt("ticket_id")).append(": ").append("User ").append(resultSet.getInt("user_id")).append(" - Movie ").append(resultSet.getInt("movie_id")).append(", ");
            }
            return result.toString();
        }
    }
}