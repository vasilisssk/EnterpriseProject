package ru.vsu.cs.erokhov_v_e.presentation.servlet;

import ru.vsu.cs.erokhov_v_e.domain.entities.Department;
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

public class DepartmentServlet extends HttpServlet {

    private EnterpriseService enterpriseService;

    private static final String PARAM_NAME = "name";

    private static final String ATTR_DEPARTMENTS = "departments";

    private static final String DEPARTMENTS_URL = "/departments";
    private static final String DEPARTMENTS_VIEW = "/WEB-INF/views/departments.jsp";

    @Override
    public void init() throws ServletException {
        enterpriseService = (EnterpriseService) getServletContext().getAttribute(WebConstants.ENTERPRISE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = enterpriseService.selectAllDepartments()
                .stream()
                .sorted(Comparator.comparingLong(Department::getId))
                .collect(Collectors.toList());

        request.setAttribute(ATTR_DEPARTMENTS, departments);
        request.getRequestDispatcher(DEPARTMENTS_VIEW).forward(request, response);
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
        String name = request.getParameter(PARAM_NAME);
        enterpriseService.insertDepartment(name);

        response.sendRedirect(request.getContextPath() + DEPARTMENTS_URL);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter(WebConstants.PARAM_ID));
        enterpriseService.removeDepartment(id);

        response.sendRedirect(request.getContextPath() + DEPARTMENTS_URL);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter(WebConstants.PARAM_ID));
        String name = request.getParameter(PARAM_NAME);
        enterpriseService.updateDepartmentName(id, name);

        response.sendRedirect(request.getContextPath() + DEPARTMENTS_URL);
    }
}