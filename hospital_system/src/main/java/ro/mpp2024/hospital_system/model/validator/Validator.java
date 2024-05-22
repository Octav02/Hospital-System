package ro.mpp2024.hospital_system.model.validator;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
