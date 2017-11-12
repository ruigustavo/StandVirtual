package ejb;

import DTOs.CarDTO;
import DTOs.UserDTO;
import data.Car;
import data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserEJB implements UserEJBInterface {
    @PersistenceContext(name = "Users")
    private EntityManager em;
    Logger logger;

    public UserEJB() {
        logger = LoggerFactory.getLogger(UserEJB.class);
    }


    public int login(String email, String hashedPassword){
        logger.info("Trying to authenticate user: "+ email);
        try{
            Query q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.email= :e");
            q.setParameter("e",email);
            @SuppressWarnings("unchecked")
            User result = (User) q.getSingleResult();
            if(result!=null) {
                if (result.getPassword().compareTo(hashedPassword) == 0) {
                    return result.getId();
                }else{
                    logger.warn("Wrong password.");
                    return -1;
                }
            }
        }catch (Exception e){
            logger.warn("Login failed, user not found.");
        }
        return -1;
    }


    public void register(UserDTO user){
        logger.info("Creating new User with name "+user.getName());
        User toPersist = new User(user.getEmail(),user.getPassword(),user.getName(),user.getAddress(),user.getPhone());
        em.persist(toPersist);
        logger.info("Registered successfully");
    }

    public void editUserInfo(UserDTO user){
        logger.info("Editing User " + user.getId());
        User aux;
        try {
            Query q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.id = :i");
            q.setParameter("i", user.getId());
            aux = (User) q.getSingleResult();
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("User not edited. Returning...");
            return;
        }
        if(user.getPassword()!=null)
            aux.setPassword(user.getPassword());
        aux.setName(user.getName());
        aux.setAddress(user.getAddress());
        aux.setEmail(user.getEmail());
        aux.setPhone(user.getPhone());
        logger.info("Saving Changes...");
        try {
            em.merge(aux);
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("User not edited. Returning...");
            return;
        }

        logger.info("User edited successfully");
    }

    public List<CarDTO> getCarsOfUser(int id){
        logger.info("Getting Cars of User with ID " + id);
        List<Car> aux =null;
        try{
            Query q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.owner.id = :n order by c.price asc");
            q.setParameter("n", id);
            aux = q.getResultList();
        }   catch (Exception e) {
        logger.warn("Dropped Exception");
        e.printStackTrace();
        logger.info("Returning null");
        return null;
    }
        List<CarDTO> toSend = new ArrayList<>();
        for(Car c : aux){
            toSend.add(new CarDTO(c.getBrand(),c.getModel(),c.getPrice(),new UserDTO(c.getOwner().getEmail(),c.getOwner().getName(),c.getOwner().getAddress(),c.getOwner().getPhone())));
        }
        logger.info("Returning Cars of User with ID " + id);
        return toSend;

    }


    public UserDTO getUserById(int id) {
        logger.info("Getting User with ID " + id);
        User aux = null;
       try{
           Query q = em.createQuery("from User s where s.id = :n");
            q.setParameter("n", id);
              aux = (User) q.getSingleResult();
    }   catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning null");
           return null;
       }
        logger.info("Return User with ID " + id);
        return new UserDTO(aux.getId(),
                aux.getEmail(),
                aux.getName(),
                aux.getAddress(),
                aux.getPhone());
    }

    public void deleteUserById(int id){
        em.remove(em.find(User.class, id));
    }

}
