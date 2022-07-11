package it.polimi.telco.entities.employeeReport;

import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name="InsolventUsers.findAll", query="select i from InsolventUsers i ")
@Table(name="insolventuser", schema="telco")
public class InsolventUsers implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="user_id", nullable = false)
    private int user_id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
