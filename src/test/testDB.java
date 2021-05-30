package test;

import java.sql.*;

public class testDB {


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/COREJAVA";

    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        runTest();
    }

    public static void runTest()
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            /* Create Table */
            System.out.println("实例化查询句柄...");
            stmt = conn.createStatement();
            String sql;
            sql = "CREATE TABLE Greetings (Message CHAR(20))";
            stmt.execute(sql);
            sql = "INSERT INTO Greetings VALUE ('Hello, World!')";
            stmt.execute(sql);

            sql = "SELECT * FROM Greetings";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Message: " + rs.getString("Message"));
            }

            stmt.execute("DROP TABLE Greetings");
            System.out.println("表格销毁成功!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
