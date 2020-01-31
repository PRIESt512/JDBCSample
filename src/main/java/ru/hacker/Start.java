package ru.hacker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Start {

    static void createTable(Statement st) throws SQLException {
        st.execute("CREATE MEMORY TABLE test_JDBC (" +
                "id IDENTITY PRIMARY KEY," +
                "name VARCHAR(50)" +
                ")");
    }

    static void insertData(Statement st) throws SQLException {
        st.execute("INSERT INTO test_JDBC VALUES(1, 'HELLO')");
        st.execute("INSERT INTO test_JDBC VALUES(2, 'JOHN')");
    }

    static void selectData(Statement st) throws SQLException {
        try (ResultSet rs = st.executeQuery("SELECT * FROM test_JDBC")) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name"));
            }

        }

        System.out.println();
    }

    static void insertStatement() {

    }

    static void insertResultSet(Statement st) throws SQLException {
        try (ResultSet rs = st.executeQuery("SELECT id, name FROM test_JDBC")) {
            rs.moveToInsertRow();

            rs.updateInt("id", 3);
            rs.updateString("name", "TestInsert");
            rs.insertRow();
            rs.beforeFirst();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //Class.forName("org.h2.Driver");

        String url = "jdbc:h2:~/test"; //БД на диске
        String url_memory = "jdbc:h2:mem:test";//БД в памяти

        try (Connection conn = DriverManager.getConnection(url_memory, "sa", "")) {

            try (Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)) {

                createTable(st);

                insertData(st);

                selectData(st);

                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO test_JDBC VALUES (?, ?)")) {
                    conn.setAutoCommit(false);

                    ps.setInt (1, 4);
                    ps.setString (2, "Test PreparedStatement");

                    ps.execute();
                    conn.commit();
                } finally {
                    conn.setAutoCommit(true);
                }

                selectData(st);

                //insertResultSet(st);

                //selectData(st);
            }

//            String name1 = "Jack";
//            String q = "insert into TEST(name) values(?)";
//            PreparedStatement st1 = null;
//
//            st1 = conn.prepareStatement(q);
//            st1.setString(1, name1);
//            st1.execute();
        }
    }
}
