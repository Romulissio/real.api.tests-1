package services.hibernate.agencies;

import entity.agencies.Agency;
import helpers.agencies.AgencyDatabaseHelper;
import helpers.utils.DataGeneratorEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import services.hibernate.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class AgencyDAO {

    public Agency findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Agency agency = session.get(Agency.class, id);
        session.close();
        return agency;
    }

    public Agency findByInn(Long inn) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createSQLQuery("select * from agencies where inn = " + inn).addEntity(Agency.class);
        Agency agency = (Agency) query.getSingleResult();
        session.close();
        return agency;
    }

    public void save(Agency Agency) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(Agency);
        tx1.commit();
        session.close();
    }

    public void update(Agency Agency) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(Agency);
        tx1.commit();
        session.close();
    }

    public void delete(Agency Agency) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(Agency);
        tx1.commit();
        session.close();
    }

    public void deleteById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Agency agency = session.load(Agency.class, id);
        session.delete(agency);
        tx1.commit();
        session.close();
    }

    public List<Agency> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Agency> agencies = session.createSQLQuery("select * from AGENCIES").addEntity(Agency.class).list();
        session.close();
        return agencies;
    }

    public void deleteAll(List<Agency> agencies) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for(Agency a : agencies){
            session.delete(a);
        }
        tx1.commit();
        session.close();
    }

    public void addAgencyList(List<Agency> agencyList) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for(Agency agency : agencyList){
            session.save(agency);
        }
        tx1.commit();
        session.close();
    }
}
