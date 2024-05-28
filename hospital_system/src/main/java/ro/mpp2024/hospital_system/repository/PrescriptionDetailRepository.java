package ro.mpp2024.hospital_system.repository;
import ro.mpp2024.hospital_system.model.PrescriptionDetail;

public interface PrescriptionDetailRepository extends Repository<PrescriptionDetail, Long>{
    Iterable<PrescriptionDetail> findByPrescriptionId(Long prescriptionId);
    Iterable<PrescriptionDetail> findByDrugId(Long drugId);
    Iterable<PrescriptionDetail> findByDrugIdAndPrescriptionId(Long drugId, Long prescriptionId);
}
