package ejb;


import javax.ejb.Local;

@Local
public interface UserEJBInterface {
    int login(String email, String hashedPassword);
}
