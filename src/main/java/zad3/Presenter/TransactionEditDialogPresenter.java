package main.java.zad3.Presenter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.zad3.Model.Invoice;
import main.java.zad3.Model.Product;

public class TransactionEditDialogPresenter {

	private Invoice invoice;

	@FXML
	private TextField invoiceNumberField;

	@FXML
	private TextField QuantityField;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productUnitsOnStock;

    public boolean isApproved = false;
	private Stage dialogStage;

    public void initialize(){
        productNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
        productUnitsOnStock.setCellValueFactory(dataValue -> dataValue.getValue().getUnitsOnStockProperty());
    }
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(Invoice invoice) {
		this.invoice= invoice;
        productTable.setItems(FXCollections.observableArrayList(invoice.products));
	}

	@FXML
	private void handleOkAction(ActionEvent event) {
		if (isInputValid()) {
			invoice.setInvoiceNumber(Integer.parseInt(invoiceNumberField.getText()));
			invoice.setQuantity(Integer.parseInt(QuantityField.getText()));
		}
		isApproved = true;
		handleCancelAction(new ActionEvent());
	}

	@FXML
	private void handleCancelAction(ActionEvent event) {
		dialogStage.close();
	}







    private boolean isInputValid() {
		return true;
	}


}
