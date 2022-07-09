package it.polimi.telco.servlets;

import it.polimi.telco.beans.EmployeeBean;
import it.polimi.telco.beans.OrderBean;
import it.polimi.telco.beans.UserBean;
import it.polimi.telco.entities.Employee;
import it.polimi.telco.entities.User;
import it.polimi.telco.exceptions.CredentialsException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB
    private UserBean userBean;

    @EJB
    private EmployeeBean employeeBean;
    @EJB
    private OrderBean orderBean;

    public CheckLogin() {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usrn = null;
        String pwd = null;
        try {
            usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
            pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
            if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        Integer userId = null;
        try {
            // query db to authenticate for user
            userId = userBean.checkCredentials(usrn, pwd);
        } catch (CredentialsException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message
        String path;
        if (userId == null) {
            try {
                userId=employeeBean.checkCredentials(usrn, pwd);
            } catch (CredentialsException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
                return;            }
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            if(userId==null){
                ctx.setVariable("errorMsg", "Incorrect username or password");
                path = "/index.html";
                templateEngine.process(path, ctx, response.getWriter());
            } else {
                Employee employee= employeeBean.findById(userId);
                request.getSession().setAttribute("employee", employee);
                ctx.setVariable("employee", employee);
                path = getServletContext().getContextPath() + "/EmployeeHomePage";
                response.sendRedirect(path);
            }

        } else {
            //TODO si può usare sessione, mettendo order in session e usando di nuovo true e false nel orderStandby
            System.out.println("dentro user login ");
            User user= userBean.findById(userId);
            request.getSession().setAttribute("user", user);
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("user", user);
            if (request.getSession().getAttribute("orderId")!=null) {
                path = getServletContext().getContextPath() + "/ConfirmationPage";
            }
            else{
                System.out.println("user login: " + user);
                path = getServletContext().getContextPath() + "/HomePage";
            }
            response.sendRedirect(path);
        }


    }

    public void destroy() {
    }
}
