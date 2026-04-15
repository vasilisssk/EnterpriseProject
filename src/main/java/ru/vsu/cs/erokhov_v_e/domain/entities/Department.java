package ru.vsu.cs.erokhov_v_e.domain.entities;

import java.util.Objects;

public class Department {

    private final long id;
    private String name;
    private int employeeCount;

    public Department(String name) {
        this.id = 0;
        this.name = name;
        this.employeeCount = 0;
    }

    public Department(long id, String name, int employeeCount) {
        this.id = id;
        this.name = name;
        this.employeeCount = employeeCount;
    }

    public void incrementEmployeeCount() {
        this.employeeCount++;
    }

    public void decrementEmployeeCount() {
        if (this.employeeCount > 0) {
            this.employeeCount--;
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    @Override
    public String toString() {
        return "Department {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employeeCount=" + employeeCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
