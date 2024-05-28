package ro.mpp2024.hospital_system.repository.db;

import ro.mpp2024.hospital_system.model.Prescription;
import ro.mpp2024.hospital_system.model.validator.PrescriptionValidator;
import ro.mpp2024.hospital_system.repository.HibernateUtils;
import ro.mpp2024.hospital_system.repository.PrescriptionRepository;

public class PrescriptionDBRepository implements PrescriptionRepository {
    private PrescriptionValidator prescriptionValidator;


    public PrescriptionDBRepository() {
        this.prescriptionValidator = new PrescriptionValidator();
    }
    @Override
    public void add(Prescription elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void delete(Prescription elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Prescription prescription = session.createQuery("from Prescription where id=:id", Prescription.class)
                    .setParameter("id", elem.getId())
                    .uniqueResult();
            if (prescription != null) {
                session.remove(prescription);
                session.flush();
            }
        });

    }

    @Override
    public void update(Prescription elem, Long id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(Prescription.class, id) != null) {
                session.merge(elem);
                session.flush();
            }
        });
    }

    @Override
    public Prescription findById(Long id) {
        return HibernateUtils.getSessionFactory().openSession().find(Prescription.class, id);
    }

    @Override
    public Iterable<Prescription> findAll() {
        return HibernateUtils.getSessionFactory().openSession().createQuery("from Prescription", Prescription.class).list();
    }

    @Override
    public Iterable<Prescription> findByUserId(Long userId) {
        return HibernateUtils.getSessionFactory().openSession().createQuery("from Prescription where userId = :userId", Prescription.class)
                .setParameter("userId", userId)
                .list();
    }

    @Override
    public Iterable<Prescription> findAllPending() {
        return HibernateUtils.getSessionFactory().openSession().createQuery("from Prescription where status = 0", Prescription.class).list();
    }
}
