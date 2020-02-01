package ru.hacker;

import java.sql.SQLException;
import java.util.List;

public class StartTwo {

    public static void main(String[] args) throws SQLException {
        DataManipulator dataManipulator = new DataManipulator("jdbc:h2:mem:test", "sa", "");

        dataManipulator.insert(new TestJDBC(1, "Alex"));
        dataManipulator.insert(new TestJDBC(2, "Артем Сергеевич"));
        dataManipulator.insert(new TestJDBC(3, "Diana"));
        dataManipulator.insert(new TestJDBC(4, "Any"));

        List<TestJDBC> result = dataManipulator.getAll();
        result.forEach(System.out::println);

        System.out.println();

        System.out.println(dataManipulator.get(3));

        System.out.println();

        TestJDBC testJDBC = dataManipulator.get(1);

        testJDBC.setName("Alex222");
        dataManipulator.update(testJDBC);

        System.out.println(dataManipulator.get(1));

    }
}
