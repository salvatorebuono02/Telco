package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="alert",schema="telco")
@NamedQuery(name="Alert.findByUser", query="select a from Alert a where a.user=:user")

public class Alert implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH},orphanRemoval = true)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "userName",length = 45)
    private String userName;

    @Column(name = "userEmail",length = 45)
    private String userEmail;


    private float amount;

    private Date lastRejection;

    public Alert(User user, float totalValueOrder, Date date_of_creation) {
        this.user=user;
        this.lastRejection=date_of_creation;
        this.amount=totalValueOrder;
    }

    public Alert() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public it.polimi.telco.entities.User getUser() {
        return user;
    }

    public void setUser(it.polimi.telco.entities.User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName() {
        this.userName = this.user.getUsername();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail() {
        this.userEmail = this.user.getEmail();
    }


    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getLastRejection() {
        return lastRejection;
    }

    public void setLastRejection(Date lastRejection) {
        this.lastRejection = lastRejection;
    }
}
