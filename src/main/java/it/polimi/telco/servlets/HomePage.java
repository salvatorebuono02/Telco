package it.polimi.telco.servlets;

import it.polimi.telco.beans.OrderBean;
import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.beans.UserBean;
import it.polimi.telco.entities.Order;
import it.polimi.telco.entities.Product;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/HomePage")
public class HomePage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB
    private UserBean userBean;
    @EJB
    private ServicePackageBean servicePackageBean;

    @EJB
    private OrderBean orderBean;

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
        User user=(User) req.getSession().getAttribute("user");
        List<Order> userOrders;
        List<ServicePackage> availableServicePackages=new ArrayList<>();
        List<Product> allProdsWithorder= new ArrayList<>();
        ServletContext servletContext=getServletContext();
        final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
        if (user!=null){
            //if(user!=null){
                webContext.getSession().setAttribute("user",user);
                webContext.setVariable("user", user);
                userOrders =orderBean.findFromCreator(user);
                System.out.println(userOrders);
                if (!userOrders.isEmpty()){
                    for(Product p: servicePackageBean.findAllProducts())
                        if(p.getOrder()!=null)
                            allProdsWithorder.add(p);
                    webContext.setVariable("active", userOrders);
                    webContext.setVariable("products", allProdsWithorder);
                }
            List<Integer> tmp=findAvailable(user);
            for (Integer i:tmp){
                availableServicePackages.add(servicePackageBean.findServicePackageById(i));
            }
            if (!availableServicePackages.isEmpty()){
                webContext.setVariable("available",availableServicePackages);
            }
            String path="HomePage.html";
            templateEngine.process(path,webContext,resp.getWriter());
        }
        else {
            //availableServicePackages when visit user are always all packages
            availableServicePackages.addAll(servicePackageBean.findAllServicePackages());
            webContext.setVariable("available",availableServicePackages);
            String path="HomePage.html";
            templateEngine.process(path,webContext,resp.getWriter());
        }

    }

    public List<Integer> findAvailable(User user){
        List<ServicePackage> sps=servicePackageBean.findAllServicePackages();
        System.out.println("all service packages not id: " + sps);

        List<ServicePackage> userSp=orderBean.getServicePackagesId(user);

        List<Integer> spsId=new ArrayList<>();
        List<Integer> userSpId=new ArrayList<>();
        List<Integer> available=new ArrayList<>();
        for (ServicePackage s: sps){
            spsId.add(s.getId());
        }
        System.out.println("all service packages id: " + spsId);
        for (ServicePackage s:userSp){
            userSpId.add(s.getId());
        }
        System.out.println("userpackageid: "+userSpId);

        for (Integer i:spsId){
            if(!userSpId.contains(i)){
                available.add(i);
            }
        }
        System.out.println("Available"+available);
        return available;
    }
}

