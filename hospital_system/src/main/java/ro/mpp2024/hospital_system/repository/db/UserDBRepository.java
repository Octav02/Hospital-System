package ro.mpp2024.hospital_system.repository.db;

import ro.mpp2024.hospital_system.model.User;
import ro.mpp2024.hospital_system.model.validator.UserValidator;
import ro.mpp2024.hospital_system.model.validator.Validator;
import ro.mpp2024.hospital_system.repository.HibernateUtils;
import ro.mpp2024.hospital_system.repository.UserRepository;

public class UserDBRepository implements UserRepository {
    private Validator<User> validator;
    public UserDBRepository() {
        this.validator = new UserValidator();
    }
    @Override
    public User findByUsername(String username) {
        return HibernateUtils.getSessionFactory().openSession().createSelectionQuery("from User where username=:username", User.class).setParameter("username", username).getSingleResultOrNull();
    }

    @Override
    public void add(User elem) {
        try {
            validator.validate(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void delete(User elem) {
        HibernateUtils.getSessionFactory().inTransaction(session ->
        {
            User user = session.find(User.class, elem.getId());
            if (user != null) {
                session.remove(user);
                session.flush();
            }
        });
    }

    @Override
    public void update(User elem, Long id) {
        try {
            validator.validate(elem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            User user = session.find(User.class, id);
            if (user != null) {
                session.merge(elem);
                session.flush();
            }
        });
    }

    @Override
    public User findById(Long id) {
        return HibernateUtils.getSessionFactory().openSession().createSelectionQuery("from User where id=:id", User.class).setParameter("id", id).getSingleResultOrNull();
    }

    @Override
    public Iterable<User> findAll() {
        return HibernateUtils.getSessionFactory().openSession().createSelectionQuery("from User", User.class).list();
    }
}
