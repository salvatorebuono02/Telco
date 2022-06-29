package it.polimi.telco.servlets;

import it.polimi.telco.beans.EmployeeBean;
import it.polimi.telco.beans.UserBean;
import it.polimi.telco.exceptions.CredentialsException;
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
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/Register")
public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB
    private UserBean userBean;
    @EJB
    private EmployeeBean employeeBean;

    @Override
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
        String usrn=null;
        String pwd=null;
        try {
            usrn=req.getParameter("username");
            pwd=req.getParameter("pwd");
            if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }
        } catch (Exception e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }
        Integer employeeId=null;
        try {
            employeeId=employeeBean.checkUsername(usrn);
        }
        catch (CredentialsException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
        }
        System.out.println(employeeId);
        if(employeeId==null){
            Integer userId=null;
            try {
                userId=userBean.checkUsername(usrn);
            }
            catch (CredentialsException e){
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            }
            if(userId==null){
                String role= req.getParameter("role");
                System.out.println(role);
                if(Objects.equals(role, "user")){
                    try {
                        System.out.println("stai dentro il try");
                        userBean.createUser(usrn,pwd);
                        gotoLogin(req,resp);
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"problem during creation of new user");
                    }
                }
                else{
                    try {
                        System.out.println("Stai dentro il try dell'employee");
                        employeeBean.createEmployee(usrn,pwd);
                        gotoLogin(req,resp);
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"problem during creation of new employee");
                    }
                }
            }else unavailableUsername(req,resp);

        }
        else unavailableUsername(req,resp);
    }

    public void unavailableUsername(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        ServletContext servletContext =getServletContext();
        final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
        webContext.setVariable("errorMsg","username already taken");
        String path="/index.html";
        templateEngine.process(path,webContext,resp.getWriter());
    }

    public void gotoLogin(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        ServletContext servletContext =getServletContext();
        final WebContext webContext=new WebContext(req,resp,servletContext,req.getLocale());
        webContext.setVariable("errorMsg","registration done, please login");
        String path="/index.html";
        templateEngine.process(path,webContext,resp.getWriter());
    }
}

