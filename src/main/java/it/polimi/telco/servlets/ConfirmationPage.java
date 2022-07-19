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
        String path = null;
        if (req.getParameter("orderId") != null) {
            req.getSession().setAttribute("orderId", req.getParameter("orderId"));
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
        String[] optionalProducts;
        List<Product> buyableProducts= new ArrayList<>();
        List<Product> products = new ArrayList<>();
        float optionalValue = 0;
        int vdId = Integer.parseInt(req.getParameter("validityPeriod"));
        ValidityPeriod validityPeriod = servicePackageBean.findValidityPeriodById(vdId);
        if(req.getParameter("optionalProducts")!=null) {
            optionalProducts = req.getParameterValues("optionalProducts");
            for (String s : optionalProducts)
                products.add(servicePackageBean.findProductById(Integer.parseInt(s)));
            //if(optionalProducts.){ if per caso in cui tutti prodotti comprati di quel pacchetto e nessuno dispoibile
            buyableProducts= buyProd(products);
            if (!buyableProducts.isEmpty()) {
                for (Product p : buyableProducts) {
                    optionalValue = optionalValue + (p.getMonthly_fee()*validityPeriod.getNumOfMonths());
                }
            }
            //}
        }else
            buyableProducts=null;
         String subscription = req.getParameter("validityStart");
        LocalDate s = LocalDate.parse(subscription);
        Date dc = new Date();
        Order order;
        /*orderBean.getOrderManaged(order);*/
        float totalValue = 0;
        float packageValue = 0;
        LocalDate end = s.plusMonths(validityPeriod.getNumOfMonths());
        packageValue = validityPeriod.getMonthly_fee() * validityPeriod.getNumOfMonths();
        totalValue = optionalValue + packageValue;
        if (user != null) {
            webContext.getSession().setAttribute("user", user);
            webContext.setVariable("user", user);
            String orderStatus;
            int status = (int) (((Math.random() * 2)));
            boolean confirmed;
            if (status == 1) {
                orderStatus = "orderOk";
                confirmed=true;
            } else {
                orderStatus = "order not ok ESCI I SOLDI";
                confirmed=false;
                userBean.setInsolvent(user, true);
                userBean.setFailedPayments(user);
            }
            order= orderBean.CreateNewOrder(buyableProducts,dc,s,end,sp,validityPeriod,totalValue,optionalValue,packageValue,user,confirmed);
            System.out.println("user failed payments: "+ userBean.getFails(user.getId()));
            if (userBean.getFails(user.getId()) == 3 && !userBean.userAlertPresent(user)){
                System.out.println("need to create alert");
                userBean.createAlert(user, order);
                System.out.println("user alert created");
            }
            else if (userBean.getFails(user.getId()) > 3 || userBean.userAlertPresent(user)) {
                userBean.updateAlert(userBean.findAlertByUser(user), order);

            }
            webContext.setVariable("orderStatus", orderStatus);
            webContext.setVariable("order", order);
            webContext.setVariable("products", buyableProducts);
            webContext.setVariable("services", services);
            path = "ConfirmationPage.html";
            templateEngine.process(path, webContext, resp.getWriter());
        } else {
            order= orderBean.CreateNewOrder(buyableProducts,dc,s,end,sp,validityPeriod,totalValue,optionalValue,packageValue,null);
            req.getSession().setAttribute("orderId", order.getId());
            path = "index.html";
            templateEngine.process(path, webContext, resp.getWriter());
        }


    }

    private List<Product> buyProd(List<Product> optionalProducts) {
        List<Product> allProds = servicePackageBean.findAllProducts();
        List<Product> buyable= new ArrayList<>();
        for(int j=0; j < optionalProducts.size();j++){
            int finalJ = j;
            Optional<Product> p1= allProds.stream().filter(p -> p.getName().equals(optionalProducts.get(finalJ).getName()) && p.getOrder()==null).findFirst();
            p1.ifPresent(buyable::add);
            }
        return buyable;
    }

    private void orderAlreadyExisting(HttpServletRequest req, HttpServletResponse resp, User user,
                                      WebContext webContext) throws IOException {
        String path;
        int orderId;
        /*if(req.getSession().getAttribute("user")==null)
            orderId = (int) req.getSession().getAttribute("orderId");
        else*/
        if(req.getSession().getAttribute("orderId") instanceof Integer)
            orderId = (int) req.getSession().getAttribute("orderId");
        else
            orderId = Integer.parseInt((String) req.getSession().getAttribute("orderId"));
        Optional<Order> order = orderBean.getOrderFromId(orderId);
        if (order.isPresent()) {
            //User user = userBean.findById(userId);
            int servicePackageId = order.get().getService().getId();
            //check se ordine in sessione è nuova prova di pagamento dell'utente
            List<Product> orderProducts= orderBean.findProductsFromOrder(order.get());
            //if(optionalProducts.){ if per caso in cui tutti prodotti comprati di quel pacchetto e nessuno dispoibile
            boolean orderRetry =
                    (packageAlreadyOwnedByUser(user, order.get(), orderBean) && !order.get().isConfirmed());
            if (!packageAlreadyOwnedByUser(user, order.get(), orderBean) || orderRetry) {
                List<Service> services = servicePackageBean.findServicesFromServicePackageId(servicePackageId);
                if (!packageAlreadyOwnedByUser(user, order.get(), orderBean))
                    orderBean.setCreator(order.get(), user);
                webContext.getSession().setAttribute("user", user);
                webContext.setVariable("user", user);
                webContext.setVariable("order", order.get());
                webContext.setVariable("products", orderProducts);
                webContext.setVariable("services", services);
                String orderStatus;
                int status = (int) (((Math.random() * 2)));
                if (status == 1) {
                    orderStatus = "orderOk";
                    orderBean.setConfirmed(order.get(), true);
                    if(orderProducts!=null)
                        orderBean.updateProductsOrder(order.get());
                    //se la nuova prova di pagamento va a buon fine, tolgo un flag insolvente dall'utente
                    if (orderRetry) {
                        userBean.removeFailedPayments(user);
                        if (userBean.getFails(user.getId()) == 0) {
                            userBean.setInsolvent(user, false);
                            System.out.println("user no more insolvent: " + user.isInsolvent());
                        }
                    }

                } else {
                    orderStatus = "order not ok ESCI I SOLDI";
                    orderBean.setConfirmed(order.get(), false);
                    userBean.setFailedPayments(user);
                    if (!user.isInsolvent()) {
                        userBean.setInsolvent(user, true);
                    }
                }
                orderBean.updateOrder(order.get());
                if (userBean.getFails(user.getId()) >= 3 && !userBean.userAlertPresent(user))
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
