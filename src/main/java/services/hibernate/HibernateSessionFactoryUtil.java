package services.hibernate;

import entity.BlogPages;
import entity.agencies.Agency;
import entity.blogs.BlogTag;
import entity.users.Permissions;
import entity.users.Roles;
import entity.users.UserModel;
import entity.users.Users;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(BlogTag.class);
                configuration.addAnnotatedClass(Agency.class);
                configuration.addAnnotatedClass(Users.class);
                configuration.addAnnotatedClass(BlogPages.class);
                configuration.addAnnotatedClass(Permissions.class);
                configuration.addAnnotatedClass(Roles.class);
                configuration.addAnnotatedClass(UserModel.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
