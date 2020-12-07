package services.hibernate.blogs;

import entity.BlogPages;
import org.hibernate.Session;
import services.hibernate.HibernateSessionFactoryUtil;

public class BlogPagesDAO {

    public BlogPages findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        BlogPages blogPages = session.get(BlogPages.class, id);
        session.close();
        return blogPages;
    }
}
