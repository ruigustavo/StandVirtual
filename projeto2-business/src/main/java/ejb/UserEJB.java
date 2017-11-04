package ejb;


import data.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class UserEJB {
    @PersistenceContext(name = "Users")
    private EntityManager em;


    public UserEJB() {
    }


    public int login(String email, String hashedPassword){
        Query q = em.createQuery("from User u where u.email==:e");
        q.setParameter("e",email);
        @SuppressWarnings("unchecked")
        User result = (User) q.getResultList().get(0);
        if(result!=null) {
            if (result.getPassword().compareTo(hashedPassword) == 0) {
                return result.getId();
            }
        }
        return 0;
    }
}
