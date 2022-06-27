package it.polimi.telco.beans;

import it.polimi.telco.entities.User;
import it.polimi.telco.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext(name = "telcoEJB")
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
}
