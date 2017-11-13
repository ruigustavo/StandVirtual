package ejb;

import DTOs.CarDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface CarEJBInterface {
    void addCar(CarDTO car);
    List<CarDTO> getAllCars(int order);
    List<CarDTO> getCarsByBrand(String brand, int order);
    List<CarDTO> getCarsByBrandAndModel(String brand, String model, int order);
    List<CarDTO> getCarsByPriceRange(long low_value, long up_value, int order);
    void editCarInfo(int id, int field, String value);
    List<CarDTO> getCarsNewerThan(int year, int order);
    void followCar(int car_id, int user_id);
    void unfollowCar(int car_id, int user_id);
    void deleteCarById(int id);
}
