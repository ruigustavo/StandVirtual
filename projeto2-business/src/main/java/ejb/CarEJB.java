package ejb;

import DTOs.CarDTO;
import DTOs.UserDTO;
import data.Car;
import data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
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

    @Resource(mappedName = "java:jboss/mail/gmail")
    Session mailSession;


    public CarEJB() {
        logger = LoggerFactory.getLogger(CarEJB.class);
    }

    public void addCar(CarDTO car){
        logger.info("Adding car");
        logger.info("Getting owner from db.");
        Query q = null;
        try{
            q = em.createQuery("select u from "+ User.class.getSimpleName()+ " u where u.email = :i ");
            q.setParameter("i",car.getOwner().getEmail());
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Failed to get user. Returning...");
            return;
        }
        logger.info("Creating new Car.");
        User owner = (User) q.getSingleResult();
        Car toPersist = new Car(car.getBrand(),
                car.getModel(),car.getPrice(),car.getKm(),
                car.getRegistration_month(),car.getRegistration_year(),
                car.getPicture(),owner);
        logger.info("Persisting it to the db.");
        try{
            em.persist(toPersist);
            if(!owner.getSellingCars().contains(toPersist)){
                owner.getSellingCars().add(toPersist);
                em.persist(owner);
                logger.info("Car added with sucess!");
            }
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Car not added. Returning...");
            return;
        }
    }

    public CarDTO getCarById(int id){
        Car aux = null;
        CarDTO toSend = null;
        logger.info("Getting car by id from db.");
        try{
            Query q = em.createQuery("select u from "+ Car.class.getSimpleName() + " u where u.id = :i");
            q.setParameter("i", id);
            aux = (Car) q.getSingleResult();
            logger.warn(aux.toString());
            List<UserDTO> followers = new ArrayList<>();
            for(User u: aux.getFollowers()){
                followers.add(new UserDTO(u.getId(),
                        u.getEmail(),
                        u.getName(),
                        u.getAddress(),
                        u.getPhone()
                ));
            }
            toSend = new CarDTO(aux.getId(),
                    aux.getBrand(),
                    aux.getModel(),
                    aux.getPrice(),
                    aux.getKm(),
                    aux.getRegistration_month(),
                    aux.getRegistration_year(),
                    new UserDTO(aux.getOwner().getId(),
                            aux.getOwner().getEmail(),
                            aux.getOwner().getName(),
                            aux.getOwner().getAddress(),
                            aux.getOwner().getPhone()),
                    aux.getPicture(),
                    followers);
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Failed to get car by id. Returning...");
            return null;
        }
        logger.info("Returning car by id");
        logger.warn(toSend.toString());
        return toSend;
    }

    public void editCarInfo(CarDTO toEdit){
        logger.info("Editing Car with ID " + toEdit.getId());

        Car aux = null;
        Query q = null;
        logger.info("Getting car by id from db.");
        try{
            q = em.createQuery("select u from "+ Car.class.getSimpleName() + " u where u.id = :i");
            q.setParameter("i", toEdit.getId());
            aux = (Car) q.getSingleResult();
            //verificar se preço foi alterado
            if(toEdit.getPrice()!= aux.getPrice()){
                for(User c : aux.getFollowers()){
                    logger.info("Sending e-mail to:"+c.getEmail());
                    sendEmail(c.getEmail(),aux.getBrand(),aux.getModel());
                }
            }
            aux.setBrand(toEdit.getBrand());
            aux.setModel(toEdit.getModel());
            aux.setPrice(toEdit.getPrice());
            aux.setKm(toEdit.getKm());
            aux.setRegistration_month(toEdit.getRegistration_month());
            aux.setRegistration_year(toEdit.getRegistration_year());
            if(toEdit.getPicture()!=null){
                aux.setPicture(toEdit.getPicture());
            }
        } catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Failed to get car to edit. Returning...");
            return;
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

    public void deleteCarById(int id){
      try{
            logger.info("Deleting car with Id " + id);
            em.remove(em.find(Car.class, id));
            logger.info("Car "+id+" deleted");
            return;
        }catch (Exception e){
            logger.warn("Dropped exception");
            e.printStackTrace();
            logger.info("Car not deleted");
            return;
        }
    }
    public void unfollowCar(int car_id, int user_id){
        Car aux_car;
        User aux_user;
        Query q;
        logger.info("Getting car to unfollow from db.");
       try{
            q = em.createQuery("select u from "+ Car.class.getSimpleName() + " u where u.id = :i");
            q.setParameter("i", car_id);
            aux_car = (Car) q.getSingleResult();
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Failed to get car to unfollow. Returning...");
            return;
         }
        logger.info("Getting user from db.");
        try{
            q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.id = :i");
            q.setParameter("i", user_id);
            aux_user = (User) q.getSingleResult();
            aux_car.getFollowers().remove(aux_user);
            aux_user.getFollowingCars().remove(aux_car);
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Failed to get user. Returning...");
            return;
        }

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
    public void followCar(int car_id, int user_id){
        Car aux_car;
        User aux_user;
        Query q = null;
        logger.info("Getting the car to follow from db.");
        try{
            q = em.createQuery("select u from "+ Car.class.getSimpleName() + " u where u.id = :i");
            q.setParameter("i", car_id);
            aux_car = (Car) q.getSingleResult();
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Failed to get car to unfollow. Returning...");
            return;
        }
        logger.info("Getting user from db.");
        try {
            q = em.createQuery("select u from " + User.class.getSimpleName() + " u where u.id = :i");
            q.setParameter("i", user_id);
            aux_user = (User) q.getSingleResult();
            aux_car.getFollowers().add(aux_user);
            aux_user.getFollowingCars().add(aux_car);
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Failed to get user. Returning...");
            return;
        }

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
        List<CarDTO> toSend = new ArrayList<>();
        try{
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
                default:
                    logger.info(" Getting cars from ascending order by price.");
                    q = em.createQuery("from Car order by price asc");
                    break;
            }
            aux = q.getResultList();



            for(Car c : aux){
                List<UserDTO> followers = new ArrayList<>();
                for(User u: c.getFollowers()){
                    followers.add(new UserDTO(u.getId(),
                            u.getEmail(),
                            u.getName(),
                            u.getAddress(),
                            u.getPhone()
                    ));
                }
                toSend.add(new CarDTO(c.getId(),
                        c.getBrand(),
                        c.getModel(),
                        c.getPrice(),
                        c.getKm(),
                        c.getRegistration_month(),
                        c.getRegistration_year(),
                        new UserDTO(c.getOwner().getId(),
                                c.getOwner().getEmail(),
                                c.getOwner().getName(),
                                c.getOwner().getAddress(),
                                c.getOwner().getPhone()),
                        c.getPicture(),
                        followers
                ));
            }
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning all Cars failed");
            return null;
        }
        logger.info("Returning all Cars");
        return toSend;
    }

    public List<CarDTO> getCarsByBrand(String brand, int order){
        logger.info("Getting all Cars of the brand:"+brand);
        List<Car> aux ;
        Query q=null;
        List<CarDTO> toSend = new ArrayList<>();
        try{
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
                    logger.info("Getting all Cars of the brand:"+brand+"in ascending order by brand");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.brand asc");
                    q.setParameter("n", brand);
                case 4:
                    logger.info("Getting all Cars of the brand:"+brand+"in descending order by brand");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.brand desc");
                    q.setParameter("n", brand);
                    break;
                case 5:
                    logger.info("Getting all Cars of the brand:"+brand+"in ascending order by model");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.brand asc,c.model asc");
                    q.setParameter("n", brand);
                case 6:
                    logger.info("Getting all Cars of the brand:"+brand+"in descending order by model");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.brand = :n order by c.brand asc,c.model desc");
                    q.setParameter("n", brand);
                    break;
            }
            aux = q.getResultList();

            for(Car c : aux){
                List<UserDTO> followers = new ArrayList<>();
                for(User u: c.getFollowers()){
                    followers.add(new UserDTO(u.getId(),
                            u.getEmail(),
                            u.getName(),
                            u.getAddress(),
                            u.getPhone()
                    ));
                }
                toSend.add(new CarDTO(c.getId(),
                        c.getBrand(),
                        c.getModel(),
                        c.getPrice(),
                        c.getKm(),
                        c.getRegistration_month(),
                        c.getRegistration_year(),
                        new UserDTO(c.getOwner().getId(),
                                c.getOwner().getEmail(),
                                c.getOwner().getName(),
                                c.getOwner().getAddress(),
                                c.getOwner().getPhone()),
                        c.getPicture(),
                        followers
                ));
            }
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Return all Cars of the brand:"+brand+"failed");
            return null;
        }
        logger.warn(toSend.toString());
        logger.info("Return all Cars of the brand:"+brand);
        return toSend;
        }

    public List<CarDTO> getCarsByBrandAndModel(String brand, String model, int order){
        logger.info("Getting all Cars of the brand:"+brand+"and model:"+model);
        List<Car> aux ;
        Query q=null;
        List<CarDTO> toSend = new ArrayList<>();
        try{
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
            case 3:
                logger.info("Getting all Cars of the brand:"+brand+"and model:"+model+"in ascending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where (c.brand = :b AND c.model = :m) order by c.brand asc");
                q.setParameter("b", brand);q.setParameter("m", model);
                break;
            case 4:
                logger.info("Getting all Cars of the brand:"+brand+"and model:"+model+"in descending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where (c.brand = :b AND c.model = :m) order by c.brand desc");
                q.setParameter("b", brand);q.setParameter("m", model);
                break;
            case 5:
                logger.info("Getting all Cars of the brand:"+brand+"and model:"+model+"in ascending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where (c.brand = :b AND c.model = :m) order by c.brand asc,c.model asc");
                q.setParameter("b", brand);q.setParameter("m", model);
                break;
            case 6:
                logger.info("Getting all Cars of the brand:"+brand+"and model:"+model+"in descending order by price");
                q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where (c.brand = :b AND c.model = :m) order by c.brand asc,c.model desc");
                q.setParameter("b", brand);q.setParameter("m", model);
                break;
        }
            aux = q.getResultList();

            for(Car c : aux){
                List<UserDTO> followers = new ArrayList<>();
                for(User u: c.getFollowers()){
                    followers.add(new UserDTO(u.getId(),
                            u.getEmail(),
                            u.getName(),
                            u.getAddress(),
                            u.getPhone()
                    ));
                }

                toSend.add(new CarDTO(c.getId(),
                        c.getBrand(),
                        c.getModel(),
                        c.getPrice(),
                        c.getKm(),
                        c.getRegistration_month(),
                        c.getRegistration_year(),
                        new UserDTO(c.getOwner().getId(),
                                c.getOwner().getEmail(),
                                c.getOwner().getName(),
                                c.getOwner().getAddress(),
                                c.getOwner().getPhone()),
                        c.getPicture(),
                        followers
                ));
            }
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Return all Cars of the brand:"+brand+"and model:"+model+"failed");
            return null;
        }
        logger.info("Return all Cars of the brand:"+brand+"and model:"+model);
        return toSend;
    }
    public List<CarDTO> getCarsNewerThan(int year, int order){
        logger.info("Getting all Cars with newer than "+year);
        Query q=null;
        List<CarDTO> toSend = new ArrayList<>();
        try{
            switch (order) {
                case 1:
                    q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.price asc");
                    q.setParameter("y", year);
                    break;
                case 2:
                    q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.price desc");
                    q.setParameter("y", year);
                    break;
                case 3:
                    q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.brand asc");
                    q.setParameter("y", year);
                    break;
                case 4:
                    q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.brand desc");
                    q.setParameter("y", year);
                    break;
                case 5:
                    q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.brand asc,c.model asc ");
                    q.setParameter("y", year);
                    break;
                case 6:
                    q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.brand desc,c.model desc ");
                    q.setParameter("y", year);
                    break;
                default:
                    q = em.createQuery("select c from " + Car.class.getSimpleName() + " c   where c.registration_year > :y  order by c.price asc");
                    q.setParameter("y", year);
                    break;
            }
            List<Car> aux;
            aux = q.getResultList();

            for(Car c : aux){
                List<UserDTO> followers = new ArrayList<>();
                for(User u: c.getFollowers()){
                    followers.add(new UserDTO(u.getId(),
                            u.getEmail(),
                            u.getName(),
                            u.getAddress(),
                            u.getPhone()
                    ));
                }
                toSend.add(new CarDTO(c.getId(),
                        c.getBrand(),
                        c.getModel(),
                        c.getPrice(),
                        c.getKm(),
                        c.getRegistration_month(),
                        c.getRegistration_year(),
                        new UserDTO(c.getOwner().getId(),
                                c.getOwner().getEmail(),
                                c.getOwner().getName(),
                                c.getOwner().getAddress(),
                                c.getOwner().getPhone()),
                        c.getPicture(),
                        followers
                ));
            }
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning all Cars with year newer than "+year+"failed");
            return null;
        }
        logger.info("Returning all Cars with year newer than "+year);
        return toSend;
    }

    public List<CarDTO> getCarsByPriceRange(long low_value, long up_value, int order){
        logger.info("Getting all Cars with price between "+low_value+"and"+up_value);
        List<Car> aux =null;
        Query q=null;
        List<CarDTO> toSend = new ArrayList<>();
        try{
            switch (order) {
                case 1:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by price");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.price asc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 2:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by price");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.price desc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 3:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by brand");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand asc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 4:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by brand");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand desc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 5:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by brand and model");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand asc,c.model asc ");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 6:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by brand and model");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.brand desc,c.model desc ");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                default:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by price");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.price between :lo AND :up order by c.price asc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
            }
            aux = q.getResultList();

            for(Car c : aux){
                List<UserDTO> followers = new ArrayList<>();
                for(User u: c.getFollowers()){
                    followers.add(new UserDTO(u.getId(),
                            u.getEmail(),
                            u.getName(),
                            u.getAddress(),
                            u.getPhone()
                    ));
                }
                toSend.add(new CarDTO(c.getId(),
                        c.getBrand(),
                        c.getModel(),
                        c.getPrice(),
                        c.getKm(),
                        c.getRegistration_month(),
                        c.getRegistration_year(),
                        new UserDTO(c.getOwner().getId(),
                                c.getOwner().getEmail(),
                                c.getOwner().getName(),
                                c.getOwner().getAddress(),
                                c.getOwner().getPhone()),
                        c.getPicture(),
                        followers
                ));
            }
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning all Cars with price between "+low_value+"and"+up_value+" failed");
            return null;
        }
        logger.info("Returning all Cars with price between "+low_value+" and "+up_value);
        return toSend;
    }

    public List<String> getDistinctBrands(){
        logger.info("Getting distinct brands");
        List<String> aux =null;
        Query q=null;
        try{
            q = em.createQuery("SELECT DISTINCT c.brand from " +Car.class.getSimpleName()+ " c" );
            aux = q.getResultList();
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning distinct brands failed");
            return null;
        }
        logger.info("Returning distinct brands ");
        return aux;
    }

    @Override
    public List<String> getDistinctModels() {
        logger.info("Getting distinct models");
        List<String> aux = null;
        try{
            Query q= em.createQuery("SELECT DISTINCT c.model from " +Car.class.getSimpleName()+ " c" );
            aux = q.getResultList();
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning distinct models failed");
            return null;
        }
        logger.info("Returning distinct models ");
        return aux;
    }


    public List<CarDTO> getCarsByKmRange(long low_value, long up_value, int order){
        logger.info("Getting all Cars with kilometers between "+low_value+"and"+up_value);
        List<Car> aux =null;
        Query q=null;
        List<CarDTO> toSend = new ArrayList<>();
        try{
            switch (order) {
                case 1:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by price");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.km between :lo AND :up order by c.price asc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 2:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by price");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.km between :lo AND :up order by c.price desc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 3:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by brand");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.km between :lo AND :up order by c.brand asc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 4:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by brand");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.km between :lo AND :up order by c.brand desc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 5:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by brand and model");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.km between :lo AND :up order by c.brand asc,c.model asc ");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                case 6:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in descending order by brand and model");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.km between :lo AND :up order by c.brand desc,c.model desc ");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
                default:
                    logger.info("Getting all Cars between "+low_value+"and"+up_value+"in ascending order by km");
                    q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c where c.km between :lo AND :up order by c.km asc");
                    q.setParameter("lo", low_value);q.setParameter("up", up_value);
                    break;
            }
            aux = q.getResultList();


            for(Car c : aux){
                List<UserDTO> followers = new ArrayList<>();
                for(User u: c.getFollowers()){
                    followers.add(new UserDTO(u.getId(),
                            u.getEmail(),
                            u.getName(),
                            u.getAddress(),
                            u.getPhone()
                    ));
                }
                toSend.add(new CarDTO(c.getId(),
                        c.getBrand(),
                        c.getModel(),
                        c.getPrice(),
                        c.getKm(),
                        c.getRegistration_month(),
                        c.getRegistration_year(),
                        new UserDTO(c.getOwner().getId(),
                                c.getOwner().getEmail(),
                                c.getOwner().getName(),
                                c.getOwner().getAddress(),
                                c.getOwner().getPhone()),
                        c.getPicture(),
                        followers
                ));
            }
    }   catch (Exception e) {
        logger.warn("Dropped Exception");
        e.printStackTrace();
        logger.info("Returning all Cars with kilometers between"+low_value+"and"+up_value+ "failed");
        return null;
    }
        logger.info("Returning all Cars with kilometers between "+low_value+"and"+up_value);
        return toSend;
    }


    public void sendEmail(String recipient_email, String brand, String model){
        // Recipient's email ID needs to be mentioned.
        logger.info("Sending notification to:" +recipient_email);
        String to = recipient_email;

        MimeMessage m = new MimeMessage(mailSession);
        try {
            m.setRecipients(Message.RecipientType.TO, to);
            m.setSubject("Price updated!");
            m.setContent("Notification: The price of the "+brand+" "+model+" was changed!","text/plain");
            Transport.send(m);//throws exception
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.info("Message not sent succefully");
            return;
        }
        logger.info("Email sent with success to: "+recipient_email);
    }
    public List<CarDTO> getCarsOfUser(int id){
        logger.info("Getting Cars of User with ID " + id);
        List<Car> aux =null;
        List<CarDTO> toSend = new ArrayList<>();
        try{
            Query q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c   where c.owner.id = :n order by c.price asc");
            q.setParameter("n", id);
            aux = q.getResultList();
            for(Car c : aux){
                List<UserDTO> followers = new ArrayList<>();
                for(User u: c.getFollowers()){
                    followers.add(new UserDTO(u.getId(),
                            u.getEmail(),
                            u.getName(),
                            u.getAddress(),
                            u.getPhone()
                    ));
                }
                toSend.add(new CarDTO(c.getId(),
                        c.getBrand(),
                        c.getModel(),
                        c.getPrice(),
                        c.getKm(),
                        c.getRegistration_month(),
                        c.getRegistration_year(),
                        new UserDTO(c.getOwner().getId(),
                                c.getOwner().getEmail(),
                                c.getOwner().getName(),
                                c.getOwner().getAddress(),
                                c.getOwner().getPhone()),
                        c.getPicture(),
                        followers
                ));
            }
        }   catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning cars of User failed");
            return null;
        }
        logger.info("Returning Cars of User with ID " + id);
        return toSend;

    }

    public List<CarDTO> getCarsUserFollow(int id){
        List<Car> aux = null;
        List<CarDTO> toSend = new ArrayList<>();
        logger.info("Getting user from db.");
        try{
            Query q = em.createQuery("select u from "+ User.class.getSimpleName() + " u where u.id = :i");
            q.setParameter("i", id);
            User u = (User) q.getSingleResult();
            aux = u.getFollowingCars();

            for(Car c : aux){
                List<UserDTO> followers = new ArrayList<>();
                for(User u1: c.getFollowers()){
                    followers.add(new UserDTO(u1.getId(),
                            u1.getEmail(),
                            u1.getName(),
                            u1.getAddress(),
                            u1.getPhone()
                    ));
                }
                toSend.add(new CarDTO(c.getId(),
                        c.getBrand(),
                        c.getModel(),
                        c.getPrice(),
                        c.getKm(),
                        c.getRegistration_month(),
                        c.getRegistration_year(),
                        new UserDTO(c.getOwner().getId(),
                                c.getOwner().getEmail(),
                                c.getOwner().getName(),
                                c.getOwner().getAddress(),
                                c.getOwner().getPhone()),
                        c.getPicture(),
                        followers
                ));
            }
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning cars that a user follow failed");
            return null;
        }
        logger.info("Getting user");
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
            List<UserDTO> followers = new ArrayList<>();
            for(User u: c.getFollowers()){
                followers.add(new UserDTO(u.getId(),
                        u.getEmail(),
                        u.getName(),
                        u.getAddress(),
                        u.getPhone()
                ));
            }
            toSend.add(new CarDTO(c.getId(),
                    c.getBrand(),
                    c.getModel(),
                    c.getPrice(),
                    c.getKm(),
                    c.getRegistration_month(),
                    c.getRegistration_year(),
                    new UserDTO(c.getOwner().getId(),
                            c.getOwner().getEmail(),
                            c.getOwner().getName(),
                            c.getOwner().getAddress(),
                            c.getOwner().getPhone()),
                    c.getPicture(),
                    followers
            ));
        }
        logger.info("Returning Cars of User with ID " + id);
        return toSend;
    }


    @Schedule( minute="*/1",hour="*", persistent=false)
    public void statistics(){
        int total_followers =0;
        List<Car>aux=null;
        String noob_owners="";
        Query q=null;
        long count_users=0L;
        long count_cars =0L;
        try{
            logger.info("Obtaining the number of users");
            q = em.createQuery("SELECT count(*) FROM "+User.class.getSimpleName());
            count_users = (long) q.getSingleResult();
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning null");
        }
        try{
            logger.info("Getting the number os cars");
            q = em.createQuery("SELECT count(*) FROM "+Car.class.getSimpleName());
            count_cars = (long) q.getSingleResult();
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning null");
        }
        try{
            logger.info("Getting the names of the users that follow their own cars");
            q = em.createQuery("select c from "+ Car.class.getSimpleName()+" c");
            aux = q.getResultList();
            for(Car c : aux){
                total_followers+= c.getFollowers().size();

                for (User f : c.getFollowers()) {
                    if (c.getOwner().getId() == f.getId()) {
                        noob_owners = noob_owners + f.getName() + ", ";
                    }
                }

            }
            if(noob_owners.compareTo("")!=0){
                noob_owners = noob_owners.substring(0, noob_owners.length() - 2);
            }
        }catch (Exception e) {
            logger.warn("Dropped Exception");
            e.printStackTrace();
            logger.info("Returning null");
            return;
        }


        logger.info("***********Statistics**********");
        logger.info("Number of registered users:"+ count_users);
        logger.info("Number of cars for sale:"+ count_cars);
        if(count_cars>0)
            logger.info("Average number of followers per car for sale:"+ total_followers/count_cars);
        else
            logger.info("Average number of followers per car for sale:"+ 0);
        logger.info("List of users that follow their own cars:"+noob_owners);
    }
}
