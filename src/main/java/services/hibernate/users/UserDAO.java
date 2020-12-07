package services.hibernate.users;

import entity.agencies.Agency;
import entity.users.Users;
import helpers.agencies.AgencyDatabaseHelper;
import helpers.utils.DataGeneratorEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.hibernate.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public Users findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Users users = session.get(Users.class, id);
        session.close();
        return users;
    }

    public void save(Users Users) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(Users);
        tx1.commit();
        session.close();
    }

    public void update(Users users) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(users);
        tx1.commit();
        session.close();
    }

    public void updateCollections(List<Users> users) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for(Users u : users) {
            session.update(u);
        }
        tx1.commit();
        session.close();
    }

    public void delete(Users users) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(users);
        tx1.commit();
        session.close();
    }

    public void deleteUsers(List<Users> list) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for(Users user : list){
            session.delete(user);
        }
        tx1.commit();
        session.close();
    }

    public void generateUsers(List<Users> list) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for(Users user : list){
            session.save(user);
        }
        tx1.commit();
        session.close();
    }

    public List<Users> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Users> users = session.createSQLQuery("select * from USERS").addEntity(Users.class).list();
        session.close();
        return users;
    }

    public void updateUsersList(List<Users> usersList) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        for(Users user : usersList){
            session.delete(user);
        }

        tx1.commit();
        session.close();
    }

    public List<Users> findAllAgencyId(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<Users> users = session.createSQLQuery("SELECT * FROM users WHERE agency_id =" + id).addEntity(Users.class).list();
        tx1.commit();
        session.close();
        return users;
    }

    public void cleanAgencyUsers(Long id){
        String str = "UPDATE users SET agency_id = NULL WHERE agency_id = 34;";
        System.out.println(str);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.createSQLQuery(str);
        tx1.commit();
        session.close();
    }
}
