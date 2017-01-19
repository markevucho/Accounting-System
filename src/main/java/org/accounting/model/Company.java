package org.accounting.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name="getAllCompanies",query="select c from Company c")
@Table(name="COMPANIES")
public class Company {

    private long id;
    private String name;
    private String founder;
    private long identificationNumber;
    private Set<Client> clients;

    public Company(){
        name=""; founder="";
        clients=new LinkedHashSet<>();
    }
    public Company(String _name,String _founder,long _identificationNumber){
        name=_name;
        founder=_founder;
        identificationNumber=_identificationNumber;
        clients=new LinkedHashSet<>();
    }
    public Company(String _name,String _founder,long _identificationNumber,Set<Client> _clients){
        name=_name;
        founder=_founder;
        identificationNumber=_identificationNumber;
        clients=_clients;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID",nullable=false)
    public long getId(){return id;}
    public void setId(long _id){id=_id;}

    @Column(name="NAME",nullable=false,length=30)
    public String getName(){return name;}
    public void setName(String _name){name=_name; }

    @Column(name="FOUNDER",nullable=false,length=100)
    public String getFounder(){return founder; }
    public void setFounder(String _founder){founder=_founder; }

    @Column(name="IDENT_NUMBER",nullable=false)
    public long getIdentificationNumber(){return identificationNumber;}
    public void setIdentificationNumber(long _identificationNumber){identificationNumber=_identificationNumber;}

    @ManyToMany(mappedBy = "companies",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    public Set<Client> getClients(){return clients; }
    public void setClients(Set<Client> _clients){clients=_clients; }

    @Override
    public boolean equals(Object obj){
        Company comp=(Company) obj;
        if(this.name.equals(comp.getName())&&this.founder.equals(comp.getFounder())&&this.identificationNumber==comp.getIdentificationNumber())
            return true;
        else return false;
    }

}
