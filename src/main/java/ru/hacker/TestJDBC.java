package ru.hacker;

import java.util.Objects;

public class TestJDBC {
    private final int id;
    private String name;

    public TestJDBC(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestJDBC testJDBC = (TestJDBC) o;
        return id == testJDBC.id &&
                Objects.equals(name, testJDBC.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TestJDBC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
