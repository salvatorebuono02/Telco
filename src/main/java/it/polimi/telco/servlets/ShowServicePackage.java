package it.polimi.telco.servlets;

import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.entities.ServicePackage;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebServlet("/ShowServicePackage")
public class ShowServicePackage extends HttpServlet {

    private TemplateEngine templateEngine;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext=getServletContext();
        final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
        int servicePackageId = Integer.parseInt(req.getParameter("servicePackageId"));
        Optional<ServicePackage> sp= servicePackageBean.findServicePackageById(servicePackageId);
        if (sp.isPresent()){
            List<Service> services = servicePackageBean.findServicesFromServicePackageId(servicePackageId);
            String pattern = "MM/dd/yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date today = Calendar.getInstance().getTime();
            String date = df.format(today);
            webContext.setVariable("services",services);
            webContext.setVariable("servicePackage",sp);
            webContext.setVariable("date",date);
            String path="ShowServicePackage.html";
            templateEngine.process(path,webContext,resp.getWriter());}
        else throw new ServletException();

    }
}
