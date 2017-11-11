package ejb;

import DTOs.CarDTO;
import DTOs.UserDTO;
import com.google.gson.GsonBuilder;
import data.Car;
import data.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class CarEJB implements CarEJBInterface{
    @PersistenceContext(name = "Cars")
    private EntityManager em;


    public CarEJB() {
    }

    public void addCar(CarDTO car){
        Car toPersist = new Car(car.getBrand(),car.getModel(),car.getPrice(),car.getOwner());
        em.persist(toPersist);
    }

    public void editCarInfo(int id, int field, String value){
        Car aux = null;
        Query q = em.createQuery("select u from "+ Car.class.getSimpleName() + " u where u.id = :i");
        q.setParameter("i", id);
        aux = (Car) q.getSingleResult();

        switch (field){
            case 1:
                aux.setBrand(value);
                break;
            case 2:
                aux.setModel(value);
                break;
            case 3:
                aux.setPrice(Integer.parseInt(value));
                break;
        }
        em.merge(aux);
    }


    public String getAllCars(int order){
        List<Car> aux = null;
        Query q=null;
        switch (order) {
        case 1:
             q = em.createQuery("from Car order by price asc");
        break;
            case 2:
             q = em.createQuery("from Car order by price desc");
             break;
        case 3:
             q = em.createQuery("from Car order by brand asc");
        break;
        case 4:
             q = em.createQuery("from Car order by brand desc");
        break;
        case 5:
             q = em.createQuery("from Car order by brand asc,model asc ");
        break;
        case 6:
            q = em.createQuery("from Car order by brand desc,model desc ");
        break;
    }
            aux = q.getResultList();

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(aux);
    }
    public String getCarsByBrand(String brand, int order){
        List<Car> aux ;
        Query q=null;
        switch (order) {
            case 1:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.price asc");
                q.setParameter("n", brand);
                break;
            case 2:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.price desc");
                q.setParameter("n", brand);
                break;
            case 3:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.model asc");
                q.setParameter("n", brand);
            case 4:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.model asc");
                q.setParameter("n", brand);
                break;
        }
        aux = q.getResultList();

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(aux);
    }

    public String getCarsByBrandAndModel(String brand, String model, int order){
        List<Car> aux ;
        Query q=null;
        switch (order) {
            case 1:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where (c.brand = :b AND c.model = :m) order by c.price asc");
                q.setParameter("b", brand);q.setParameter("m", model);
                break;
            case 2:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where (c.brand = :b AND c.model = :m) order by c.price desc");
                q.setParameter("b", brand);q.setParameter("m", model);
                break;
        }
        aux = q.getResultList();

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(aux);
    }
    public String getCarsByPriceRange(long low_value, long up_value, int order){
        List<Car> aux =null;
        Query q=null;
        switch (order) {
            case 2:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.price asc");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 3:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.price desc");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 4:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand asc");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 5:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand desc");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 6:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand asc,c.model asc ");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 7:
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand desc,c.model desc ");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
        }
        aux = q.getResultList();

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(aux);
    }
}
