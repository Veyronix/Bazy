package main.java.zad3.Controler;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import main.java.zad3.Model.Invoice;
import main.java.zad3.Model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import main.java.zad3.Main;
import main.java.zad3.Model.Category;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class AccountOverviewController {

	private AccountAppController appController;

	@FXML
	private TableView<Category> categoryTable;


	@FXML
	private TableColumn<Category, String> nameColumn;


	@FXML
	private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productUnitsOnStock;

    @FXML
    private TableColumn<Product, String> supplierColumn;

	@FXML
	private Button addButton;

	@FXML
	private void initialize() {
		categoryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		nameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
        categoryTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadProducts(categoryTable.getSelectionModel().getSelectedItem());
            }
        });
        productNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
        productUnitsOnStock.setCellValueFactory(dataValue -> dataValue.getValue().getUnitsOnStockProperty());
        supplierColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSupplier());
        addButton.disableProperty().bind(Bindings.isEmpty(productTable.getSelectionModel().getSelectedItems()));
	}

	@FXML
	private void handleAddAction(ActionEvent event) {
		Invoice invoice= new Invoice(-1,0);
        for(Product product: productTable.getSelectionModel().getSelectedItems()){
            invoice.products.add(product);
        }
		if(appController.showTransactionEditDialog(invoice)) {
            Session session = Main.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(invoice);
            for(Product product: productTable.getSelectionModel().getSelectedItems()){
                product.invoices.add(invoice);
                session.save(product);
            }
            tx.commit();
            session.close();
        }
	}

	public void setData() {
        Session session = Main.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        TypedQuery<Category> queryCategories = session.createQuery("from Category as category", Category.class);
        List<Category> categoriesList= queryCategories.getResultList();
        categoryTable.setItems(FXCollections.observableArrayList(categoriesList));
        tx.commit();
        session.close();
	}

	private void loadProducts(Category category){
        Session session = Main.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        TypedQuery<Category> queryProducts = session.createQuery("from Category as category " +
                "where category = :chosen", Category.class)
                .setParameter("chosen",category);
        Set<Product> productsList= queryProducts.getResultList().get(0).Products;
        productTable.setItems(FXCollections.observableArrayList(productsList));
        tx.commit();
        session.close();
    }
	public void setAppController(AccountAppController appController) {
		this.appController = appController;
	}
}
