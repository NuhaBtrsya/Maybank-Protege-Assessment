import java.sql.*;

public class DatabaseConnector {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection;
            connection = DriverManager.getConnection("jdbc:mysql://your_host:3306/hello_world", "admin", "admin");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM display");

            while (resultSet.next())
            {
                String content = resultSet.getString("content");
                System.out.println(content);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}