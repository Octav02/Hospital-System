package ro.mpp2024.hospital_system.repository;

import ro.mpp2024.hospital_system.model.Prescription;

public interface PrescriptionRepository extends Repository<Prescription, Long>{
    Iterable<Prescription> findByUserId(Long userId);

    Iterable<Prescription> findAllPending();
}
