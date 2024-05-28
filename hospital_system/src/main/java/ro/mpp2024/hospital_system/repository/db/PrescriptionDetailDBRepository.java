package ro.mpp2024.hospital_system.repository.db;

import ro.mpp2024.hospital_system.model.PrescriptionDetail;
import ro.mpp2024.hospital_system.model.validator.PrescriptionDetailValidator;
import ro.mpp2024.hospital_system.model.validator.Validator;
import ro.mpp2024.hospital_system.repository.HibernateUtils;
import ro.mpp2024.hospital_system.repository.PrescriptionDetailRepository;

public class PrescriptionDetailDBRepository implements PrescriptionDetailRepository {
    private Validator<PrescriptionDetail> prescriptionDetailValidator;

    public PrescriptionDetailDBRepository() {
        this.prescriptionDetailValidator = new PrescriptionDetailValidator();
    }

    @Override
    public Iterable<PrescriptionDetail> findByPrescriptionId(Long prescriptionId) {
        return HibernateUtils.getSessionFactory().openSession().createQuery("from PrescriptionDetail where prescriptionId = :prescriptionId", PrescriptionDetail.class)
                .setParameter("prescriptionId", prescriptionId)
                .list();
    }

    @Override
    public Iterable<PrescriptionDetail> findByDrugId(Long drugId) {
        return HibernateUtils.getSessionFactory().openSession().createQuery("from PrescriptionDetail where drugId = :drugId", PrescriptionDetail.class)
                .setParameter("drugId", drugId)
                .list();
    }

    @Override
    public Iterable<PrescriptionDetail> findByDrugIdAndPrescriptionId(Long drugId, Long prescriptionId) {
        return HibernateUtils.getSessionFactory().openSession().createQuery("from PrescriptionDetail where drugId = :drugId and prescriptionId = :prescriptionId", PrescriptionDetail.class)
                .setParameter("drugId", drugId)
                .setParameter("prescriptionId", prescriptionId)
                .list();
    }

    @Override
    public void add(PrescriptionDetail elem) {
        try {
            prescriptionDetailValidator.validate(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void delete(PrescriptionDetail elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            PrescriptionDetail prescriptionDetail = session.createQuery("from PrescriptionDetail where id=:id", PrescriptionDetail.class)
                    .setParameter("id", elem.getId())
                    .uniqueResult();
            if (prescriptionDetail != null) {
                session.remove(prescriptionDetail);
                session.flush();
            }
        });
    }

    @Override
    public void update(PrescriptionDetail elem, Long id) {
        try {
            prescriptionDetailValidator.validate(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(PrescriptionDetail.class, id) != null) {
                session.merge(elem);
                session.flush();
            }
        });
    }

    @Override
    public PrescriptionDetail findById(Long id) {
        return HibernateUtils.getSessionFactory().openSession().find(PrescriptionDetail.class, id);
    }

    @Override
    public Iterable<PrescriptionDetail> findAll() {
        return HibernateUtils.getSessionFactory().openSession().createQuery("from PrescriptionDetail", PrescriptionDetail.class).list();

    }
}
