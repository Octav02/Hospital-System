package ro.mpp2024.hospital_system.model;

public interface Identifiable<Tid> {
    Tid getId();
    void setId(Tid id);
}
