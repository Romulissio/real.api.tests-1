package services.hibernate.roles;

import entity.users.Roles;
import org.hibernate.Session;
import services.hibernate.HibernateSessionFactoryUtil;

public class RolesDAO {

    public Roles findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Roles roles = session.get(Roles.class, id);
        session.close();
        return roles;
    }

}
