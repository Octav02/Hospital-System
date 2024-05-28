package ro.mpp2024.hospital_system.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ro.mpp2024.hospital_system.dto.PrescriptionDTO;
import ro.mpp2024.hospital_system.service.Service;
import ro.mpp2024.hospital_system.utils.observer.Observer;

public class PharmacyController implements Observer {
    public TableView prescriptionTableView;
    public TableColumn prescriptionIdTableColumn;
    public TableColumn doctorTableColumn;
    public TableView<PrescriptionDTO> prescriptionDetailsTableView;
    public TableColumn medicineTableColumn;
    public TableColumn quantityTableColumn;
    public TableColumn DoctorTableColumn;
    public TableColumn informationTableColumn;

    private Service service;
    private Long pharmacyId;

    public void handleFillPrescription(ActionEvent actionEvent) {
    }

    public void setService(Service service, Long id) {
        this.service = service;
        service.addObserver(this);
        this.pharmacyId = id;
    }

    @Override
    public void update() {
        //TODO
    }
}
