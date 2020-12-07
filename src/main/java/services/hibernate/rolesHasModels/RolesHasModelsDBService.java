package services.hibernate.rolesHasModels;

import entity.users.UserModel;

public class RolesHasModelsDBService {

    private RolesHasModelsDAO rolesDAO = new RolesHasModelsDAO();

    public void saveRoleHasModels(UserModel userModel) {
        rolesDAO.save(userModel);
    }

}
