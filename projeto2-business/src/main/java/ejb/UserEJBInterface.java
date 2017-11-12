package ejb;


import DTOs.CarDTO;
import DTOs.UserDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserEJBInterface {
    int login(String email, String hashedPassword);
    void register(UserDTO user);
    void editUserInfo(int id, int field, String value);
    void deleteUserById(int id);
    List<CarDTO> getCarsOfUser(int id);
    List<CarDTO> getCarsUserNotOwn(int id);
    List<CarDTO> getCarsUserFollow(int id);
    UserDTO getUserById(int id);
}
