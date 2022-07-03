package it.polimi.telco.servlets;

import it.polimi.telco.beans.EmployeeBean;
import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.entities.Employee;
import it.polimi.telco.entities.Product;
import it.polimi.telco.entities.ValidityPeriod;
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
import java.sql.SQLException;
import java.util.ArrayList;
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
        List<ValidityPeriod> validityPeriods=employeeBean.findAllValidityPeriods();
        int employeeId = (int) req.getSession().getAttribute("userId");
        employee = employeeBean.findById(employeeId);
        ServletContext servletContext = getServletContext();
        final WebContext webContext = new WebContext(req, resp, servletContext, req.getLocale());
        if (employee != null) {
            webContext.setVariable("employee", employee);
            webContext.setVariable("services", services);
            webContext.setVariable("products", products);
            webContext.setVariable("validityPeriods",validityPeriods);
        }

        String path = "EmployeeHomePage.html";
        templateEngine.process(path, webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //devo settare l'id service package nella tabella service dopo la creazione di un nuovo service package
        String nameServPackage=req.getParameter("name");
        String[] services=req.getParameterValues("services");
        String[] optionalProducts=req.getParameterValues("optionalProducts");
        String[] validityPeriods= req.getParameterValues("validityPeriods");

        ArrayList<Service> serviceArrayList=new ArrayList<>();
        ArrayList<Product> productArrayList=new ArrayList<>();
        ArrayList<ValidityPeriod> validityPeriodArrayList=new ArrayList<>();

        for(String service:services){
            Service serviceSelected=employeeBean.findServiceById(Integer.parseInt(service));
            if (serviceSelected!=null)
                serviceArrayList.add(serviceSelected);
        }

        if(optionalProducts!=null){
            for (String optionalProduct:optionalProducts){
                Product product=employeeBean.findProductById(Integer.parseInt(optionalProduct));
                if (product!=null)
                    productArrayList.add(product);
            }
        }

        for (String validityPeriod:validityPeriods){
            ValidityPeriod validityPeriodSelected= employeeBean.findValidityPeriodById(Integer.parseInt(validityPeriod));
            if (validityPeriodSelected!=null)
                validityPeriodArrayList.add(validityPeriodSelected);
        }

        String path;
        if(servicePackageBean.findServicePackageByName(nameServPackage)!=null){
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            path = "/EmployeeHomePage.html";
            ctx.setVariable("confirmMsg", "Name already chosen");
            templateEngine.process(path, ctx, resp.getWriter());

        }
        else {
            try {
                employeeBean.createServicePackage(nameServPackage,serviceArrayList,productArrayList,validityPeriodArrayList);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            path = "/EmployeeHomePage.html";
            ctx.setVariable("confirmMsg", "Service package created successfully");
            templateEngine.process(path, ctx, resp.getWriter());
        }

    }





}



