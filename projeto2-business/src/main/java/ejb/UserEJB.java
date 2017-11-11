package ejb;

import DTOs.CarDTO;
import DTOs.UserDTO;
import data.Car;
import data.User;

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


    public UserEJB() {
    }


    public int login(String email, String hashedPassword){
        Query q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.email= :e");
        q.setParameter("e",email);
        @SuppressWarnings("unchecked")
        User result = (User) q.getResultList().get(0);
        if(result!=null) {
            if (result.getPassword().compareTo(hashedPassword) == 0) {
                return result.getId();
            }
        }
        return -1;
    }


    public void register(UserDTO user){
        User toPersist = new User(user.getEmail(),user.getPassword(),user.getName(),user.getAddress(),user.getPhone());
        em.persist(toPersist);
    }

    public void editUserInfo(int id, int field, String value){
        User aux = null;
        Query q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.id = :i");
        q.setParameter("i", id);
        aux = (User) q.getSingleResult();

        switch (field){
            case 1:
                aux.setName(value);
                break;
            case 2:
                aux.setEmail(value);
                break;
            case 3:
                aux.setAddress(value);
                break;
            case 4:
                aux.setPhone(value);
                break;
            case 5:
                aux.setPassword(value);
                break;
        }
        em.merge(aux);
    }

    public List<CarDTO> getCarsOfUser(int id){
        Query q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.owner.id = :n order by c.price asc");
        q.setParameter("n", id);
        List<Car> aux = q.getResultList();
        List<CarDTO> toSend = new ArrayList<>();
        for(Car c : aux){
            toSend.add(new CarDTO(c.getBrand(),c.getModel(),c.getPrice(),new UserDTO(c.getOwner().getEmail(),c.getOwner().getName(),c.getOwner().getAddress(),c.getOwner().getPhone())));
        }
        return toSend;

    }


    public UserDTO getUserById(int id) {
        Query q = em.createQuery("from User s where s.id = :n");
        q.setParameter("n", id);
        User aux = (User) q.getSingleResult();

        return new UserDTO(aux.getEmail(),aux.getName(),aux.getName(),aux.getPhone());
    }

    public void deleteUserById(int id){
        em.remove(em.find(User.class, id));
    }

}
