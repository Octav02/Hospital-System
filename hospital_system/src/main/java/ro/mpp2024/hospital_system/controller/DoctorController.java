package ro.mpp2024.hospital_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import ro.mpp2024.hospital_system.dto.PrescriptionDTO;
import ro.mpp2024.hospital_system.model.Drug;
import ro.mpp2024.hospital_system.model.Prescription;
import ro.mpp2024.hospital_system.model.PrescriptionDetail;
import ro.mpp2024.hospital_system.model.Status;
import ro.mpp2024.hospital_system.service.Service;
import ro.mpp2024.hospital_system.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class DoctorController implements Observer {
    private final ObservableList<Drug> drugModel = FXCollections.observableArrayList();
    private final ObservableList<PrescriptionDTO> prescriptionModel = FXCollections.observableArrayList();
    public TableView<Drug> medicineTableView;
    public TableColumn<Drug, String> contraindicationsTableColumn;
    public TableColumn<Drug, String> nameTableColumn;
    public TableColumn<Drug, Integer> stockTableColumn;
    public TableColumn<PrescriptionDTO, String> medicineNameTableColumn;
    public TableView<PrescriptionDTO> prescriptionTableView;
    public TableColumn<PrescriptionDTO, Integer> quantityTableColumn;
    public TextField quantityTextField;
    private List<PrescriptionDetail> prescriptionDetails;

    private Service service;
    private Long doctorId;
    private Drug selectedDrug;


    public void handleCreatePrescription(ActionEvent actionEvent) {
        if (prescriptionDetails.isEmpty()) {
            showError("No drugs added to prescription");
            return;
        }
        try {
            Prescription prescription = new Prescription(doctorId, Status.PENDING);
            service.addPrescription(prescription, prescriptionDetails);
        } catch (Exception e) {
            showError("Failed to create prescription");
        }
    }

    public void setService(Service service, Long doctorId) {
        this.service = service;
        this.doctorId = doctorId;
        this.prescriptionDetails = new ArrayList<>();
        service.addObserver(this);
        initModel();
    }

    private void initModel() {
        initDrugModel();
        initPrescriptionModel();
    }

    private void initPrescriptionModel() {
        prescriptionModel.clear();
        prescriptionDetails.clear();
    }

    private void initDrugModel() {
        drugModel.clear();
        Iterable<Drug> drugs = service.getAllDrugs();
        drugs.forEach(drugModel::add);
    }

    public void initialize() {
        medicineTableView.setItems(drugModel);
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contraindicationsTableColumn.setCellValueFactory(new PropertyValueFactory<>("contraindications"));
        stockTableColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        setUpTableSelectionListener();

        prescriptionTableView.setItems(prescriptionModel);
        medicineNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void setUpTableSelectionListener() {
        medicineTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedDrug = newValue;
            }
        });
    }

    public void handleAddDrugToPrescription(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                int quantity = Integer.parseInt(quantityTextField.getText());
                if (selectedDrug != null) {
                    prescriptionModel.add(new PrescriptionDTO(selectedDrug.getName(), quantity, service.getUserById(doctorId).getFirstName() + service.getUserById(doctorId).getLastName(), selectedDrug.getContraindications()));
                    prescriptionDetails.add(new PrescriptionDetail(null, selectedDrug.getId(), quantity));
                }
            } catch (Exception e) {
                showError("Invalid input");
            }
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
        initDrugModel();
    }

    public void handleRemoveDrugFromPrescription(ActionEvent actionEvent) {
        PrescriptionDTO prescriptionDTO = prescriptionTableView.getSelectionModel().getSelectedItem();
        if (prescriptionDTO != null) {
            prescriptionModel.remove(prescriptionDTO);
            prescriptionDetails.removeIf(prescriptionDetail -> prescriptionDetail.getDrugId().equals(service.findDrugByName(prescriptionDTO.getMedicineName()).getId()));
        }

        //TODO: initModel?
    }
}
