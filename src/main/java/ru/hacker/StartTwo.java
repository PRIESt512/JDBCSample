package ru.hacker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StartTwo {

    public static void main(String[] args) throws SQLException {
        DataManipulator dataManipulator = new DataManipulator("jdbc:h2:mem:test", "sa", "");

        List<TestJDBC> array = new ArrayList<>();

        array.add(new TestJDBC(1, "Alex"));
        array.add(new TestJDBC(2, "Артем Сергеевич"));
        array.add(new TestJDBC(3, "Diana"));
        array.add(new TestJDBC(4, "Any"));

        dataManipulator.insertAll2(array);

        List<TestJDBC> result = dataManipulator.getAll();
        result.forEach(System.out::println);

        System.out.println();

//        try {
//            System.out.println(dataManipulator.get(3));
//        } catch (SQLException ex) {
//            System.out.println(ex.getNextException());
//        }
//        System.out.println();
//
//        TestJDBC testJDBC = dataManipulator.get(1);
//
//        testJDBC.setName("Alex222");
//        dataManipulator.update(testJDBC);
//
//        System.out.println(dataManipulator.callFunc(100));

    }
}
