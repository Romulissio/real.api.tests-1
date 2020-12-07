package services.hibernate.tags;

import entity.blogs.BlogTag;
import entity.users.Users;
import org.hibernate.Session;
import services.hibernate.HibernateSessionFactoryUtil;

import java.util.List;

public class BlogTagDAO {

    public BlogTag findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        BlogTag tag = session.get(BlogTag.class, id);
        session.close();
        return tag;
    }

    public List<BlogTag> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<BlogTag> tags = session.createSQLQuery("SELECT * FROM tags").addEntity(BlogTag.class).list();
        session.close();
        return tags;
    }
}
