package helpers.blogs;

import entity.blogs.BlogTag;
import entity.users.Users;
import helpers.utils.DataGeneratorUtils;
import services.hibernate.tags.BlogTagDBService;

import java.util.List;

public class BlogTagDatabaseHelper {

    private static final BlogTagDBService serviceDbTag = new BlogTagDBService();
    private static final DataGeneratorUtils dataGeneratorUtils = new DataGeneratorUtils();

    /** получение тэга по id */
    public BlogTag getTagFindId(Long id){
        return serviceDbTag.findBlogTagId(id);
    }

    /** получение списка всех тэгов */
    public List<BlogTag> getAllTagList(){
        return serviceDbTag.findBlogTagAll();
    }

    /** получение рандомного тэга */
    public BlogTag getRandomTag() {
        List<BlogTag> tags = getAllTagList();
        BlogTag tag = tags.get(dataGeneratorUtils.getRandIndexCollection(tags));
        return tag;
    }
}
