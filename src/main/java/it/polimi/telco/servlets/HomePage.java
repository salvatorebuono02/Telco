package it.polimi.telco.servlets;

import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.beans.UserBean;
import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;
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

@WebServlet("/HomePage")
public class HomePage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB
    private UserBean userBean;
    @EJB
    private ServicePackageBean servicePackageBean;

    public HomePage(){
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
        User user=null;
        List<ServicePackage> availableServicePackages=null;
        int userId=(int) req.getSession().getAttribute("userId");
        user = userBean.findById(userId);
        ServletContext servletContext=getServletContext();
        final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
        if(user!=null)
            webContext.setVariable("user",user);
        //availableServicePackage
        availableServicePackages=servicePackageBean.findAvailable(user);
        if (!availableServicePackages.isEmpty() && availableServicePackages!=null)
            webContext.setVariable("available",availableServicePackages);

        String path="HomePage.html";
        templateEngine.process(path,webContext,resp.getWriter());
    }
}

