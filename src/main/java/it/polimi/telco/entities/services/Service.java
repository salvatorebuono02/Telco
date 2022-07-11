package it.polimi.telco.entities.services;


import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;
@MappedSuperclass
public abstract class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id_servicePkg")
    private ServicePackage servicePackage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }
    public abstract String getTypeofService();

}

