package pl.coderslab.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DbUtil;

import java.util.Arrays;

public class UserDao {

  private static final String CREATE_USER_QUERY = "INSERT INTO users (userName, userEmail, userPassword) VALUES (?, ?, ?);";
  private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?;";
  private static final String UPDATE_USER_QUERY = "UPDATE users SET userName = ?, userEmail = ?, userPassword = ? WHERE id = ?;";
  private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?;";
  private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users;";

  public UserDao() {
  }

  public User create(User user) {
    try (Connection conn = DbUtil.getConnection()) {
      PreparedStatement preStmt = conn.prepareStatement(CREATE_USER_QUERY,
          Statement.RETURN_GENERATED_KEYS);

      preStmt.setString(1, user.getUserName());
      preStmt.setString(2, user.getUserEmail());
      preStmt.setString(3, hashPassword(user.getUserPassword()));
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

  public User read(int userId) {
    try (Connection conn = DbUtil.getConnection()) {
      PreparedStatement preStmt = conn.prepareStatement(READ_USER_QUERY);
      preStmt.setInt(1, userId);
      ResultSet resultSet = preStmt.executeQuery();

      if (resultSet.next()) {
        User user = new User();

        user.setId(resultSet.getInt(1));
        user.setUserName(resultSet.getString("userName"));
        user.setUserEmail(resultSet.getString("userEmail"));
        user.setUserPassword(resultSet.getString("userPassword"));
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

      preStmt.setString(1, user.getUserName());
      preStmt.setString(2, user.getUserEmail());
      preStmt.setString(3, this.hashPassword(user.getUserPassword()));
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
        user.setUserName(resultSet.getString("userName"));
        user.setUserEmail(resultSet.getString("userEmail"));
        user.setUserPassword(resultSet.getString("userPassword"));
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

