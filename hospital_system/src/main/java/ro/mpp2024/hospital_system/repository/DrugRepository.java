package ro.mpp2024.hospital_system.repository;

import ro.mpp2024.hospital_system.model.Drug;

public interface DrugRepository extends Repository<Drug, Long> {
    Drug findByName(String name);
}
