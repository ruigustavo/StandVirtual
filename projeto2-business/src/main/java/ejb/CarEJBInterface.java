package ejb;

import DTOs.CarDTO;

import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface CarEJBInterface {
    void addCar(CarDTO car);
    String getAllCars(int order);
    String getCarsByBrand(String brand, int order);
    String getCarsByBrandAndModel(String brand, String model, int order);
    String getCarsByPriceRange(long low_value, long up_value, int order);
    void editCarInfo(int id, int field, String value);
}
