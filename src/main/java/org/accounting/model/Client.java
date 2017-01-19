package org.accounting.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "getAllClients", query = "select c from Client c")
@Table(name="CLIENTS")
public class Client {

    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private int age;
    private Date dateOfRegistration;
    private Set<Company> companies;

    public Client(){
        name=""; surname=""; patronymic="";
        dateOfRegistration=new Date();
        companies=new LinkedHashSet<>();
    }
    public Client(String _name, String _surname,String _patronymic ,int _age){
        name=_name;
        surname=_surname;
        age=_age;
	    patronymic=_patronymic;
        dateOfRegistration=new Date();
        companies=new LinkedHashSet<>();
    }
    public Client(String _name, String _surname,String _patronymic ,int _age,Set<Company> _companies){
        name=_name;
        surname=_surname;
        age=_age;
        patronymic=_patronymic;
        dateOfRegistration=new Date();
        companies=_companies;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID",nullable=false)
    public long getId(){return id; }
    public void setId(long _id){id=_id; }

    @Column(name="NAME",nullable=false,length=15)
    public String getName(){return name; }
    public void setName(String _name){name=_name;}

    @Column(name="SURNAME",nullable=false,length=20)
    public String getSurname(){return surname; }
    public void setSurname(String _surname){surname=_surname;}

    @Column(name="PATRONYMIC",nullable=false,length=20)
    public String getPatronymic(){return patronymic; }
    public void setPatronymic(String _patronymic){patronymic=_patronymic;}

    @Column(name="REGISTRATION_DATE",nullable=false)
    @Temporal(TemporalType.DATE)
    public Date getDateOfRegistration(){return dateOfRegistration; }
    public void setDateOfRegistration(Date _dateOfRegistration){dateOfRegistration=_dateOfRegistration;}

    @Column(name="AGE",nullable=false)
    public int getAge(){return age; }
    public void setAge(int _age){age=_age; }

    @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name="CLIENT_COMPANY",
    joinColumns = @JoinColumn(name="CLIENT_ID",nullable=false,referencedColumnName="ID"),
    inverseJoinColumns = @JoinColumn(name="COMPANY_ID",nullable=false,referencedColumnName="ID"))
    public Set<Company> getCompanies(){return companies; }
    public void setCompanies(Set<Company> _companies){companies=_companies; }

    @Override
    public boolean equals(Object _client){
        Client client=(Client) _client;
        if(this.name.equals(client.getName())&&this.surname.equals(client.getSurname())&&this.patronymic.equals(client.getPatronymic())&&this.age==client.getAge())
         return true;
        else return false;
    }

}
