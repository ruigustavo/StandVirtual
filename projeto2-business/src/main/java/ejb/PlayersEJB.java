package ejb;

import data.Car;
import data.Player;
import data.Team;
import data.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Session Bean implementation class PlayersEJB
 */
@Stateless
public class PlayersEJB implements PlayersEJBInterface {
    @PersistenceContext(name="Players")
    EntityManager em;

    /**
     * Default constructor.
     */
    public PlayersEJB() {
        // TODO Auto-generated constructor stub
    }

    public Date getDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);

        Date d = cal.getTime();
        return d;
    }


    public void populate(){
        User[] u = {//password=test
                new User("koala","098f6bcd4621d373cade4e832627b4f6","userKoala","Anadia","123"),
                new User("r.g.ventura@hotmail.com","098f6bcd4621d373cade4e832627b4f6","user1","Coimbra","123"),
                new User("rgventuraa@gmail.com","098f6bcd4621d373cade4e832627b4f6","user2","Coimbra","123"),

        };

        Car[] c = {
           new Car("mercedes","v5",4000,12345,"December",2005,u[1]),
                new Car("alfa-romeu","v6",1000,123456,"August",2011,u[0]),
                new Car("fiat","punto",10000,1234567,"January",2000,u[1]),
                new Car("fiat","swag",1220,1212334567,"January",2000,u[1]),
                new Car("citroen","c5",100,12345678,"July",2010,u[0])
        };
        c[0].getFollowers().add(u[1]);
        c[0].getFollowers().add(u[1]);
        for (User t : u)
            em.persist(t);
        for (Car t : c)
            em.persist(t);
    }


    public List<Player> playersTallerThan(float threshold) {
        System.out.println("In the EJB. Height = " + threshold);
        Query q = em.createQuery("from Player p where p.height >= :t");
        q.setParameter("t", threshold);
        @SuppressWarnings("unchecked")
        List<Player> result = q.getResultList();
        return result;
    }
}