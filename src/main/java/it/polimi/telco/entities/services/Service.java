package it.polimi.telco.entities.services;


import it.polimi.telco.entities.ServicePackage;

import javax.persistence.*;
import java.io.Serializable;
@MappedSuperclass
public abstract class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id")
    private ServicePackage servicePackage;

    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

