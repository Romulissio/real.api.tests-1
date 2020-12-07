package services.hibernate.permissions;

import entity.users.Permissions;
import org.hibernate.Session;
import services.hibernate.HibernateSessionFactoryUtil;

public class PermissionsDAO {

    public Permissions findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Permissions permissions = session.get(Permissions.class, id);
        session.close();
        return permissions;
    }
}
