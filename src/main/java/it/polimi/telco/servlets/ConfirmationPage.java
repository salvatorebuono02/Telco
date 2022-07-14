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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        final WebContext webContext = new WebContext(req, resp, servletContext, req.getLocale());
        User user = (User) req.getSession().getAttribute("user");
        System.out.println("USER: " + user);
        String path = null;
        if (req.getParameter("orderId") != null) {
            System.out.println("Inside if, first session orderId: " + req.getSession().getAttribute("orderId"));
            req.getSession().setAttribute("orderId", req.getParameter("orderId"));
            System.out.println("session orderId: " + req.getSession().getAttribute("orderId"));
        }
        //se non c'è un ordine pendente
        if (req.getSession().getAttribute("orderId") == null) {
            newOrder(req, resp, user, webContext);
        }
        // c'è un ordine pendente (ordine non andato a buon fine/ ordine da visitor)
        else {
            orderAlreadyExisting(req, resp, user, webContext);

        }
    }

    private boolean packageAlreadyOwnedByUser(User user, Order order, OrderBean orderBean) {
        List<ServicePackage> packages = orderBean.getServicePackagesId(user);
        for (ServicePackage s : packages)
            if (s.getId() == order.getService().getId())
                return true;
        return false;
    }


    private void newOrder(HttpServletRequest req, HttpServletResponse resp, User user, WebContext webContext) throws IOException {
        String path;
        int servicePackageId = Integer.parseInt(req.getParameter("servicePackageId"));
        List<Service> services = servicePackageBean.findServicesFromServicePackageId(servicePackageId);
        ServicePackage sp = servicePackageBean.findServicePackageById(servicePackageId);
        String[] optionalProducts = req.getParameterValues("optionalProducts");
        int vdId = Integer.parseInt(req.getParameter("validityPeriod"));
        String subscription = req.getParameter("validityStart");
        //System.out.println(subscription);
        LocalDate s = LocalDate.parse(subscription);
        Date dc = new Date();
        ArrayList<Product> productArrayList = new ArrayList<>();
        Order order = new Order();
        /*orderBean.getOrderManaged(order);*/
        float totalValue = 0;
        float packageValue = 0;
        float optionalValue = 0;
        ValidityPeriod validityPeriod = servicePackageBean.findValidityPeriodById(vdId);
        if (optionalProducts != null) {
            for (String pId : optionalProducts) {
                Product product = servicePackageBean.findProductById(Integer.parseInt(pId));
                productArrayList.add(product);
                optionalValue = optionalValue + (product.getMonthly_fee()*validityPeriod.getNumOfMonths());
            }

        }

        LocalDate end = s.plusMonths(validityPeriod.getNumOfMonths());
        packageValue = validityPeriod.getMonthly_fee() * validityPeriod.getNumOfMonths();
        totalValue = optionalValue + packageValue;


        if (user != null) {

            webContext.getSession().setAttribute("user", user);
            webContext.setVariable("user", user);
            String orderStatus;
            int status = (int) (((Math.random() * 2)));
            Boolean confirmed;
            if (status == 1) {
                orderStatus = "orderOk";
                confirmed=true;
            } else {
                orderStatus = "order not ok ESCI I SOLDI";
                confirmed=false;
                userBean.setInsolvent(user, true);
                userBean.setFailedPayments(user);
            }
            orderBean.CreateNewOrder(productArrayList,dc,s,end,sp,validityPeriod,totalValue,optionalValue,packageValue,user,confirmed);
            if (user.getFailedPayments() == 3)
                userBean.createAlert(user, order);
            else if (user.getFailedPayments() > 3) {
                userBean.updateAlert(userBean.findAlertByUser(user), order);

            }
            System.out.println("confirmationpage order:" + order);
            webContext.setVariable("orderStatus", orderStatus);
            webContext.setVariable("order", order);
            webContext.setVariable("services", services);
            path = "ConfirmationPage.html";
            templateEngine.process(path, webContext, resp.getWriter());
        } else {
            //System.out.println("confPage 123");
            orderBean.CreateNewOrder(productArrayList,dc,s,end,sp,validityPeriod,totalValue,optionalValue,packageValue,null,null);
//                    orderBean.setOrderInStandBy(order.getId());
            req.getSession().setAttribute("orderId", order.getId());
            System.out.println(req.getSession().getAttribute("orderId"));
            path = "index.html";
            templateEngine.process(path, webContext, resp.getWriter());
        }


    }

    private void orderAlreadyExisting(HttpServletRequest req, HttpServletResponse resp, User user,
                                      WebContext webContext) throws IOException {
        String path;
        int orderId = Integer.parseInt((String) req.getSession().getAttribute("orderId"));
        Optional<Order> order = orderBean.getOrderFromId(orderId);
        if (order.isPresent()) {
            //User user = userBean.findById(userId);
            int servicePackageId = order.get().getService().getId();
            //check se ordine in sessione è nuova prova di pagamento dell'utente
            boolean orderRetry =
                    (packageAlreadyOwnedByUser(user, order.get(), orderBean) && !order.get().isConfirmed());
            if (!packageAlreadyOwnedByUser(user, order.get(), orderBean) || orderRetry) {
                List<Service> services = servicePackageBean.findServicesFromServicePackageId(servicePackageId);
                if (!packageAlreadyOwnedByUser(user, order.get(), orderBean))
                    orderBean.setCreator(order.get(), user);
                webContext.getSession().setAttribute("user", user);
                webContext.setVariable("user", user);
                webContext.setVariable("order", order.get());
                webContext.setVariable("services", services);
                String orderStatus;
                int status = (int) (((Math.random() * 2)));
                if (status == 1) {
                    orderStatus = "orderOk";
                    orderBean.setConfirmed(order.get(), true);
                    //se la nuova prova di pagamento va a buon fine, tolgo un flag insolvente dall'utente
                    if (orderRetry) {
                        userBean.removeFailedPayments(user);
                        if (user.getFailedPayments() == 0) {
                            userBean.setInsolvent(user, false);
                            System.out.println("user no more insolvent: " + user.isInsolvent());
                        }
                    }

                } else {
                    orderStatus = "order not ok ESCI I SOLDI";
                    orderBean.setConfirmed(order.get(), false);
                    //System.out.println(user.getInsolvent());
                    userBean.setFailedPayments(user);
                    if (!user.isInsolvent()) {
                        userBean.setInsolvent(user, true);
                    }
                    System.out.println("user insolvent :" + user.isInsolvent());
                    //System.out.println(user.getInsolvent());
                    //System.out.println("user insolvent orders: "+user.getOrders());
                }
                orderBean.updateOrder(order.get());
                if (user.getFailedPayments() >= 3 && !userBean.userAlertPresent(user))
                    userBean.createAlert(user, order.get());
                    //TODO se un ordine viene cancellato dal db, bisogna controllare che venga totlto se nella lista
                    // degli ordini non pagati
                else if (userBean.userAlertPresent(user)) {
                    userBean.updateAlert(userBean.findAlertByUser(user), order.get());
                }
                req.getSession().setAttribute("orderId", null);
                webContext.setVariable("orderStatus", orderStatus);
                path = "ConfirmationPage.html";
                templateEngine.process(path, webContext, resp.getWriter());
            } else {//ordine già presente nell'elenco dell'utente
                orderBean.removeOrder(order.get());
                webContext.getSession().setAttribute("user", user);
                webContext.setVariable("user", user);
                path = "HomePage.html";
                templateEngine.process(path, webContext, resp.getWriter());

            }

        }
    }
}
