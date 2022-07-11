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

@WebServlet("/CreateServicePackage")
public class CreateServicePackage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB
    private EmployeeBean employeeBean;
    @EJB
    private ServicePackageBean servicePackageBean;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameServPackage=req.getParameter("name");
        String[] optionalProducts=req.getParameterValues("products");
        String[] validityPeriods= req.getParameterValues("validityPeriods");

        Employee employee = (Employee) req.getSession().getAttribute("employee");
        ArrayList<Product> productArrayList=new ArrayList<>();
        ArrayList<ValidityPeriod> validityPeriodArrayList=new ArrayList<>();



        if(optionalProducts!=null){
            System.out.println("optprodct diverso da null");
            for (String optionalProduct:optionalProducts){
                Product product=servicePackageBean.findProductById(Integer.parseInt(optionalProduct));
                if (product!=null){
                    productArrayList.add(product);
                    System.out.println("Aggiunto prodotto: "+product);
                }
            }
        }

        for (String validityPeriod:validityPeriods){
            ValidityPeriod validityPeriodSelected= servicePackageBean.findValidityPeriodById(Integer.parseInt(validityPeriod));
            if (validityPeriodSelected!=null)
                validityPeriodArrayList.add(validityPeriodSelected);
        }
        //inoltro i parametri

        String path;
        ServletContext servletContext;
        if(servicePackageBean.findServicePackageByName(nameServPackage)!=null){
            servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            path = "/EmployeeHomePage.html";
            List<Service> servicesList = employeeBean.findAllServices();
            List<Product> productsList = employeeBean.findAllProducts();
            List<ValidityPeriod> validityPeriodsList =employeeBean.findAllValidityPeriods();
            ctx.setVariable("confirmMsg", "Name already chosen");
            ctx.setVariable("employee",employee );
            ctx.setVariable("services", servicesList);
            ctx.setVariable("products", productsList);
            ctx.setVariable("validityPeriods",validityPeriodsList);
            templateEngine.process(path, ctx, resp.getWriter());
        }
        else {
            try {
                employeeBean.createServicePackage(nameServPackage,productArrayList,validityPeriodArrayList);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            path=getServletContext().getContextPath()+"/EmployeeHomePage";
            resp.sendRedirect(path);
        }
    }
}
