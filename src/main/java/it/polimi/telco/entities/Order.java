package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order",schema = "telco")

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date_of_creation;
    private Integer hour_of_subscription;
    private Date date_of_subscription;

    private boolean confirmed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToOne
    @JoinColumn(name= "serviceId")
    private ServicePackage service;

    public Order() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(Date date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public Integer getHour_of_subscription() {
        return hour_of_subscription;
    }

    public void setHour_of_subscription(Integer hour_of_subscription) {
        this.hour_of_subscription = hour_of_subscription;
    }

    public Date getDate_of_subscription() {
        return date_of_subscription;
    }

    public void setDate_of_subscription(Date date_of_subscription) {
        this.date_of_subscription = date_of_subscription;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ServicePackage getService() {
        return service;
    }

    public void setService(ServicePackage service) {
        this.service = service;
    }

}
