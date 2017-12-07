package ejb;


import DTOs.CarDTO;
import DTOs.UserDTO;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Local
public interface UserEJBInterface {
    int login(String email, String hashedPassword);
    int register(UserDTO user);
    void editUserInfo(UserDTO user);
    void deleteUserById(int id);
    UserDTO getUserById(int id);
}
