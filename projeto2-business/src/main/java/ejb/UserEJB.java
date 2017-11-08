package ejb;


import DTOs.UserDTO;
import data.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UserEJB implements UserEJBInterface {
    @PersistenceContext(name = "Users")
    private EntityManager em;


    public UserEJB() {
    }


    public int login(String email, String hashedPassword){
        Query q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.email= :e");
        q.setParameter("e",email);
        @SuppressWarnings("unchecked")
        User result = (User) q.getResultList().get(0);
        if(result!=null) {
            System.out.println("TESTING TESTING TESTING");
            System.out.println(result.toString());
            if (result.getPassword().compareTo(hashedPassword) == 0) {
                System.out.println("TRUE TRUE TRUE");
                return result.getId();
            }
        }
        return -1;
    }


    public void register(UserDTO user){
        User toPersist = new User(user.getEmail(),user.getPassword(),user.getName(),user.getAddress(),user.getPhone());
        em.persist(toPersist);
    }

}
