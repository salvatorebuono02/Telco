package it.polimi.telco.entities.employeeReport;

import it.polimi.telco.entities.ServicePackage;
import it.polimi.telco.entities.ValidityPeriod;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name="TotalPurchasePackageAndValidity.findAll", query="select p from TotalPurchasePackageAndValidity p")
@NamedQuery(name="TotalPurchasePackageAndValidity.findByPackage", query="select p from TotalPurchasePackageAndValidity p where p.package_id=:package_id")
@NamedQuery(name="TotalPurchasePackageAndValidity.findByValidity", query="select p from TotalPurchasePackageAndValidity p where p.valPeriod_id=:valPeriod_id")

@Table(name="totalPurchasePerPackAndValidityPeriod", schema="telco")
public class TotalPurchasePackageAndValidity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="package_id", nullable = false)
    private int package_id;
    @Column(name="valPeriod_id", nullable = false)
    private int valPeriod_id;

    @OneToOne
    @JoinColumn(name="package_id")
    private ServicePackage servicePackage;

    @OneToOne
    @JoinColumn(name="valPeriod_id")
    private ValidityPeriod validityPeriod;

    private int totalPurchases;

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public int getValPeriod_id() {
        return valPeriod_id;
    }

    public void setValPeriod_id(int valPeriod_id) {
        this.valPeriod_id = valPeriod_id;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }
}
