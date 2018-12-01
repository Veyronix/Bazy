package main.java.zad3.Model;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String CompanyName;
    private String street;
    private String city;
    private String zipCode;

    public Supplier(){

    }
    public Supplier(String CompanyName, String street, String city, String zipCode){
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.CompanyName = CompanyName;
    }
    public String getCompanyName(){
        return CompanyName;
    }
}
