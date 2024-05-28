package ro.mpp2024.hospital_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.mpp2024.hospital_system.dto.PrescriptionDTO;
import ro.mpp2024.hospital_system.model.Prescription;
import ro.mpp2024.hospital_system.service.Service;
import ro.mpp2024.hospital_system.utils.observer.Observer;

public class PharmacyController implements Observer {
    private final ObservableList<Prescription> prescriptionModel = FXCollections.observableArrayList();
    private final ObservableList<PrescriptionDTO> prescriptionDetailsModel = FXCollections.observableArrayList();
    public TableView<Prescription> prescriptionTableView;
    public TableColumn<Prescription, Long> prescriptionIdTableColumn;
    public TableView<PrescriptionDTO> prescriptionDetailsTableView;
    public TableColumn<PrescriptionDTO, String> medicineTableColumn;
    public TableColumn<PrescriptionDTO, Integer> quantityTableColumn;
    public TableColumn<PrescriptionDTO, String> doctorTableColumn;
    public TableColumn<PrescriptionDTO, String> informationTableColumn;
    private Service service;
    private Long pharmacyId;
    private Prescription selectedPrescription;

    public void initialize() {
        prescriptionIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        medicineTableColumn.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        doctorTableColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        informationTableColumn.setCellValueFactory(new PropertyValueFactory<>("information"));
        initializeTableSelectionListener();
    }

    private void initializeTableSelectionListener() {
        prescriptionTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedPrescription = newValue;
                initPrescriptionDetailsModel();
            }
        });
    }

    private void initPrescriptionDetailsModel() {
        prescriptionDetailsModel.clear();
        Iterable<PrescriptionDTO> prescriptionDetails = service.getPrescriptionDetails(selectedPrescription.getId());
        prescriptionDetails.forEach(prescriptionDetailsModel::add);
        prescriptionDetailsTableView.setItems(prescriptionDetailsModel);
    }

    public void setService(Service service, Long id) {
        this.service = service;
        service.addObserver(this);
        this.pharmacyId = id;
        initModel();
    }

    private void initModel() {
        initPrescriptionsModel();
    }

    private void initPrescriptionsModel() {
        prescriptionModel.clear();
        Iterable<Prescription> prescriptions = service.getAllPendingPrescriptions();
        prescriptions.forEach(prescriptionModel::add);
        prescriptionTableView.setItems(prescriptionModel);
    }

    public void handleFillPrescription(ActionEvent actionEvent) {
        try {
            service.fillPrescription(selectedPrescription.getId(), pharmacyId);
        } catch (Exception e) {
            showError("Failed to fill prescription" + e.getMessage());
        }
    }

    @Override
    public void update() {
        initModel();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.show();
    }

    public void handleLogOut(ActionEvent actionEvent) {
        service.logOut(pharmacyId);
        Stage stage = (Stage) prescriptionTableView.getScene().getWindow();
        stage.close();
    }
}
