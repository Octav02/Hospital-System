package ro.mpp2024.hospital_system.repository.db;

import ro.mpp2024.hospital_system.model.Drug;
import ro.mpp2024.hospital_system.model.validator.DrugValidator;
import ro.mpp2024.hospital_system.model.validator.Validator;
import ro.mpp2024.hospital_system.repository.DrugRepository;
import ro.mpp2024.hospital_system.repository.HibernateUtils;

public class DrugDBRepository implements DrugRepository {

    private Validator<Drug> validator;

    public DrugDBRepository() {
        this.validator = new DrugValidator();
    }
    @Override
    public Drug findByName(String name) {
        return HibernateUtils.getSessionFactory().openSession().createSelectionQuery("from Drug where name = :name", Drug.class)
                .setParameter("name", name)
                .uniqueResult();
    }

    @Override
    public void add(Drug elem) {
        try {
            validator.validate(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void delete(Drug elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Drug drug = session.createQuery("from Drug where id=:id", Drug.class)
                    .setParameter("id", elem.getId())
                    .uniqueResult();
            if (drug != null) {
                session.remove(drug);
                session.flush();
            }
        });

    }

    @Override
    public void update(Drug elem, Long id) {
        try {
            validator.validate(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (session.find(Drug.class, id) != null) {
                session.merge(elem);
                session.flush();
            }
        });
    }

    @Override
    public Drug findById(Long id) {
        return HibernateUtils.getSessionFactory().openSession().createSelectionQuery("from Drug where id=:id", Drug.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public Iterable<Drug> findAll() {
        return HibernateUtils.getSessionFactory().openSession().createQuery("from Drug", Drug.class).list();
    }
}
