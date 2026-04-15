package ru.vsu.cs.erokhov_v_e.data.postgresql;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;
import ru.vsu.cs.erokhov_v_e.domain.repositories.EmployeeRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgreSQLEmployeeRepositoryImpl implements EmployeeRepository {

    private final DataSource dataSource;

    public PostgreSQLEmployeeRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Employee> selectByDepartmentId(long departmentId) {
        List<Employee> employees = new ArrayList<>();
        String selectByDepIdQuery = "SELECT id, full_name, age, salary, department_id FROM employee WHERE department_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectByDepIdQuery)) {

            preparedStatement.setLong(1, departmentId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    employees.add(new Employee(
                            rs.getLong("id"),
                            rs.getString("full_name"),
                            rs.getInt("age"),
                            rs.getDouble("salary"),
                            rs.getLong("department_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting employees by department id", e);
        }
        return employees;
    }

    @Override
    public double selectTotalSalaryByDepartmentId(long departmentId) {
        String selectSalaryQuery = "SELECT sum(salary) FROM employee WHERE department_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSalaryQuery)) {

            statement.setLong(1, departmentId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting employees' total salary by department id", e);
        }
        return 0.0;
    }

    @Override
    public Employee save(Employee entity) {
        if (entity.getId() == 0) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    @Override
    public Optional<Employee> selectById(long id) {
        String selectEmpByIdQuery = "SELECT id, full_name, age, salary, department_id FROM employee WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectEmpByIdQuery)) {

            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Employee(
                            rs.getLong("id"),
                            rs.getString("full_name"),
                            rs.getInt("age"),
                            rs.getDouble("salary"),
                            rs.getLong("department_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting employee by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> selectAll() {
        List<Employee> employees = new ArrayList<>();
        String selectAllEmpsQuery = "SELECT id, full_name, age, salary, department_id FROM employee";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectAllEmpsQuery)) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getLong("id"),
                        rs.getString("full_name"),
                        rs.getInt("age"),
                        rs.getDouble("salary"),
                        rs.getLong("department_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting all employees", e);
        }
        return employees;
    }

    @Override
    public void deleteById(long id) {
        String deleteEmpQuery = "DELETE FROM employee WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedDeleteStatement = connection.prepareStatement(deleteEmpQuery)) {

            preparedDeleteStatement.setLong(1, id);
            int affectedRows = preparedDeleteStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Employee with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting employee", e);
        }
    }

    @Override
    public boolean existsById(long id) {
        String select1Query = "SELECT 1 FROM employee WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(select1Query)) {

            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking employee existence", e);
        }
    }

    private Employee insert(Employee employee) {
        String insertEmpQuery = "INSERT INTO employee (full_name, age, salary, department_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedEmpStatement = connection.prepareStatement(insertEmpQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedEmpStatement.setString(1, employee.getFullName());
            preparedEmpStatement.setInt(2, employee.getAge());
            preparedEmpStatement.setDouble(3, employee.getSalary());
            preparedEmpStatement.setLong(4, employee.getDepartmentId());
            preparedEmpStatement.executeUpdate();

            long newId;
            try (ResultSet generatedKeys = preparedEmpStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained");
                }
            }

            return new Employee(newId, employee.getFullName(), employee.getAge(),
                    employee.getSalary(), employee.getDepartmentId());
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting employee", e);
        }
    }

    private Employee update(Employee employee) {
        String updateEmpQuery = "UPDATE employee SET full_name = ?, age = ?, salary = ?, department_id = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateEmpQuery)) {

            preparedStatement.setString(1, employee.getFullName());
            preparedStatement.setInt(2, employee.getAge());
            preparedStatement.setDouble(3, employee.getSalary());
            preparedStatement.setLong(4, employee.getDepartmentId());
            preparedStatement.setLong(5, employee.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Employee with id " + employee.getId() + " not found");
            }

            return employee;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating employee", e);
        }
    }
}