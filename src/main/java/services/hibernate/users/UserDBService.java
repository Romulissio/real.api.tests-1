package services.hibernate.users;

import entity.users.Users;
import java.util.List;

public class UserDBService {

    private final UserDAO userDAO = new UserDAO();

    public Users findUser(Long id) {
        return userDAO.findById(id);
    }

    public void saveUser(Users users) {
        userDAO.save(users);
    }

    public void updateCollectionsUserByAgencyId(List<Users> usersList) {
        userDAO.updateUsersList(usersList);
    }

    public void cleanAgencyId(Long id) {
        userDAO.cleanAgencyUsers(id);
    }

    public void updateUser(Users users){
        userDAO.update(users);
    }

    public void updateUserCollections(List<Users> users){
        userDAO.updateCollections(users);
    }

    public void deleteUser(Users users) {
        userDAO.delete(users);
    }

    public void generateUsersCollections(List<Users> list){
        userDAO.generateUsers(list);
    }

    public void deleteUsersCollections(List<Users> list){
        userDAO.deleteUsers(list);
    }

    public List<Users> findAll() {
        return userDAO.findAll();
    }

    public List<Users> findAllAgencyId(Long id) {
        return userDAO.findAllAgencyId(id);
    }
}
