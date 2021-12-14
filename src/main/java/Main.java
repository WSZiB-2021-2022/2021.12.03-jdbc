import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static Connection connection;

    public static void main(String[] args) {
        connect();
        /*User user = new User(1, "imie", "login", "haslo");
        User user2 = new User(1, "janusz", "janusz", "janusz");
        User user3 = new User(1, "karol", "karol", "karol");
        addUser(user);
        addUser(user2);
        addUser(user3);*/
        //updateUser(user);
        //deleteUser(2);

        /*List<User> users = getAllUsers();
        System.out.println(users);*/

        System.out.println(getUserById(3));
    }

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Main.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test3", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addUser(User user) {
        try {
            String sql = new StringBuilder()
                    .append("INSERT INTO tuser (name, login, password) VALUES (")
                    .append("'")
                    .append(user.getName())
                    .append("','")
                    .append(user.getLogin())
                    .append("','")
                    .append(user.getPassword())
                    .append("');").toString();

            Statement statement = Main.connection.createStatement();
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateUser(User user) {
        try {
            String sql = "UPDATE tuser SET name = ?, login = ?, password = ? WHERE id = ?";

            PreparedStatement preparedStatement = Main.connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void deleteUser(int id) {
        try {
            String sql = "DELETE FROM tuser WHERE id = ?";

            PreparedStatement preparedStatement = Main.connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static User getUserById(int id) {
        try {
            String sql = "SELECT * FROM tuser WHERE id = ?";

            PreparedStatement preparedStatement = Main.connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("login"),
                        rs.getString("password")
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public static List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser;";

            PreparedStatement preparedStatement = Main.connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                result.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("login"),
                                rs.getString("password")
                        ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }
}
