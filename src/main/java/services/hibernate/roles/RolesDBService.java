package services.hibernate.roles;

import entity.users.Roles;

public class RolesDBService {

    private RolesDAO rolesDAO = new RolesDAO();

    public Roles findRolesId(Long id) {
        return rolesDAO.findById(id);
    }
}
