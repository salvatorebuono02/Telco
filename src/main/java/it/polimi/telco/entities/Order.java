package it.polimi.telco.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order",schema = "telco")

@NamedQuery(name="Order.findFromId",query="select o from Order o where o.id=:id")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date_of_creation;
    private LocalDate date_of_subscription;

    private boolean confirmed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToOne
    @JoinColumn(name= "serviceId")
    private ServicePackage service;

    @OneToOne
    @JoinColumn(name="validityId")
    private ValidityPeriod validityPeriod;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private List<Product> products;


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

    public LocalDate getDate_of_subscription() {
        return date_of_subscription;
    }

    public void setDate_of_subscription(LocalDate date_of_subscription) {
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

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
