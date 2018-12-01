package main.java.zad3.Model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ProductId;
    private String ProductName;
    private int UnitsOnStock;

    @ManyToOne
    public Supplier supplier;


    @ManyToMany( cascade = {CascadeType.PERSIST})
    public Set<Invoice> invoices;

    public Product(){

    }
    public Product(String ProductName, int UnitsOnStock){
        this.ProductName = ProductName;
        this.UnitsOnStock = UnitsOnStock;
        this.invoices = new HashSet<>();
    }

    public String getProductName() {
        return ProductName;
    }

    public int getUnitsOnStock(){
        return UnitsOnStock;
    }

    public StringProperty getNameProperty(){
        return new SimpleStringProperty(this.getProductName());
    }

    public SimpleObjectProperty<Integer> getUnitsOnStockProperty(){
        return new SimpleObjectProperty<Integer>(this.UnitsOnStock);
    }

    public StringProperty getSupplier(){
        return new SimpleStringProperty(supplier.getCompanyName());
    }
}
