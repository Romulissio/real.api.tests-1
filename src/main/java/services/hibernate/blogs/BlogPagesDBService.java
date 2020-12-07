package services.hibernate.blogs;

import entity.BlogPages;

public class BlogPagesDBService {

    private BlogPagesDAO blogPagesDAO = new BlogPagesDAO();

    public BlogPages findBlogPages(Long id) {
        return blogPagesDAO.findById(id);
    }
}
