package ejb;


import DTOs.CarDTO;
import DTOs.UserDTO;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserEJBInterface {
    int login(String email, String hashedPassword);
    int register(UserDTO user);
    void editUserInfo(UserDTO user);
    void deleteUserById(int id);
    List<CarDTO> getCarsOfUser(int id);
    List<CarDTO> getCarsUserNotOwn(int id);
    List<CarDTO> getCarsUserFollow(int id);
    UserDTO getUserById(int id);
}
