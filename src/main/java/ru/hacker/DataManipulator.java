package ru.hacker;

import com.sun.tools.internal.ws.api.TJavaGeneratorExtension;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataManipulator {

    private final Connection connection;

    private final String seq = "CREATE SEQUENCE INC";

    private final String func = "CREATE ALIAS test " +
            "FOR \"ru.hacker.DataManipulator.test\" ";

    private final String createTable = "CREATE MEMORY TABLE test_JDBC (" +
            "id IDENTITY PRIMARY KEY," +
            "name VARCHAR(50)" +
            ")";

    private final String callSeq = "CALL NEXT VALUE FOR INC";

    private final String insertSample = "INSERT INTO test_JDBC VALUES (?, ?)";

    private final String selectAll = "SELECT * FROM test_JDBC";

    private final String selectParam = "SELECT * FROM test_JDBC WHERE test_JDBC.id = ?";

    private final String update = "UPDATE test_JDBC SET test_JDBC.id = ?, test_JDBC.name = ? WHERE test_JDBC.id = ?";

    public DataManipulator(String url, String name, String passw) throws SQLException {
        this.connection = DriverManager.getConnection(url, name, passw);
        create(createTable);
        create(seq);
        create(func);
    }

    private void create(String sql) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
        }
    }

    public void insert(TestJDBC testJDBC) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement(insertSample)) {
            st.setInt(1, testJDBC.getId());
            st.setString(2, testJDBC.getName());

            st.execute();
        }
    }

    public TestJDBC get(int index) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement(selectParam)) {
            st.setInt(1, index);
            try (ResultSet rs = st.executeQuery()) {

                TestJDBC result = null;
                while (rs.next()) {
                    result = new TestJDBC(rs.getInt(1), rs.getString(2));
                }

                return result;
            }
        }
    }

    public List<TestJDBC> getAll() throws SQLException {
        try (Statement st = connection.createStatement()) {

            if (st.execute(selectAll)) {

                List<TestJDBC> result = new ArrayList<>();
                try (ResultSet rs = st.getResultSet()) {
                    while (rs.next()) {
                        result.add(new TestJDBC(rs.getInt(1), rs.getString(2)));
                    }
                }
                return result;
            }
            return Collections.emptyList();
        }
    }

    public void update(TestJDBC testJDBC) throws SQLException {
        TestJDBC old = get(testJDBC.getId());

        try (PreparedStatement st = connection.prepareStatement(update)) {
            st.setInt(1, testJDBC.getId());
            st.setString(2, testJDBC.getName());
            st.setInt(3, old.getId());

            if (!st.execute() && st.getUpdateCount() == 1) {
                System.out.println("Успешно обновлено");
            }
        }
    }

    public void getSeq() throws SQLException {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("CALL NEXT VALUE FOR INC");
            rs.next();
            System.out.println(rs.getLong(1));
            rs = st.executeQuery("CALL NEXT VALUE FOR INC");
            rs.next();
            System.out.println(rs.getLong(1));
        }
    }

    public int callFunc(int value) throws SQLException {
        try (CallableStatement st = connection.prepareCall("{? = call test(?)}")) {

            st.registerOutParameter(1, Types.INTEGER);

            st.setInt(2, value);
            st.execute();
            return st.getInt(1);
        }
    }

    public static int test(int value) {
        return value;
    }
}
