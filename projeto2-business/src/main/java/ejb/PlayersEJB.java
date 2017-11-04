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


    public void populate() {
        Team [] teams = { new Team("Sporting", "Alvalade", "Carvalho"), new Team("Academica", "Coimbra", "Simões"), new Team("Porto", "Antas", "Costa"), new Team("Benfica", "Luz", "Vieira") };
        Player [] players = {
                new Player("Albino", getDate(23,4,1987), 1.87f, teams[0]),
                new Player("Bernardo", getDate(11,4,1987), 1.81f, teams[0]),
                new Player("Cesar", getDate(12,5,1983), 1.74f, teams[0]),
                new Player("Dionisio", getDate(3,12,1992), 1.67f, teams[0]),
                new Player("Eduardo", getDate(31,8,1985), 1.89f, teams[0]),
                new Player("Franco", getDate(6,1,1989), 1.95f, teams[1]),
                new Player("Gil", getDate(7,12,1986), 1.8f, teams[1]),
                new Player("Helder", getDate(14,5,1987), 1.81f, teams[1]),
                new Player("Ilidio", getDate(13,6,1991), 1.82f, teams[1]),
                new Player("Jacare", getDate(4,2,1993), 1.83f, teams[1]),
                new Player("Leandro", getDate(4,10,1984), 1.81f, teams[2]),
                new Player("Mauricio", getDate(3,6,1984), 1.8f, teams[2]),
                new Player("Nilton", getDate(11,3,1985), 1.88f, teams[2]),
                new Player("Oseias", getDate(23,11,1990), 1.74f, teams[2]),
                new Player("Paulino", getDate(14,9,1986), 1.75f, teams[2]),
                new Player("Quevedo", getDate(10,10,1987), 1.77f, teams[2]),
                new Player("Renato", getDate(7,7,1991), 1.71f, teams[3]),
                new Player("Saul", getDate(13,7,1992), 1.86f, teams[3]),
                new Player("Telmo", getDate(4,1,1981), 1.88f, teams[3]),
                new Player("Ulisses", getDate(29,8,1988), 1.84f, teams[3]),
                new Player("Vasco", getDate(16,5,1988), 1.83f, teams[3]),
                new Player("X", getDate(8,12,1990), 1.82f, teams[3]),
                new Player("Ze", getDate(13,5,1987), 1.93f, teams[3]),
        };

        User[] u = {
                new User("cona@cona.pt","cona123","cona","meio das pernas","123"),
                new User("cornudo@cornudo.pt","cona123","cona","meio das pernas","123"),

        };

        Car[] c = {
           new Car("mercedes","v5","4000",u[1])
        };
        c[0].getFollowers().add(u[0]);


        for (User t : u)
            em.persist(t);
        for (Car t : c)
            em.persist(t);


        for (Team t : teams)
            em.persist(t);

        for (Player p : players)
            em.persist(p);
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