package ro.mpp2024.hospital_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.mpp2024.hospital_system.model.Drug;
import ro.mpp2024.hospital_system.service.Service;
import ro.mpp2024.hospital_system.utils.observer.Observer;

public class AdministratorController implements Observer {
    public TableView<Drug> drugTableView;
    public TableColumn<Drug, String> drugNameTableColumn;
    public TextField nameTextField;
    public TextArea contraindicationsTextField;
    public TextField quantityTextField;
    public TableColumn<Drug, String> drugContraindicationsTableColumn;
    public TableColumn<Drug, Integer> drugStockTableColumn;
    private Service service;

    private ObservableList<Drug> drugsModels = FXCollections.observableArrayList();
    private long userId;

    public void setService(Service service, Long id) {
        this.service = service;
        service.addObserver(this);
        initModel();
        this.userId = id;
    }

    private void initModel() {
        drugsModels.clear();
        Iterable<Drug> drugs = service.getAllDrugs();
        drugs.forEach(drugsModels::add);
        drugTableView.setItems(drugsModels);
    }

    public void initialize() {
        drugNameTableColumn.setCellValueFactory(new PropertyValueFactory<Drug, String>("name"));
        drugContraindicationsTableColumn.setCellValueFactory(new PropertyValueFactory<Drug, String>("contraindications"));
        drugStockTableColumn.setCellValueFactory(new PropertyValueFactory<Drug, Integer>("stock"));
        setUpTableSelectionListener();

    }

    private void setUpTableSelectionListener() {
        drugTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameTextField.setText(newValue.getName());
                contraindicationsTextField.setText(newValue.getContraindications());
                quantityTextField.setText(String.valueOf(newValue.getStock()));
            }
        });
    }

    public void handleLogOut(ActionEvent actionEvent) {
        service.logOut(userId);
        Stage stage = (Stage) drugTableView.getScene().getWindow();
        stage.close();
    }

    public void handleAddDrug(ActionEvent actionEvent) {
        try {
            String name = nameTextField.getText();
            String contraindications = contraindicationsTextField.getText();
            int quantity = Integer.parseInt(quantityTextField.getText());
            if (service.findDrugByName(name) != null) {
                quantity += service.findDrugByName(name).getStock();
                service.updateDrug(name, contraindications, quantity);
            } else {
                service.addDrug(name, contraindications, quantity);
            }
            initModel();
        } catch (Exception e) {
            showError("Invalid input");
        }
    }

    public void handleUpdateDrug(ActionEvent actionEvent) {
        try {
            String name = nameTextField.getText();
            String contraindications = contraindicationsTextField.getText();
            int quantity = Integer.parseInt(quantityTextField.getText());
            service.updateDrug(name, contraindications, quantity);
            initModel();
        } catch (Exception e) {
            showError("No drug selected");
        }
    }

    public void handleDeleteDrug(ActionEvent actionEvent) {
        try {
            Drug drug = drugTableView.getSelectionModel().getSelectedItem();
            service.deleteDrug(drug.getName());
            drugsModels.remove(drug);
            initModel();
        } catch (Exception e) {
            showError("No drug selected");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.show();
    }

    @Override
    public void update() {
        initModel();
    }
}
