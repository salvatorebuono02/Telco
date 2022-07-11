package it.polimi.telco.servlets;

import it.polimi.telco.beans.EmployeeBean;
import it.polimi.telco.entities.employeeReport.InsolventUsers;
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

@WebServlet("/showReport")
public class EmployeeReport extends HttpServlet {
    private TemplateEngine templateEngine;
    @EJB
    private EmployeeBean employeeBean;

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

        ServletContext servletContext = getServletContext();
        final WebContext webContext = new WebContext(req, resp, servletContext, req.getLocale());
        webContext.setVariable("salesPackage", employeeBean.findAllSalesPack());
        webContext.setVariable("alertReport", employeeBean.findAllAlertReport());
        webContext.setVariable("avgProductsPerPackage", employeeBean.findAllAvgProd());
        webContext.setVariable("bestProduct", employeeBean.findBestProduct());
        webContext.setVariable("insolvents", employeeBean.findAllInsolvents());
        webContext.setVariable("suspendedOrders", employeeBean.findAllSuspended());
        webContext.setVariable("totalPurchasePack", employeeBean.findAllPurchasePack());
        webContext.setVariable("totalPurchasePackAndVal", employeeBean.findAllPurchasePackAndVal());
        String path = "EmployeeReport.html";
        templateEngine.process(path, webContext, resp.getWriter());
    }
}
