package ru.vsu.cs.erokhov_v_e.data.postgresql;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;
import ru.vsu.cs.erokhov_v_e.domain.repositories.DepartmentRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgreSQLDepartmentRepositoryImpl implements DepartmentRepository {

    private final DataSource dataSource;

    public PostgreSQLDepartmentRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Department save(Department entity) {
        if (entity.getId() == 0) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    @Override
    public Optional<Department> selectById(long id) {
        String selectDepByIdQuery = "SELECT id, name, employee_count FROM department WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectDepByIdQuery)) {

            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Department dept = new Department(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getInt("employee_count")
                    );
                    return Optional.of(dept);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting department by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Department> selectAll() {
        List<Department> departments = new ArrayList<>();
        String selectAllDepsQuery = "SELECT id, name, employee_count FROM department";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(selectAllDepsQuery)) {

            while (rs.next()) {
                departments.add(new Department(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("employee_count")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting all departments", e);
        }
        return departments;
    }

    @Override
    public void deleteById(long id) {
        String deleteDepByIdQuery = "DELETE FROM department WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteDepByIdQuery)) {

            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Department with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting department", e);
        }
    }

    @Override
    public boolean existsById(long id) {
        String existsDepByIdQuery = "SELECT 1 FROM department WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(existsDepByIdQuery)) {

            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking department existence", e);
        }
    }

    private Department insert(Department department) {
        String insertDepQuery = "INSERT INTO department (name, employee_count) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertDepQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getEmployeeCount());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long newId = generatedKeys.getLong(1);
                    return new Department(newId, department.getName(), department.getEmployeeCount());
                } else {
                    throw new SQLException("Creating department failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting department", e);
        }

    }

    private Department update(Department department) {
        String updateDepQuery = "UPDATE department SET name = ?, employee_count = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateDepQuery)) {

            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getEmployeeCount());
            preparedStatement.setLong(3, department.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Department with id " + department.getId() + " not found");
            }

            return department;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating department", e);
        }
    }
}