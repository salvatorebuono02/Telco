package it.polimi.telco.beans;

import it.polimi.telco.entities.Alert;
import it.polimi.telco.entities.Employee;
import it.polimi.telco.entities.Order;
import it.polimi.telco.entities.User;
import it.polimi.telco.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;

    public UserBean() {
    }

    public User findById(int userId){ return em.find(User.class,userId);}

    public Integer checkCredentials(String usrn,String pwd) throws CredentialsException, NonUniqueResultException {
        List<User> userList;
        try {
            userList=em.createNamedQuery("User.checkCredentials",User.class).setParameter(1,usrn).setParameter(2,pwd).getResultList();
        } catch (PersistenceException e){
            throw new CredentialsException("Could not verify credentials");
        }
        if (userList.isEmpty())
            return null;
        else if (userList.size()==1){
            User found=userList.get(0);
            return found.getId();
        }
        throw new NonUniqueResultException("More than one user registered with same credentials");
    }
    public Integer checkUsername(String usrn) throws CredentialsException{
        List<User> userList;
        try {
            userList=em.createNamedQuery("User.checkUsername",User.class).setParameter("username",usrn).getResultList();
        }
        catch (PersistenceException e){
            throw new CredentialsException("could not verify username");
        }
        if(userList.isEmpty())
            return null;
        else {
            User found= userList.get(0);
            return found.getId();
        }
    }

    public User createUser(String username,String password,String name, String lastname, String email) throws SQLException {
        User newUser= new User();
        newUser.setFirstname(name);
        newUser.setLastname(lastname);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setInsolvent(false);
        //TODO settare inizio failed payments
        newUser.setFailedPayments();
        em.persist(newUser);
        em.flush();
        return newUser;
    }

    public void createAlert(User user, Order order){
        Alert alert =new Alert(user,order.getTotalValueOrder(),order.getDate_of_creation());
        user.setAlert(alert);
        try{
            em.merge(user);
        } catch (ConstraintViolationException ignored) {}
    }

    public void setFailedPayments(User user ){
        User user1= em.find(User.class, user.getId());
        user1.setFailedPayments();
        em.merge(user1);
    }


    public void removeFailedPayments(User user){
        User user1 = em.find(User.class,user.getId());
        user1.removeFailedPayment();
        em.merge(user1);
    }

    public void setInsolvent(User user, boolean b) {
        User user1 = em.find(User.class, user.getId());
        user1.setInsolvent(b);
        em.merge(user1);
    }

    public void updateAlert(Alert alert, Order order) {
        Alert alert1= em.find(Alert.class, alert.getId());
        alert1.setAmount(order.getTotalValueOrder());
        alert1.setLastRejection(order.getDate_of_creation());
        em.merge(alert1);
    }

    public Alert findAlertByUser(User user) {
        return em.createNamedQuery("Alert.findByUser", Alert.class).setParameter("user",user).getResultStream().findFirst().get();
    }

    public boolean userAlertPresent(User user) {
        if(!em.createNamedQuery("Alert.findByUser", Alert.class).setParameter("user",user).getResultList().isEmpty())
         return true;
        return false;
    }
}
