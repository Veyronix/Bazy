package main.java.zad3.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int InvoiceId;
    private int InvoiceNumber;
    private int Quantity;

    @ManyToMany(mappedBy = "invoices", cascade = {CascadeType.PERSIST})
    public Set<Product> products;

    public Invoice(){

    }

    public Invoice(int InvoiceNumber, int Quantity){
        this.InvoiceNumber = InvoiceNumber;
        this.Quantity = Quantity;
        this.products = new HashSet<>();
    }

    public int getInvoiceNumber() {
        return InvoiceNumber;
    }
    public void setInvoiceNumber(int number){
        this.InvoiceNumber = number;
    }
    public void setQuantity(int quantity){
        this.Quantity = quantity;
    }
}
