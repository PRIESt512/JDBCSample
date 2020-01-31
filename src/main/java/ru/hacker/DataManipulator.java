package ru.hacker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataManipulator {

    private final Connection connection;

    private final String insertSample = "INSERT INTO test_JDBC VALUES (?, ?)";

    public DataManipulator(String url, String name, String passw) throws SQLException {
        this.connection = DriverManager.getConnection(url, name, passw);
    }

    public void insert(TestJDBC testJDBC) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement(insertSample)) {
            st.setInt(1, testJDBC.getId());
            st.setString(2, testJDBC.getName());

            st.execute();
        }
    }

    public TestJDBC get(int index) {

    }

    public List<TestJDBC> getAll() {

    }

    public void update(TestJDBC testJDBC) {

    }
}
