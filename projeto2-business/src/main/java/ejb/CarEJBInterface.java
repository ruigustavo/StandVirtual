package ejb;

import DTOs.CarDTO;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Local
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
    CarDTO getCarById(int id);
}
