package sample;

import java.sql.*;

public class DataSourceDB {
    public static void DBTest(){
        String conn_url = "jdbc:mysql://localhost:3306/world?user=root&password=root&serverTimezone=UTC";
        Connection conn = null;
        try {
            String cmd = "select * from country ";

            conn = DriverManager.getConnection(conn_url);
            System.out.println("Connection successful!");

            ResultSet rs = null;

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(cmd);

            while (rs!=null && rs.next()){
                System.out.println(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String args[]){
        DataSourceDB.DBTest();
    }
}