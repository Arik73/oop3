import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class User {
    private int userId;
    private String username;

    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void addUser(Connection connection) throws SQLException {
        String query = "INSERT INTO users (user_id, username) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, getUserId());
            statement.setString(2, getUsername());
            statement.executeUpdate();
        }
    }

    public static String getAllUsers(Connection connection) throws SQLException {
        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            StringBuilder result = new StringBuilder();
            while (resultSet.next()) {
                result.append(resultSet.getInt("user_id")).append(": ").append(resultSet.getString("username")).append(", ");
            }
            return result.toString();
        }
    }
}