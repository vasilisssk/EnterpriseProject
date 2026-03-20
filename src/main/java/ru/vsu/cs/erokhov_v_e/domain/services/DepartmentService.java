package ru.vsu.cs.erokhov_v_e.domain.services;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;
import ru.vsu.cs.erokhov_v_e.domain.repositories.DepartmentRepository;

import java.util.List;

public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department create(String name) {
        validate(name);
        Department department = new Department(name);
        return departmentRepository.save(department);
    }

    public Department selectById(long id) {
        return departmentRepository.selectById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Отдел с ID {" + id + "} не найден"
                ));
    }

    public List<Department> selectAll() {
        return departmentRepository.selectAll();
    }

    public Department updateName(long id, String newName) {
        Department department = selectById(id);
        validate(newName);
        department.setName(newName);
        return departmentRepository.save(department);
    }

    public void delete(long id) {
        if (!existsById(id)) {
            throw new RuntimeException("Отдел с ID {" + id + "} не найден");
        }
        departmentRepository.deleteById(id);
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public boolean existsById(long id) {
        return departmentRepository.existsById(id);
    }

    private void validate(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Имя не должно быть пустым");
    }
}
