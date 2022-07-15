package it.polimi.telco.servlets;

import it.polimi.telco.beans.EmployeeBean;
import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.entities.Employee;
import it.polimi.telco.entities.Product;
import it.polimi.telco.entities.ServicePackage;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        String[] ids= req.getParameterValues("servicesId");
        ArrayList<Integer> servicesId=new ArrayList<>();
        for (String i: ids){
            servicesId.add(Integer.parseInt(i));
        }

        String[] optionalProducts=req.getParameterValues("products");
        String[] validityPeriods= req.getParameterValues("validityPeriods");

        Employee employee = (Employee) req.getSession().getAttribute("employee");
        ArrayList<Product> productArrayList=new ArrayList<>();
        ArrayList<ValidityPeriod> validityPeriodArrayList=new ArrayList<>();
        ArrayList<Service> serviceArrayList=new ArrayList<>();

        for (int i:servicesId){
            serviceArrayList.add(employeeBean.findServiceById(i));
        }


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
            ServicePackage pack = null;
            ArrayList<Service> managedService=new ArrayList<>();
            for (Service s:serviceArrayList){
                Service s1=employeeBean.getManagedService(s);
                managedService.add(s1);
            }
            try {
                pack= employeeBean.createServicePackage(nameServPackage,productArrayList,validityPeriodArrayList,managedService);
            }
            catch (SQLException e){
                e.printStackTrace();
            }                
            employeeBean.updateServices(servicesId, pack);

            path=getServletContext().getContextPath()+"/EmployeeHomePage";
            resp.sendRedirect(path);
        }
    }
}
