package ro.mpp2024.hospital_system.repository;

import ro.mpp2024.hospital_system.model.User;

public interface UserRepository extends Repository<User,Long>{
    User findByUsername(String username);
}
