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
            System.out.println(result.getSellingCars());
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


    public int register(UserDTO user){
        logger.info("Checking if email already exists");
        Query q = em.createQuery("select count(u) from"+ User.class.getSimpleName() +"u where u.email =:email");
        q.setParameter("email", user.getEmail());

        if((int)q.getSingleResult()==0){
            logger.info("Creating new User with name "+user.getName());
            User toPersist = new User(user.getEmail(),user.getPassword(),user.getName(),user.getAddress(),user.getPhone());
            em.persist(toPersist);
            logger.info("Registered successfully");
            return 1;
        }
        else{
            logger.info("Register not successful");
            return 0;
        }

    }

    public void editUserInfo(UserDTO user){
        logger.info("Editing User " + user.getId());
        User aux;
        try {
            logger.info("Getting user from db.");
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
            toSend.add(new CarDTO(c.getId(),
                    c.getBrand(),
                    c.getModel(),
                    c.getPrice(),
                    c.getRegistration_month(),
                    c.getRegistration_year(),
                    new UserDTO(c.getOwner().getEmail(),
                            c.getOwner().getName(),
                            c.getOwner().getAddress(),
                            c.getOwner().getPhone()),
                    c.getPicture()));
        }
        logger.info("Returning Cars of User with ID " + id);
        return toSend;

    }

     public List<CarDTO> getCarsUserFollow(int id){
         List<Car> aux = null;
         logger.info("Getting user from db.");
         Query q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.id = :i");
         q.setParameter("i", id);
         User u = (User) q.getSingleResult();
         aux = u.getFollowingCars();
         List<CarDTO> toSend = new ArrayList<>();
         for(Car c : aux){
             toSend.add(new CarDTO(c.getId(),
                     c.getBrand(),
                     c.getModel(),
                     c.getPrice(),
                     c.getRegistration_month(),
                     c.getRegistration_year(),
                     new UserDTO(c.getOwner().getEmail(),
                             c.getOwner().getName(),
                             c.getOwner().getAddress(),
                             c.getOwner().getPhone()),
                     c.getPicture()));
         }
         return toSend;
     }
    public List<CarDTO> getCarsUserNotOwn(int id){
        logger.info("Getting Cars of User with ID " + id);
        List<Car> aux =null;
        try{
            Query q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.owner.id != :n order by c.price asc");
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
            toSend.add(new CarDTO(c.getId(),
                    c.getBrand(),
                    c.getModel(),
                    c.getPrice(),
                    c.getRegistration_month(),
                    c.getRegistration_year(),
                    new UserDTO(c.getOwner().getEmail(),
                            c.getOwner().getName(),
                            c.getOwner().getAddress(),
                            c.getOwner().getPhone()),
                    c.getPicture()));
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
//        User aux;
//        logger.info("Deleting User with Id " + id);
//        Query q = em.createQuery("FROM User c WHERE c.id = :id");
//        q.setParameter("id", id);
//        aux = (User) q.getSingleResult();
//        em.remove(aux);
        try{
            logger.info("Deleting user with Id " + id);
            em.remove(em.find(User.class, id));
            logger.info("User "+id+" deleted");
        }catch (Exception e){
            logger.warn("Dropped exception");
            e.printStackTrace();
            logger.info("User not deleted");
        }


    }

}
