package main.java.zad3.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int CategoryId;
    private String Name;

    @OneToMany
    public Set<Product> Products;


    public Category(){}

    public Category(String name){
        this.Name = name;
        this.Products = new HashSet<>();
//        setName(name);
//        setCategoryProducts(new HashSet<>());
    }

    public String getName() {
        return Name;
    }

    public Set<Product> getProducts(){
        return Products;
    }

    public StringProperty getNameProperty(){
        return new SimpleStringProperty(this.getName());
    }
}
