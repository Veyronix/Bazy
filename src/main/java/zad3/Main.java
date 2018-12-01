package main.java.zad3;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.zad3.Controler.AccountAppController;
import main.java.zad3.Model.Category;
import main.java.zad3.Model.Invoice;
import main.java.zad3.Model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main extends Application {

    private Stage primaryStage;

    private AccountAppController appController;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Shop");

        this.appController = new AccountAppController(primaryStage);
        this.appController.initRootLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }


//}
//public class Main {
    private static SessionFactory sessionFactory = null;
//    public static void main(String[] args) {
//        order();
        /*
        sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
//        Scanner inputScanner = new Scanner(System.in);
//        String prodName = inputScanner.nextLine();
//        String unitsOnStock = inputScanner.nextLine();
        Transaction tx = session.beginTransaction();
        Model.Category category1 = new Model.Category("Ubrania");
        Model.Category category2 = new Model.Category("Samochody");

        Model.Supplier supplier1 = new Model.Supplier("Nike","dluga","Krakow","32-445");
        session.save(supplier1);
        session.save(category1);
        session.save(category2);
//        Model.Product product = new Model.Product(prodName,Integer.parseInt(unitsOnStock));
        Model.Product product1 = new Model.Product("czapki",100);
        Model.Product product2 = new Model.Product("spodnie",200);
        Model.Product product3 = new Model.Product("skarpetki",300);
        product1.supplier = supplier1;
        product2.supplier = supplier1;
        product3.supplier = supplier1;

        session.save(product1);
        session.save(product2);
        session.save(product3);

        category1.Products.add(product1);
        category1.Products.add(product2);
        category1.Products.add(product3);

            */
//        Model.Invoice invoice1 = new Model.Invoice(1,20);
//        Model.Invoice invoice2 = new Model.Invoice(2,30);
//        invoice1.products.add(product1);
//        invoice1.products.add(product2);
//        invoice2.products.add(product2);
//        invoice2.products.add(product3);
//        product1.invoices.add(invoice1);
//        product2.invoices.add(invoice1);
//        product2.invoices.add(invoice2);
//        product3.invoices.add(invoice2);
//        session.save(invoice1);
//        session.save(invoice2);
//
//        session.save(product1);
//        session.save(product2);
//        session.save(product3);
//        tx.commit();
//        session.close();


//    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory = configuration.configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void order(){
        sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        TypedQuery<Category> queryCategories = session.createQuery("from Category as category", Category.class);
        List<Category> categoriesList= queryCategories.getResultList();
        int i = 0;
        System.out.println("Categories in database:");
        for(Category category: categoriesList){
            System.out.println(i + " " +category.getName());
            i++;
        }
        System.out.println("Write number of chosen category");
        Scanner inputScanner = new Scanner(System.in);
        int categoryNumber = Integer.parseInt(inputScanner.nextLine());
        System.out.println(categoriesList.get(categoryNumber).getName());
        List<Product> productsList = new ArrayList<Product>();
        productsList.addAll(categoriesList.get(categoryNumber).getProducts());
        System.out.println("Products in chosen category:");
        i = 0;
        for(Product product: productsList){
            System.out.println(i + " " + product.getProductName() + " quantity = " + product.getUnitsOnStock());
            i++;
        }
        System.out.println("Write number of chosen product");
        inputScanner = new Scanner(System.in);
        int productNumber = Integer.parseInt(inputScanner.nextLine());
        Product chosenProduct = productsList.get(productNumber);
        System.out.println("Write quantity");
        inputScanner = new Scanner(System.in);
        int quantity = Integer.parseInt(inputScanner.nextLine());

        if(quantity > chosenProduct.getUnitsOnStock()){
            System.out.println("Quantity bigger than units on stock");
            tx.commit();
            session.close();
            return;
        }
        System.out.println("Write invoice number");
        inputScanner = new Scanner(System.in);
        int invoiceNumber = Integer.parseInt(inputScanner.nextLine());

        TypedQuery<Invoice> queryInvoices = session.createQuery("from Invoice as invoice " +
                "where invoice.InvoiceNumber = :invoiceNumber", Invoice.class)
                .setParameter("invoiceNumber",invoiceNumber);
        List<Invoice> invoicesList = queryInvoices.getResultList();
        Invoice invoice = null;
        for(Invoice invoiceInList: invoicesList){
            if(invoiceInList.getInvoiceNumber() == invoiceNumber) {
                invoice = invoiceInList;
                break;
            }
        }
        if(invoice == null){
            invoice = new Invoice(invoiceNumber,quantity);
        }
        invoice.products.add(chosenProduct);
        chosenProduct.invoices.add(invoice);
        session.save(invoice);
        session.save(chosenProduct);
        tx.commit();
        session.close();
    }
}




