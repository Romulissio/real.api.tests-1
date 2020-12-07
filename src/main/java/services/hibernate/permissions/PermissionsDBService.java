package services.hibernate.permissions;

import entity.users.Permissions;

public class PermissionsDBService {

    private PermissionsDAO permissionsDAO = new PermissionsDAO();

    public Permissions findBlogPages(Long id) {
        return permissionsDAO.findById(id);
    }
}
