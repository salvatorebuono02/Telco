package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user",schema = "telco")
@NamedQuery(name="User.checkCredentials", query = "SELECT u FROM User u where u.username = ?1 " +
                                                            "and u.password = ?2")
@NamedQuery(name = "User.checkUsername",query = "select u from User u where u.username=:username")
@NamedQuery(name = "User.findFailedPayments",query = "select u from User u where u.id=:id")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname",length = 45)
    private String firstname;
    @Column(name = "lastname",length = 45)
    private String lastname;
    @Column(name = "email",length = 45)
    private String email;
    @Column(name = "username",length = 45)
    private String username;
    @Column(name = "password",length = 45)
    private String password;
    private boolean insolvent=false;
    private int failedPayments = 0;
   @OneToMany(fetch= FetchType.EAGER, mappedBy="creator",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
   @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments() {
        this.failedPayments = this.failedPayments+1;
    }

    public void removeFailedPayment(){this.failedPayments=this.failedPayments-1;}

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(Order order) {
       if(!this.orders.contains(order))
        this.orders.add(order);
    }

    public boolean isInsolvent() {
        return insolvent;
    }

    public void setInsolvent(boolean insolvent) {
        this.insolvent = insolvent;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
