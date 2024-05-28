package ro.mpp2024.hospital_system.service;

import com.google.protobuf.DescriptorProtos;
import ro.mpp2024.hospital_system.model.*;
import ro.mpp2024.hospital_system.repository.DrugRepository;
import ro.mpp2024.hospital_system.repository.PrescriptionDetailRepository;
import ro.mpp2024.hospital_system.repository.PrescriptionRepository;
import ro.mpp2024.hospital_system.repository.UserRepository;
import ro.mpp2024.hospital_system.utils.observer.Observable;
import ro.mpp2024.hospital_system.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Service implements Observable {
    private UserRepository userRepository;
    private DrugRepository drugRepository;
    private PrescriptionRepository prescriptionRepository;
    private PrescriptionDetailRepository prescriptionDetailRepository;
    private List<Observer> observers;

    public Service(UserRepository userRepository, DrugRepository drugRepository, PrescriptionRepository prescriptionRepository, PrescriptionDetailRepository prescriptionDetailRepository) {
        this.userRepository = userRepository;
        this.drugRepository = drugRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionDetailRepository = prescriptionDetailRepository;
        this.observers = new ArrayList<>();
    }

    public boolean login(String username, String password) {
        return userRepository.findByUsername(username).getPassword().equals(password);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void addUser(String firstName, String lastName, String cnp, String username, String password, UserType userType) {
        userRepository.add(new User(firstName, lastName, cnp, password, userType, username));
    }

    public Iterable<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    public void addDrug(String name, String contraindications, int quantity) {
        drugRepository.add(new Drug(name, quantity, contraindications));
    }

    public void updateDrug(String name, String contraindications, int quantity) {
        Drug drug = drugRepository.findByName(name);
        drug.setContraindications(contraindications);
        drug.setStock(quantity);
        drugRepository.update(drug, drug.getId());
    }

    public void deleteDrug(String name) {
        Drug drug = drugRepository.findByName(name);
        drugRepository.delete(drug);
    }

    public Drug findDrugByName(String name) {
        return drugRepository.findByName(name);
    }

    public User getUserById(Long doctorId) {
        return userRepository.findById(doctorId);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    public void addPrescription(Prescription prescription, List<PrescriptionDetail> prescriptionDetails) {
        prescriptionRepository.add(prescription);
        prescriptionDetails.forEach(prescriptionDetail -> {
            prescriptionDetail.setPrescriptionId(prescription.getId());
            prescriptionDetailRepository.add(prescriptionDetail);
        });
        notifyObservers();
    }
}
