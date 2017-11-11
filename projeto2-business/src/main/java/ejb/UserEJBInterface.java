package ejb;


import DTOs.UserDTO;

import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface UserEJBInterface {
    int login(String email, String hashedPassword);
    void register(UserDTO user);
    void editUserInfo(int id, int field, String value);
    void deleteUserById(int id);
    String getCarsOfUser(int id);
    String getUserById(int id);
}
