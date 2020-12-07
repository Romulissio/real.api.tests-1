package services.hibernate.rolesHasModels;

import entity.users.UserModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.hibernate.HibernateSessionFactoryUtil;

public class RolesHasModelsDAO {

    public void save(UserModel userModel) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(userModel);
        tx1.commit();
        session.close();
    }
}
