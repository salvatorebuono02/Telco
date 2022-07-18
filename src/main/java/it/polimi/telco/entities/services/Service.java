package it.polimi.telco.entities.services;

import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "service",schema = "telco")
@NamedQuery(name = "Service.findAll",query ="select s from Service as s")
@NamedQuery(name = "Service.findFromId",query = "select s from Service as s where s.id=:id")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "serviceType")
public abstract class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "service_servicePkg",joinColumns = {@JoinColumn(name = "service_id")},inverseJoinColumns = {@JoinColumn(name = "package_id")})
    private List<ServicePackage> servicePackages;

    @Column(name = "serviceType",length = 45)
    private String serviceType;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public void setServicePackages(ServicePackage servicePackage) {
        this.servicePackages.add(servicePackage);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

