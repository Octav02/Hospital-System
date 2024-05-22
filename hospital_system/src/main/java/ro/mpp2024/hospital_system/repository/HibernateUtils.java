package ro.mpp2024.hospital_system.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ro.mpp2024.hospital_system.model.Drug;
import ro.mpp2024.hospital_system.model.Prescription;
import ro.mpp2024.hospital_system.model.PrescriptionDetail;
import ro.mpp2024.hospital_system.model.User;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }

    private static  SessionFactory createNewSessionFactory(){
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Drug.class)
                .addAnnotatedClass(Prescription.class)
                .addAnnotatedClass(PrescriptionDetail.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static  void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
}
