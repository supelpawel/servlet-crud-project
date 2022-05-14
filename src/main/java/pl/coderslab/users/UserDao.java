package pl.coderslab.users;

import org.mindrot.jbcrypt.BCrypt.BCrypt;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users (email, username, password) VALUES (?, ?, ?);";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?;";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?;";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?;";
    private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users;";

    public UserDao() {
    }

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            preStmt.setString(1, user.getEmail());
            preStmt.setString(2, user.getUsername());
            preStmt.setString(3, hashPassword(user.getPassword()));
            preStmt.executeUpdate();
            ResultSet resultSet = preStmt.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(READ_USER_QUERY);
            preStmt.setInt(1, userId);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(UPDATE_USER_QUERY);
            preStmt.setString(1, user.getEmail());
            preStmt.setString(2, user.getUsername());
            preStmt.setString(3, this.hashPassword(user.getPassword()));
            preStmt.setInt(4, user.getId());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(DELETE_USER_QUERY);
            preStmt.setInt(1, userId);
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            User[] users = new User[0];
            PreparedStatement preStmt = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(users, user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private static User[] addToArray(User[] users, User u) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }
}

