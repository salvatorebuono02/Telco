package it.polimi.telco.servlets;

import it.polimi.telco.beans.OrderBean;
import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.beans.UserBean;
import it.polimi.telco.entities.Order;
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
        User user=null;
        List<Order> userOrders =null;
        List<ServicePackage> availableServicePackages=new ArrayList<>();
        if (req.getSession().getAttribute("userId")!=null){
            int userId=(int) req.getSession().getAttribute("userId");
            user = userBean.findById(userId);
            ServletContext servletContext=getServletContext();
            final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
            if(user!=null){
                webContext.setVariable("user",user);
                userOrders =orderBean.findFromCreator(user);
                System.out.println(userOrders);
                if (!userOrders.isEmpty())
                    webContext.setVariable("active", userOrders);
            }

            //activeServicePackages
            //availableServicePackages
            List<Integer> tmp=findAvailable(user);
            for (Integer i:tmp){
                if(servicePackageBean.findServicePackageById(i).isPresent())
                    availableServicePackages.add(servicePackageBean.findServicePackageById(i).get());
            }


            if (!availableServicePackages.isEmpty())
                webContext.setVariable("available",availableServicePackages);

            String path="HomePage.html";
            templateEngine.process(path,webContext,resp.getWriter());
        }
        else {
            ServletContext servletContext=getServletContext();
            final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
            //availableServicePackages
            List<Integer> tmp=findAvailable(user);
            for (Integer i:tmp){
                if(servicePackageBean.findServicePackageById(i).isPresent())
                    availableServicePackages.add(servicePackageBean.findServicePackageById(i).get());
            }
            if (!availableServicePackages.isEmpty() )
                webContext.setVariable("available",availableServicePackages);
            String path="HomePage.html";
            templateEngine.process(path,webContext,resp.getWriter());
        }

    }

    public List<Integer> findAvailable(User user){
        List<ServicePackage> sps=servicePackageBean.findAllServicePackages();

        List<ServicePackage> userSp=orderBean.getServicePackagesId(user);

        List<Integer> spsId=new ArrayList<>();
        List<Integer> userSpId=new ArrayList<>();
        List<Integer> available=new ArrayList<>();
        for (ServicePackage s: sps){
            spsId.add(s.getId());
        }
        System.out.println(spsId);
        for (ServicePackage s:userSp){
            userSpId.add(s.getId());
        }
        System.out.println("userpackageid"+userSpId);
        /*for (ServicePackage sp:spIds){
            if (!userSp.contains(sp)){
                System.out.println("sono dentro if: pacchetto è available");
                available.add(sp);
            }

        }*/
        for (Integer i:spsId){
            if(!userSpId.contains(i)){
                available.add(i);
            }
        }
        System.out.println("Available"+available);
        return available;
    }
}

