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
public class CarEJB implements CarEJBInterface{
    @PersistenceContext(name = "Cars")
    private EntityManager em;
    Logger logger;


    public CarEJB() {
        logger = LoggerFactory.getLogger(CarEJB.class);
    }

    public void addCar(CarDTO car){
        logger.info("Getting owner from db.");
        Query q = em.createQuery("select u from "+ User.class.getSimpleName()+ " u where u.email = :i ");
        q.setParameter("i",car.getOwner().getEmail());
        logger.info("Creating new Car.");
        Car toPersist = new Car(car.getBrand(),car.getModel(),car.getPrice(),car.getRegistration_month(),car.getRegistration_year(),car.getPicture(),(User) q.getSingleResult());
        logger.info("Persisting it to the db.");
        em.persist(toPersist);
    }

    public void editCarInfo(int id, int field, String value){
        logger.info("Editing Car with ID " + id +" in field " + field);
        Car aux = null;
        logger.info("Getting car from db.");
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
                aux.setPrice(Long.parseLong(value));
                break;
        }
        logger.info("Saving Changes to the car...");
        try {
            em.merge(aux);
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Car not edited. Returning...");
            return;
        }
        logger.info("Car edited successfully");
    }
//TODO ROGERIO NOOB
    public void deleteCarById(int id){
      try{
            logger.info("Deleting car with Id " + id);
            em.remove(em.find(Car.class, id));
            logger.info("Car "+id+" deleted");
}catch (Exception e){
        logger.warn("Dropped exception");
        e.printStackTrace();
        logger.info("Car not deleted");
        }
    }
    public void unfollowCar(int car_id, int user_id){
        Car aux_car;
        User aux_user;
        logger.info("Getting car from db.");
        Query q = em.createQuery("select u from "+ Car.class.getSimpleName() + " u where u.id = :i");
        q.setParameter("i", car_id);
        aux_car = (Car) q.getSingleResult();
        logger.info("Getting car from db.");
        q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.id = :i");
        q.setParameter("i", user_id);
        aux_user = (User) q.getSingleResult();
        aux_car.getFollowers().remove(aux_user);

        logger.info("Saving Changes ...");
        try {
            em.persist(aux_user);
            em.persist(aux_car);
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Car not followed. Returning...");
            return;
        }
        logger.info("Car unfollowed successfully");
    }
    public  void followCar(int car_id, int user_id){
        Car aux_car;
        User aux_user;
        logger.info("Getting car from db.");
        Query q = em.createQuery("select u from "+ Car.class.getSimpleName() + " u where u.id = :i");
        q.setParameter("i", car_id);
        aux_car = (Car) q.getSingleResult();
        logger.info("Getting car from db.");
        q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.id = :i");
        q.setParameter("i", user_id);
        aux_user = (User) q.getSingleResult();
        aux_car.getFollowers().add(aux_user);

        logger.info("Saving Changes ...");
        try {
            em.persist(aux_user);
            em.persist(aux_car);
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Car not followed. Returning...");
            return;
        }
        logger.info("Car followed successfully");
    }
    public List<CarDTO> getAllCars(int order){
        logger.info("Getting all Cars");
        List<Car> aux = null;
        Query q=null;
        switch (order) {
            case 1:
                logger.info(" Getting cars from ascending order by price.");
                q = em.createQuery("from Car order by price asc");
                break;
            case 2:
                logger.info("Getting cars  descending order by price");
                q = em.createQuery("from Car order by price desc");
                break;
            case 3:
                logger.info("Getting cars  ascending order by brand.");
                q = em.createQuery("from Car order by brand asc");
                break;
            case 4:
                logger.info("Getting cars  descending order by brand.");
                 q = em.createQuery("from Car order by brand desc");
                break;
            case 5:
                logger.info("Getting cars from ascending order by brand and model.");
                 q = em.createQuery("from Car order by brand asc,model asc ");
                break;
            case 6:
                logger.info("Getting carsdescending order by brand and model.");
                q = em.createQuery("from Car order by brand desc,model desc ");
                break;
        }
        aux = q.getResultList();

        List<CarDTO> toSend = new ArrayList<>();
        for(Car c : aux){
            toSend.add(new CarDTO(c.getBrand(),c.getModel(),c.getPrice(),c.getRegistration_month(),c.getRegistration_year(),new UserDTO(c.getOwner().getEmail(),c.getOwner().getName(),c.getOwner().getAddress(),c.getOwner().getPhone())));
        }
        logger.info("Returning all Cars");
        return toSend;
    }

    public List<CarDTO> getCarsByBrand(String brand, int order){
        logger.info("Getting all Cars of the brand:"+brand);
        List<Car> aux ;
        Query q=null;
        switch (order) {
            case 1:
                logger.info("Getting all Cars of the brand:"+brand+"in ascending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.price asc");
                q.setParameter("n", brand);
                break;
            case 2:
                logger.info("Getting all Cars of the brand:"+brand+"in descending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.price desc");
                q.setParameter("n", brand);
                break;
            case 3:
                logger.info("Getting all Cars of the brand:"+brand+"in ascending order by model");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.model asc");
                q.setParameter("n", brand);
            case 4:
                logger.info("Getting all Cars of the brand:"+brand+"in descending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.model asc");
                q.setParameter("n", brand);
                break;
        }
        aux = q.getResultList();
        List<CarDTO> toSend = new ArrayList<>();
        for(Car c : aux){
            toSend.add(new CarDTO(c.getBrand(),c.getModel(),c.getPrice(),c.getRegistration_month(),c.getRegistration_year(),new UserDTO(c.getOwner().getEmail(),c.getOwner().getName(),c.getOwner().getAddress(),c.getOwner().getPhone())));
        }
        logger.info("Return all Cars of the brand:"+brand);
        return toSend;
        }

    public List<CarDTO> getCarsByBrandAndModel(String brand, String model, int order){
        logger.info("Getting all Cars of the brand:"+brand+"and model:"+model);
        List<Car> aux ;
        Query q=null;
        switch (order) {
            case 1:
                logger.info("Getting all Cars of the brand:"+brand+"and model:"+model+"in ascending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where (c.brand = :b AND c.model = :m) order by c.price asc");
                q.setParameter("b", brand);q.setParameter("m", model);
                break;
            case 2:
                logger.info("Getting all Cars of the brand:"+brand+"and model:"+model+"in descending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where (c.brand = :b AND c.model = :m) order by c.price desc");
                q.setParameter("b", brand);q.setParameter("m", model);
                break;
        }
        aux = q.getResultList();
        List<CarDTO> toSend = new ArrayList<>();
        for(Car c : aux){
            toSend.add(new CarDTO(c.getBrand(),c.getModel(),c.getPrice(),c.getRegistration_month(),c.getRegistration_year(),new UserDTO(c.getOwner().getEmail(),c.getOwner().getName(),c.getOwner().getAddress(),c.getOwner().getPhone())));
        }
        logger.info("Return all Cars of the brand:"+brand+"and model:"+model);
        return toSend;
    }
    public List<CarDTO> getCarsNewerThan(int year, int order){
        logger.info("Getting all Cars with newer than "+year);
        Query q=null;
        switch (order) {
            case 2:
                q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.price asc");
                q.setParameter("y", year);
                break;
            case 3:
                q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.price desc");
                q.setParameter("y", year);
                break;
            case 4:
                q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.brand asc");
                q.setParameter("y", year);
                break;
            case 5:
                q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.brand desc");
                q.setParameter("y", year);
                break;
            case 6:
                q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by order by c.brand asc,c.model asc ");
                q.setParameter("y", year);
                break;
            case 7:
                q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by order by c.brand desc,c.model desc ");
                q.setParameter("y", year);
                break;
            default:
                q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.price asc");
                q.setParameter("y", year);
                break;
        }
        List<Car> aux = null;
        aux = q.getResultList();
        List<CarDTO> toSend = new ArrayList<>();
        for(Car c : aux){
            toSend.add(new CarDTO(c.getBrand(),c.getModel(),c.getPrice(),c.getRegistration_month(),c.getRegistration_year(),new UserDTO(c.getOwner().getEmail(),c.getOwner().getName(),c.getOwner().getAddress(),c.getOwner().getPhone())));
        }
        logger.info("Returning all Cars with year newer than "+year);
        return toSend;
    }

    public List<CarDTO> getCarsByPriceRange(long low_value, long up_value, int order){
        logger.info("Getting all Cars with price between "+low_value+"and"+up_value);
        List<Car> aux =null;
        Query q=null;
        switch (order) {
            case 2:
                logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.price asc");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 3:
                logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.price desc");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 4:
                logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by brand");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand asc");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 5:
                logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by brand");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand desc");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 6:
                logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by brand and model");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand asc,c.model asc ");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
            case 7:
                logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by brand and model");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand desc,c.model desc ");
                q.setParameter("lo", low_value);q.setParameter("up", up_value);
                break;
        }
        aux = q.getResultList();

        List<CarDTO> toSend = new ArrayList<>();
        for(Car c : aux){
            toSend.add(new CarDTO(c.getBrand(),c.getModel(),c.getPrice(),c.getRegistration_month(),c.getRegistration_year(),new UserDTO(c.getOwner().getEmail(),c.getOwner().getName(),c.getOwner().getAddress(),c.getOwner().getPhone())));
        }
        logger.info("Returning all Cars with price between "+low_value+"and"+up_value);
        return toSend;
    }
}
