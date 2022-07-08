package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="alert",schema="telco")
public class Alert implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private String userName;

    private String userEmail;

    private float amount;

    private Date lastRejection;

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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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