package ru.vsu.cs.erokhov_v_e.presentation.servlet;

import ru.vsu.cs.erokhov_v_e.domain.entities.Employee;
import ru.vsu.cs.erokhov_v_e.domain.services.EnterpriseService;
import ru.vsu.cs.erokhov_v_e.presentation.util.WebConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeServlet extends HttpServlet {

    private EnterpriseService enterpriseService;

    private static final String PARAM_FULL_NAME = "full_name";
    private static final String PARAM_AGE = "age";
    private static final String PARAM_SALARY = "salary";
    private static final String PARAM_DEPARTMENT_ID = "department_id";

    private static final String ATTR_TOTAL_SALARY = "totalSalary";
    private static final String ATTR_DEPARTMENT_ID = "departmentId";
    private static final String ATTR_EMPLOYEES = "employees";

    private static final String EMPLOYEES_URL = "/employees";
    private static final String EMPLOYEES_VIEW = "/WEB-INF/views/employees.jsp";

    @Override
    public void init() throws ServletException {
        enterpriseService = (EnterpriseService) getServletContext().getAttribute(WebConstants.ENTERPRISE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long departmentId = Long.parseLong(request.getParameter(PARAM_DEPARTMENT_ID));
        List<Employee> employees = enterpriseService.selectEmployeesByDepartment(departmentId)
                .stream()
                .sorted(Comparator.comparingLong(Employee::getId))
                .collect(Collectors.toList());
        double salary = enterpriseService.selectTotalSalary(departmentId);

        request.setAttribute(ATTR_TOTAL_SALARY, salary);
        request.setAttribute(ATTR_DEPARTMENT_ID, departmentId);
        request.setAttribute(ATTR_EMPLOYEES, employees);
        request.getRequestDispatcher(EMPLOYEES_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(WebConstants.ENCODING);

        String method = request.getParameter(WebConstants.PARAM_METHOD);

        if (method == null) {
            handleInsert(request, response);
        } else if (method.equalsIgnoreCase(WebConstants.METHOD_DELETE)) {
            handleDelete(request, response);
        } else if (method.equalsIgnoreCase(WebConstants.METHOD_PUT)) {
            handleUpdate(request, response);
        }
    }

    private void handleInsert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullName = request.getParameter(PARAM_FULL_NAME);
        int age = Integer.parseInt(request.getParameter(PARAM_AGE));
        double salary = Double.parseDouble(request.getParameter(PARAM_SALARY));
        long departmentId = Long.parseLong(request.getParameter(PARAM_DEPARTMENT_ID));
        enterpriseService.insertEmployee(fullName, age, salary, departmentId);

        response.sendRedirect(request.getContextPath() + EMPLOYEES_URL + "?department_id=" + departmentId);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter(WebConstants.PARAM_ID));

        Employee employee = enterpriseService.findEmployeeById(id);
        long departmentId = employee.getDepartmentId();

        enterpriseService.removeEmployee(id);

        response.sendRedirect(request.getContextPath() + EMPLOYEES_URL + "?department_id=" + departmentId);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter(WebConstants.PARAM_ID));

        Employee employee = enterpriseService.findEmployeeById(id);
        long oldDepartmentId = employee.getDepartmentId();

        String fullName = request.getParameter(PARAM_FULL_NAME);
        int age = Integer.parseInt(request.getParameter(PARAM_AGE));
        double salary = Double.parseDouble(request.getParameter(PARAM_SALARY));
        long departmentId = Long.parseLong(request.getParameter(PARAM_DEPARTMENT_ID));
        enterpriseService.updateEmployee(id, fullName, age, salary, departmentId);

        response.sendRedirect(request.getContextPath() + EMPLOYEES_URL + "?department_id=" + oldDepartmentId);
    }
}
