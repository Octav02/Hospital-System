package ro.mpp2024.hospital_system.service;

import ro.mpp2024.hospital_system.dto.PrescriptionDTO;
import ro.mpp2024.hospital_system.model.*;
import ro.mpp2024.hospital_system.repository.DrugRepository;
import ro.mpp2024.hospital_system.repository.PrescriptionDetailRepository;
import ro.mpp2024.hospital_system.repository.PrescriptionRepository;
import ro.mpp2024.hospital_system.repository.UserRepository;
import ro.mpp2024.hospital_system.utils.observer.Observable;
import ro.mpp2024.hospital_system.utils.observer.Observer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Service implements Observable {
    private UserRepository userRepository;
    private DrugRepository drugRepository;
    private PrescriptionRepository prescriptionRepository;
    private PrescriptionDetailRepository prescriptionDetailRepository;
    private List<Observer> observers;
    private Set<Long> loggedUsers;

    public Service(UserRepository userRepository, DrugRepository drugRepository, PrescriptionRepository prescriptionRepository, PrescriptionDetailRepository prescriptionDetailRepository) {
        this.userRepository = userRepository;
        this.drugRepository = drugRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionDetailRepository = prescriptionDetailRepository;
        this.observers = new ArrayList<>();
        loggedUsers = new HashSet<>();
    }

    public boolean login(String username, String password) {
        if (loggedUsers.contains(userRepository.findByUsername(username).getId())) {
            throw new RuntimeException("User already logged in");
        }
        loggedUsers.add(userRepository.findByUsername(username).getId());
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
        notifyObservers();

    }

    public void updateDrug(String name, String contraindications, int quantity) {
        Drug drug = drugRepository.findByName(name);
        drug.setContraindications(contraindications);
        drug.setStock(quantity);
        drugRepository.update(drug, drug.getId());
        notifyObservers();

    }

    public void deleteDrug(String name) {
        Drug drug = drugRepository.findByName(name);
        drugRepository.delete(drug);
        notifyObservers();
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

    public Iterable<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Iterable<Prescription> getAllPendingPrescriptions() {
        return prescriptionRepository.findAllPending();
    }

    public Iterable<PrescriptionDTO> getPrescriptionDetails(Long id) {
        List<PrescriptionDTO> prescriptionDetails = new ArrayList<>();
        Prescription prescription = prescriptionRepository.findById(id);
        prescriptionDetailRepository.findByPrescriptionId(id).forEach(prescriptionDetail -> {
            Drug drug = drugRepository.findById(prescriptionDetail.getDrugId());
            prescriptionDetails.add(new PrescriptionDTO(drug.getName(), prescriptionDetail.getQuantity(),
                    userRepository.findById(prescription.getUserId()).getFirstName() +
                            " " + userRepository.findById(prescription.getUserId()).getLastName(),
                    drug.getContraindications()));
        });
        return prescriptionDetails;
    }

    public void fillPrescription(Long id, Long pharmacyId) {
        Prescription prescription = prescriptionRepository.findById(id);
        validatePrescriptionDetails(prescriptionDetailRepository.findByPrescriptionId(id));
        prescription.setStatus(Status.COMPLETED);
        prescriptionRepository.update(prescription, id);
        notifyObservers();
    }

    private void validatePrescriptionDetails(Iterable<PrescriptionDetail> prescriptionDetails) {
        prescriptionDetails.forEach(prescriptionDetail -> {
            Drug drug = drugRepository.findById(prescriptionDetail.getDrugId());
            if (drug.getStock() < prescriptionDetail.getQuantity()) {
                throw new RuntimeException("Not enough stock for drug " + drug.getName());
            }
            else {
                drug.setStock(drug.getStock() - prescriptionDetail.getQuantity());
                drugRepository.update(drug, drug.getId());
            }
        });
    }

    public void logOut(long userId) {
        loggedUsers.remove(userId);
    }
}
