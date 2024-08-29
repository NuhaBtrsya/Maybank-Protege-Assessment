<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    String url = "jdbc:mysql://your_host:3306/hello_world";
    String username = "admin";
    String password = "admin";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM display");

        while (resultSet.next()) {
            String content = resultSet.getString("content");
%>
<p><%= content %></p>
<%
        }
        resultSet.close();
        statement.close();
        connection.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>