import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Movie {
    private int movieId;
    private String title;

    public Movie(int movieId, String title) {
        this.movieId = movieId;
        this.title = title;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public void addMovie(Connection connection) throws SQLException {
        String query = "INSERT INTO movies (movie_id, title) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, getMovieId());
            statement.setString(2, getTitle());
            statement.executeUpdate();
        }
    }

    public static String getAllMovies(Connection connection) throws SQLException {
        String query = "SELECT * FROM movies";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            StringBuilder result = new StringBuilder();
            while (resultSet.next()) {
                result.append(resultSet.getInt("movie_id")).append(": ").append(resultSet.getString("title")).append(", ");
            }
            return result.toString();
        }
    }
}
