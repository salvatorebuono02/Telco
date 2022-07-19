package it.polimi.telco.entities.employeeReport;

import it.polimi.telco.entities.Order;
import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name="TotalPurchasePackage.findAll", query="select p from TotalPurchasePackage p")
@NamedQuery(name="TotalPurchasePackage.findByPackage", query="select p from TotalPurchasePackage p " +
        "where p.package_id=:package_id")
@Table(name="totalpurchaseperpackage", schema="telco")
public class TotalPurchasePackage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="package_id", nullable = false)
    private int package_id;

    @OneToOne
    @JoinColumn(name="package_id")
    private ServicePackage servicePackage;

    private int totalPurchases;

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }
}
