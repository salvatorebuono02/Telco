package it.polimi.telco.servlets;

import it.polimi.telco.beans.OrderBean;
import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.beans.UserBean;
import it.polimi.telco.entities.Order;
import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;
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
import java.util.Optional;

@WebServlet("/Confirmation")
public class ConfirmationPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB
    private ServicePackageBean servicePackageBean;
    @EJB
    private UserBean userBean;
    @EJB
    private OrderBean orderBean;

    public ConfirmationPage() {
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
        ServletContext servletContext=getServletContext();
        final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
        int servicePackageId = Integer.parseInt(req.getParameter("servicePackageId"));
        Optional<ServicePackage> sp= servicePackageBean.findServicePackageById(servicePackageId);

        Integer userId= (Integer) req.getSession().getAttribute("userId");
        String path;
        if (sp.isPresent()){
            List<Service> services = servicePackageBean.findServicesFromServicePackageId(servicePackageId);
            //TODO crea ordine
            Order order=null;
            if (userId!=null){
                User user=userBean.findById(userId);
                webContext.setVariable("user",user);
                webContext.setVariable("order",order);
                path= "Confirmation.html";
                templateEngine.process(path,webContext,resp.getWriter());
            }
            else {
                path= "index.html";
                templateEngine.process(path,webContext,resp.getWriter());
                orderBean.setOrderInStandBy(true);
            }

        }
    }
}
