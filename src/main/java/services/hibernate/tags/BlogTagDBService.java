package services.hibernate.tags;

import entity.blogs.BlogTag;

import java.util.List;

public class BlogTagDBService {

    private static final BlogTagDAO blogTag = new BlogTagDAO();

    public BlogTag findBlogTagId(Long id) {
        return blogTag.findById(id);
    }

    public List<BlogTag> findBlogTagAll(){
        return blogTag.findAll();
    }
}
