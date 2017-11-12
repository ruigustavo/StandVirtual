package ejb;


import DTOs.CarDTO;
import DTOs.UserDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserEJBInterface {
    int login(String email, String hashedPassword);
    void register(UserDTO user);
    void editUserInfo(UserDTO user);
    void deleteUserById(int id);
    List<CarDTO> getCarsOfUser(int id);
    UserDTO getUserById(int id);
}
