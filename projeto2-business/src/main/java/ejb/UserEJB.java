package ejb;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DTOs.UserDTO;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import data.Car;
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

    public String getCarsOfUser(int id){
        Query q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.owner.id = :n order by c.price asc");
        q.setParameter("n", id);
        List<Car> s = q.getResultList();
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(s);

    }


    public String getUserById(int id) {
        String aux = null;

            Query q = em.createQuery("from User s where s.id = :n");
            q.setParameter("n", id);
            aux = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(q.getSingleResult());


        return aux;
    }

    public void deleteUserById(int id){
        em.remove(em.find(User.class, id));
    }

}
