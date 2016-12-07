package org.wildfly.swarm.examples.jpa.war;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ken Finnigan
 */
@WebServlet(name = "EmployeeServlet", urlPatterns = "/*")
public class EmployeeServlet extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees = em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
        PrintWriter writer = resp.getWriter();

        writer.write("<html><head></head><body>\n");
        writer.write("Employees:\n\n");
        writer.write("<table><tr><th>Id</th><th>Name</th></tr>\n");

        for (Employee employee : employees) {
            writer.write("<tr><td>" + employee.getId() + "</td><td>" + employee.getName() + "</td></tr>\n");
        }

        writer.write("</body></html>");
    }
}
