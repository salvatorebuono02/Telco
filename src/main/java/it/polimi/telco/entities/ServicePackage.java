package it.polimi.telco.entities;

import it.polimi.telco.entities.services.FixedInternetService;
import it.polimi.telco.entities.services.FixedPhoneService;
import it.polimi.telco.entities.services.MobileInternetService;
import it.polimi.telco.entities.services.MobilePhoneService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "service_package",schema = "telco")
public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int numOfMonths;
    private int monthlyFee;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Product> products;
/*
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<FixedPhoneService> fixedPhoneServices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<FixedInternetService> fixedInternetServices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<MobilePhoneService> mobilePhoneServices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<MobileInternetService> mobileInternetServices;
*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfMonths() {
        return numOfMonths;
    }

    public void setNumOfMonths(int numOfMonths) {
        this.numOfMonths = numOfMonths;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(int monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
