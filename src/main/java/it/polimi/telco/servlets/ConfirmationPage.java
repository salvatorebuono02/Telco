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
import javax.xml.xpath.XPath;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
        int servicePackageId = Integer.parseInt(req.getParameter("servicePackageId"));
        List<Service> services = servicePackageBean.findServicesFromServicePackageId(servicePackageId);


        if(orderBean.getOrderInStandBy()==-1) {
            Optional<ServicePackage> sp = servicePackageBean.findServicePackageById(servicePackageId);
            if (sp.isPresent()) {
                //System.out.println(services);
                //TODO crea ordine
                String[] optionalProducts = req.getParameterValues("optionalProducts");
                int vdId = Integer.parseInt(req.getParameter("validityPeriod"));
                String subscription = req.getParameter("validityStart");
                //System.out.println(subscription);
                LocalDate s = LocalDate.parse(subscription);
                Date dc=new Date();
                ArrayList<Product> productArrayList = new ArrayList<>();
                Order order = new Order();
                float totalValue=0;
                if (optionalProducts!=null){
                    for (String pId : optionalProducts){
                        Product product= servicePackageBean.findProductById(Integer.parseInt(pId));
                        productArrayList.add(product);
                        totalValue=totalValue + (product.getMonthly_fee());
                    }
                    order.setProducts(productArrayList);
                }
                ValidityPeriod validityPeriod = servicePackageBean.findValidityPeriodById(vdId);
                LocalDate end= s.plusMonths(validityPeriod.getNumOfMonths());
                totalValue= totalValue + (validityPeriod.getMonthly_fee()*validityPeriod.getNumOfMonths());
                order.setDate_of_creation(dc);
                order.setDate_of_subscription(s);
                order.setDate_end_subscription(end);
                order.setService(sp.get());
                order.setValidityPeriod(validityPeriod);
                order.setTotalValueOrder(totalValue);

                if (userId != null) {
                    User user = userBean.findById(userId);
                    order.setCreator(user);
                    webContext.setVariable("user", user);
                    webContext.setVariable("order", order);
                    webContext.setVariable("services",services);
                    String orderStatus;
                    int status= (int) (((Math.random()*2)));
                    if (status==1){
                        orderStatus="orderOk";
                        order.setConfirmed(true);
                    }
                    else{
                        orderStatus="order not ok ESCI I SOLDI";
                        order.setConfirmed(false);
                        //System.out.println(user.getInsolvent());
                        user.setInsolvent();
                        //System.out.println(user.getInsolvent());
                        user.setOrders(order);
                        //System.out.println("user insolvent orders: "+user.getOrders());
                    }
                    webContext.setVariable("orderStatus",orderStatus);
                    path="ConfirmationPage.html";
                    templateEngine.process(path, webContext, resp.getWriter());
                } else {
                    orderBean.setOrderInStandBy(order.getId());
                    path = "index.html";
                    templateEngine.process(path, webContext, resp.getWriter());
                }

                orderBean.CreateNewOrder(order);
                System.out.println("The order has id: " + order.getId());
            }
        }else{
            Optional<Order> order= orderBean.getOrderFromId(orderBean.getOrderInStandBy());
            if(order.isPresent()){
                User user = userBean.findById(userId);
                order.get().setCreator(user);
                webContext.setVariable("user", user);
                webContext.setVariable("order", order);
                webContext.setVariable("services",services);
                String orderStatus;
                int status= (int) (((Math.random()*2)));
                if (status==1){
                    orderStatus="orderOk";
                    order.get().setConfirmed(true);
                }
                else{
                    orderStatus="order not ok ESCI I SOLDI";
                    order.get().setConfirmed(false);
                    //System.out.println(user.getInsolvent());
                    user.setInsolvent();
                    //System.out.println(user.getInsolvent());
                    user.setOrders(order.get());
                    //System.out.println("user insolvent orders: "+user.getOrders());
                }

                webContext.setVariable("orderStatus",orderStatus);
                path="ConfirmationPage.html";
                templateEngine.process(path, webContext, resp.getWriter());
            }

        }
    }

}
