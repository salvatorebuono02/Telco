package it.polimi.telco.servlets;

import it.polimi.telco.beans.EmployeeBean;
import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.entities.Employee;
import it.polimi.telco.entities.Product;
import it.polimi.telco.entities.services.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/EmployeeHomePage")
public class EmployeeHomePage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB
    private EmployeeBean employeeBean;
    @EJB
    private ServicePackageBean servicePackageBean;

    public EmployeeHomePage() {
        super();
    }

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = null;
        List<Service> services = employeeBean.findAllServices();
        List<Product> products = employeeBean.findAllProducts();
        int employeeId = (int) req.getSession().getAttribute("userId");
        employee = employeeBean.findById(employeeId);
        ServletContext servletContext = getServletContext();
        final WebContext webContext = new WebContext(req, resp, servletContext, req.getLocale());
        if (employee != null)
            webContext.setVariable("employee", employee);
            webContext.setVariable("services", services);
            webContext.setVariable("products", products);
        String path = "EmployeeHomePage.html";
        templateEngine.process(path, webContext, resp.getWriter());
    }
}



