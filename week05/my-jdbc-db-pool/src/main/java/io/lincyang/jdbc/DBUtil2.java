package io.lincyang.jdbc;

import io.lincyang.entity.Student;

import java.sql.*;

/**
 * @Author lincyang
 * @Date 2021/4/18 8:53 PM
 **/
public class DBUtil2 {
    private static final String URL = "jdbc:mysql://localhost:336/mytest?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER_NAME = "root";
    private static final String PASSWD = "123456";

    static {
        try {
            Class.forName("com.mysql.jdbc.Drive");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWD);
        return connection;
    }

    public void insertOne(Student stu) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("insert into student values(?,?)");
            statement.setInt(1, stu.getId());
            statement.setString(2, stu.getName());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e1) {

            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteById(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("delete student where id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e1) {

            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Student stu) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("update student set name=? where id=?");
            statement.setString(1, stu.getName());
            statement.setInt(2, stu.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e1) {

            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Student selectOne(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        Student stu = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from student from id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stu = new Student();
                stu.setId(resultSet.getInt("id"));
                stu.setName(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception e1) {

            }

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return stu;
    }

}
