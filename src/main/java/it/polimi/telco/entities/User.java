package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user",schema = "telco")
@NamedQuery(name="User.checkCredentials", query = "SELECT u FROM User u where u.username = ?1 and u.password = ?2")
@NamedQuery(name = "User.checkUsername",query = "select u from User u where u.username=:username")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private int insolvent = 0;


   @OneToMany(fetch= FetchType.EAGER, mappedBy="creator")
    private List<Order> orders = new ArrayList<>();

   @OneToOne(mappedBy = "user", orphanRemoval = true)
   private Alert alert;

    public User(){

    }

    public int getId() {
        return id;
    }







    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInsolvent() {
        return insolvent;
    }

    public void setInsolvent() {
        this.insolvent = this.insolvent+1;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(Order order) {
            this.orders.add(order);
    }
}
