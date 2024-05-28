package ro.mpp2024.hospital_system.dto;

public class PrescriptionDTO {
    private String medicineName;
    private int quantity;
    private String doctorName;
    private String information;
    private Long prescriptionId;

    public PrescriptionDTO(String medicineName, int quantity, String doctorName, String information) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.doctorName = doctorName;
        this.information = information;
    }

    public PrescriptionDTO(String medicineName, int quantity, String doctorName, String information, Long prescriptionId) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.doctorName = doctorName;
        this.information = information;
        this.prescriptionId = prescriptionId;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "PrescriptionDTO{" +
                "medicineName='" + medicineName + '\'' +
                ", quantity=" + quantity +
                ", doctorName='" + doctorName + '\'' +
                ", information='" + information + '\'' +
                '}';
    }
}
