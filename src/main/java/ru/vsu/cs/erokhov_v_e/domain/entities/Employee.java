package ru.vsu.cs.erokhov_v_e.domain.entities;

import java.util.Objects;

public class Employee {

    private final long id;
    private String fullName;
    private int age;
    private double salary;
    private long departmentId;

    public Employee(String fullName, int age, double salary, long departmentId) {
        this.id = 0;
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public Employee(long id, String fullName, int age, double salary, long departmentId) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
