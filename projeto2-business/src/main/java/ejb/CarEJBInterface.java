package ejb;

import DTOs.CarDTO;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Remote
public interface CarEJBInterface {
    void addCar(CarDTO car);

    List<CarDTO> getAllCars(int order);

    List<CarDTO> getCarsByBrand(String brand, int order);

    List<CarDTO> getCarsByBrandAndModel(String brand, String model, int order);

    List<CarDTO> getCarsByPriceRange(long low_value, long up_value, int order);

    void editCarInfo(CarDTO toEdit);

    List<CarDTO> getCarsNewerThan(int year, int order);

    void followCar(int car_id, int user_id);

    void unfollowCar(int car_id, int user_id);

    void deleteCarById(int id);

    CarDTO getCarById(int id);

    List<CarDTO> getCarsByKmRange(long low_value, long up_value, int order);

    List<CarDTO> getCarsOfUser(int id);
    List<CarDTO> getCarsUserNotOwn(int id);
    List<CarDTO> getCarsUserFollow(int id);
   // void sendEmail(String recipient_email);
    List<String> getDistinctBrands();

    List<String> getDistinctModels();

    //void statistics();
}