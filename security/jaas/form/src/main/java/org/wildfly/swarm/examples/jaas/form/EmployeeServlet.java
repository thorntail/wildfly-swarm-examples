package org.wildfly.swarm.examples.jaas.form;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class EmployeeServlet extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees = em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
        req.setAttribute("employees", req.isUserInRole("admin") ? employees : Collections.emptyList());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/employees.jsp");
        dispatcher.forward(req, resp);
    }

}
