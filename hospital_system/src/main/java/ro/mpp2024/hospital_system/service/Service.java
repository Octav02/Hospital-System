package ro.mpp2024.hospital_system.service;

import ro.mpp2024.hospital_system.model.Drug;
import ro.mpp2024.hospital_system.model.User;
import ro.mpp2024.hospital_system.model.UserType;
import ro.mpp2024.hospital_system.repository.DrugRepository;
import ro.mpp2024.hospital_system.repository.UserRepository;

public class Service {
    private UserRepository userRepository;
    private DrugRepository drugRepository;

    public Service(UserRepository userRepository, DrugRepository drugRepository) {
        this.userRepository = userRepository;
        this.drugRepository = drugRepository;
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
}
