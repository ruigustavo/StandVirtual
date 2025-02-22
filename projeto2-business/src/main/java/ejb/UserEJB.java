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
                    logger.info("Login was successful");
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
                Query q = em.createQuery("select count(u) from "+ User.class.getSimpleName() +" u where u.email =:email");
                q.setParameter("email", user.getEmail());

                        if(q.getSingleResult().equals(0L)){
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


    public UserDTO getUserById(int id) {
        logger.info("Getting User with ID " + id);
        User aux = null;
        try{
            Query q = em.createQuery("from User s where s.id = :n");
            q.setParameter("n", id);
            aux = (User) q.getSingleResult();
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning null");
            return null;
        }

        UserDTO user = new UserDTO(aux.getId(),
                aux.getEmail(),
                aux.getName(),
                aux.getAddress(),
                aux.getPhone()
                );
        List<CarDTO> followingCars= new ArrayList<>();
        for(Car c : aux.getFollowingCars()){
            List<UserDTO> followers = new ArrayList<>();
            for(User u: c.getFollowers()){
                followers.add(new UserDTO(u.getId(),
                        u.getEmail(),
                        u.getName(),
                        u.getAddress(),
                        u.getPhone()
                ));
            }
            followingCars.add(new CarDTO(c.getId(),
                    c.getBrand(),
                    c.getModel(),
                    c.getPrice(),
                    c.getKm(),
                    c.getRegistration_month(),
                    c.getRegistration_year(),
                    user,
                    c.getPicture(),
                    followers));
        }
        List<CarDTO> sellingCars= new ArrayList<>();
        for(Car c : aux.getSellingCars()){
            List<UserDTO> followers = new ArrayList<>();
            for(User u: c.getFollowers()){
                followers.add(new UserDTO(u.getId(),
                        u.getEmail(),
                        u.getName(),
                        u.getAddress(),
                        u.getPhone()
                ));
            }
            sellingCars.add(new CarDTO(c.getId(),
                    c.getBrand(),
                    c.getModel(),
                    c.getPrice(),
                    c.getKm(),
                    c.getRegistration_month(),
                    c.getRegistration_year(),
                    user,
                    c.getPicture(),
                    followers
            ));
        }
        user.setFollowingCars(followingCars);
        user.setSellingCars(sellingCars);
        logger.info("Return User with ID " + id);
        return user;
    }

    public void deleteUserById(int id){
        logger.info("Deleting user and all his information:"+id);
        logger.info("Deleting the info about following cars");
        Query q1 = em.createQuery("select u from "+ User.class.getSimpleName()+" u where u.id =:id");
        q1.setParameter("id",id);
        User u = (User) q1.getSingleResult();
        List<Car> cars_f = u.getFollowingCars();
        for(Car c_f : cars_f){
            try {
                c_f.getFollowers().remove(u);
                em.persist(c_f);
            } catch (Exception e) {
                logger.warn("Dropped Exception");
                e.printStackTrace();
                logger.info("Car not unfollowed. Returning...");
            }
        }
        u.getFollowingCars().removeAll(cars_f);
        em.persist(u);

        logger.info("Deleting all the cars that user had for sale");
        List<Car> aux =null;
             Query q2 = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.owner.id = :n ");
            q2.setParameter("n", id);
            aux = q2.getResultList();
        for(Car c : aux){
            logger.info("Deleting car with Id " + id);
            em.remove(em.find(Car.class, c.getId()));
            logger.info("Car "+id+" deleted");
        }
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
