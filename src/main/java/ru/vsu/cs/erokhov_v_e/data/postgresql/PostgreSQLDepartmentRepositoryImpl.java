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

    private static final String SELECT_DEP_BY_ID_QUERY = "SELECT id, name, employee_count FROM department WHERE id = ?";
    private static final String SELECT_ALL_DEPS_QUERY = "SELECT id, name, employee_count FROM department";
    private static final String DELETE_DEP_BY_ID_QUERY = "DELETE FROM department WHERE id = ?";
    private static final String EXISTS_DEP_BY_ID_QUERY = "SELECT 1 FROM department WHERE id = ?";
    private static final String INSERT_DEP_QUERY = "INSERT INTO department (name, employee_count) VALUES (?, ?)";
    private static final String UPDATE_DEP_QUERY = "UPDATE department SET name = ?, employee_count = ? WHERE id = ?";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMPLOYEE_COUNT = "employee_count";

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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DEP_BY_ID_QUERY)) {

            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Department dept = new Department(
                            rs.getLong(ID),
                            rs.getString(NAME),
                            rs.getInt(EMPLOYEE_COUNT)
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

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_ALL_DEPS_QUERY)) {

            while (rs.next()) {
                departments.add(new Department(
                        rs.getLong(ID),
                        rs.getString(NAME),
                        rs.getInt(EMPLOYEE_COUNT)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting all departments", e);
        }
        return departments;
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEP_BY_ID_QUERY)) {

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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXISTS_DEP_BY_ID_QUERY)) {

            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking department existence", e);
        }
    }

    private Department insert(Department department) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEP_QUERY, Statement.RETURN_GENERATED_KEYS)) {

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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEP_QUERY)) {

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