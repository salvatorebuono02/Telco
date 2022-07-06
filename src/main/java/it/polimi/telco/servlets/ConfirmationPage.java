package it.polimi.telco.servlets;

import it.polimi.telco.beans.OrderBean;
import it.polimi.telco.beans.ServicePackageBean;
import it.polimi.telco.beans.UserBean;
import it.polimi.telco.entities.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebServlet("/ConfirmationPage")
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext=getServletContext();
        final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
        Integer userId= (Integer) req.getSession().getAttribute("userId");
        String path;
        if(orderBean.getOrderInStandBy()==-1) {
            int servicePackageId = Integer.parseInt(req.getParameter("servicePackageId"));
            Optional<ServicePackage> sp = servicePackageBean.findServicePackageById(servicePackageId);
            if (sp.isPresent()) {
                List<Service> services = servicePackageBean.findServicesFromServicePackageId(servicePackageId);
                //TODO crea ordine
                String[] optionalProducts = req.getParameterValues("optionalProducts");
                int vd = Integer.parseInt(req.getParameter("validityPeriod"));
                String date_creation = req.getParameter("creation");
                String subscription = req.getParameter("validityStart");
                System.out.println(date_creation);
                System.out.println(subscription);
                LocalDateTime dc = LocalDateTime.parse(date_creation);
                LocalDate s = LocalDate.parse(subscription);
                ArrayList<Product> productArrayList = new ArrayList<>();
                for (String pId : optionalProducts)
                    productArrayList.add(servicePackageBean.findProductById(Integer.parseInt(pId)));
                ValidityPeriod validityPeriod = servicePackageBean.findValidityPeriodById(vd);
                Order order = new Order();
                order.setDate_of_creation(dc);
                order.setDate_of_subscription(s);
                order.setService(sp.get());
                order.setValidityPeriod(validityPeriod);
                order.setProducts(productArrayList);
                if (userId != null) {
                    User user = userBean.findById(userId);
                    order.setCreator(user);
                    webContext.setVariable("user", user);
                    webContext.setVariable("order", order);
                    path = "ConfirmationPage.html";
                    templateEngine.process(path, webContext, resp.getWriter());
                } else {
                    path = "index.html";
                    templateEngine.process(path, webContext, resp.getWriter());
                    orderBean.setOrderInStandBy(order.getId());
                }

            }
        }else{
            Optional<Order> order= orderBean.getOrderFromId(orderBean.getOrderInStandBy());
            if(order.isPresent()){
                User user = userBean.findById(userId);
                order.get().setCreator(user);
                webContext.setVariable("user", user);
                webContext.setVariable("order", order);
                path = "ConfirmationPage.html";
                templateEngine.process(path, webContext, resp.getWriter());
            }

        }
    }

}
